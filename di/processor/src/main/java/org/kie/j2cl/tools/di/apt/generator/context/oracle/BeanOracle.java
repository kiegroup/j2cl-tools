/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.kie.j2cl.tools.di.apt.generator.context.oracle;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Specializes;
import jakarta.enterprise.inject.Typed;
import jakarta.inject.Named;
import jakarta.inject.Qualifier;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;

import com.google.auto.common.MoreTypes;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.definition.InjectableVariableDefinition;
import org.kie.j2cl.tools.di.apt.definition.UnscopedBeanDefinition;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.util.TypeUtils;

public class BeanOracle {

  private final IOCContext context;
  private final TreeLogger logger;

  public BeanOracle(IOCContext context, TreeLogger logger) {
    this.context = context;
    this.logger = logger;
  }

  public BeanDefinition guess(TypeMirror parent, InjectableVariableDefinition point) {
    Named named = point.getVariableElement().getAnnotation(Named.class);
    Set<AnnotationMirror> qualifiers = getAnnotationMirrors(point);
    boolean isInterfaceOrAbstractClass =
        isInterfaceOrAbstractClass(point.getVariableElement().asType());

    TypeMirror beanTypeMirror =
        context.getGenerationContext().getTypes().erasure(point.getVariableElement().asType());

    if (isInterfaceOrAbstractClass) {
      if (named != null) {
        Optional<BeanDefinition> candidate = processName(point, named);
        if (candidate.isPresent()) {
          return candidate.get();
        }
      }

      if (!qualifiers.isEmpty()) {
        Optional<BeanDefinition> candidate = processQualifiers(point, qualifiers);
        if (candidate.isPresent()) {
          return candidate.get();
        }
      }

      Optional<BeanDefinition> candidate =
          asInterfaceOrAbstractClass(point.getVariableElement().asType());
      if (candidate.isPresent()) {
        return candidate.get();
      }
    }

    // Case 2: simple injection case, known type
    if (context.getBeans().containsKey(beanTypeMirror)) {
      BeanDefinition simpleInjectionCaseCandidate = context.getBeans().get(beanTypeMirror);
      if (simpleInjectionCaseCandidate.getIocGenerator().isPresent()) {
        return simpleInjectionCaseCandidate;
      }
    }

    // handle generic injection point type
    Optional<BeanDefinition> maybeGeneric = asGenericPoint(parent, point);
    if (maybeGeneric.isPresent()) {
      return maybeGeneric.get();
    }

    if (isUnscopedBean(beanTypeMirror)) {
      return new UnscopedBeanDefinition(beanTypeMirror, logger, context);
    }

    return null;
  }

  // TODO add support to constructor injection points
  private Optional<BeanDefinition> asGenericPoint(TypeMirror parent,
      InjectableVariableDefinition point) {
    if (!point.getVariableElement().getEnclosingElement().getKind().isClass()) {
      return Optional.empty();
    }
    if (!MoreTypes.asDeclared(point.getVariableElement().asType()).getTypeArguments().isEmpty()
        && MoreTypes.asDeclared(point.getVariableElement().asType()).getTypeArguments()
            .size() == 1) {
      Types types = context.getGenerationContext().getTypes();
      DeclaredType declaredType = (DeclaredType) parent;
      TypeMirror asMemberOf = types.asMemberOf(declaredType, point.getVariableElement());
      Set<BeanDefinition> subclasses =
          getSubClasses(types.erasure(point.getVariableElement().asType()));

      for (BeanDefinition sub : subclasses) {
        if (types.isSubtype(sub.getType(), asMemberOf)) {
          return Optional.of(sub);
        }
      }
    }
    return Optional.empty();
  }

  private boolean isUnscopedBean(TypeMirror beanTypeMirror) {
    TypeElement type = MoreTypes.asTypeElement(beanTypeMirror);

    if (type.getKind().isClass() && !type.getModifiers().contains(Modifier.ABSTRACT)
        && type.getModifiers().contains(Modifier.PUBLIC)) {
      Set<ExecutableElement> constructors = ElementFilter.methodsIn(type.getEnclosedElements())
          .stream().filter(elm -> elm.getKind().equals(ElementKind.CONSTRUCTOR))
          .collect(Collectors.toSet());
      if (constructors.isEmpty()) {
        return true;
      }

      if (constructors.stream().filter(elm -> elm.getParameters().isEmpty())
          .filter(elm -> elm.getModifiers().contains(Modifier.PUBLIC)).count() == 1) {
        return true;
      }
    }

    return false;
  }

  private Optional<BeanDefinition> asInterfaceOrAbstractClass(TypeMirror point) {
    Set<BeanDefinition> subclasses = getSubClasses(point);

    Set<BeanDefinition> types = subclasses.stream()
        .filter(bean -> !isInterfaceOrAbstractClass(bean.getType())).collect(Collectors.toSet());
    // Case TheOnlyOneImpl
    if (types.size() == 1) {
      return types.stream().findFirst();
    } else if (types.size() > 1) {
      // Case @Default
      Set<BeanDefinition> maybeDefault = types.stream()
          .filter(
              elm -> MoreTypes.asTypeElement(elm.getType()).getAnnotation(Default.class) != null)
          .collect(Collectors.toSet());
      if (maybeDefault.size() == 1) {
        return maybeDefault.stream().findFirst();
      }
      // Case @Specializes
      Set<BeanDefinition> maybeSpecializes = types.stream().filter(
          elm -> MoreTypes.asTypeElement(elm.getType()).getAnnotation(Specializes.class) != null)
          .collect(Collectors.toSet());
      if (maybeSpecializes.size() == 1) {
        return maybeSpecializes.stream().findFirst();
      }
      // Case @Typed
      Set<BeanDefinition> maybeTyped = types.stream()
          .filter(elm -> MoreTypes.asTypeElement(elm.getType()).getAnnotation(Typed.class) != null)
          .collect(Collectors.toSet());
      if (!maybeTyped.isEmpty()) {
        TypeMirror mirror = context.getGenerationContext().getTypes().erasure(point);
        return Optional.of(context.getBean(mirror));

        /*        for (BeanDefinition typed : maybeTyped) {
          Optional<List<TypeMirror>> annotations = getTypedAnnotationValues(typed.getType());
          if (annotations.isPresent()) {
            if (annotations.get().contains(mirror)) {
              return Optional.of(typed);
            }
          }
        }*/
      }
    }
    return Optional.empty();
  }

  private Optional<List<TypeMirror>> getTypedAnnotationValues(TypeMirror typeMirror) {
    try {
      MoreTypes.asTypeElement(typeMirror).getAnnotation(Typed.class).value();
    } catch (MirroredTypesException e) {
      return Optional.of((List<TypeMirror>) e.getTypeMirrors());
    }
    return Optional.empty();
  }

  private Optional<BeanDefinition> processQualifiers(InjectableVariableDefinition point,
      Set<AnnotationMirror> qualifiers) {
    Set<BeanDefinition> subclasses = getSubClasses(point.getVariableElement().asType());

    String[] annotations =
        qualifiers.stream().map(a -> a.getAnnotationType().toString()).toArray(String[]::new);

    return subclasses.stream().filter(bean -> !isInterfaceOrAbstractClass(bean.getType()))
        .filter(
            q -> TypeUtils.containsAnnotation(MoreTypes.asTypeElement(q.getType()), annotations))
        .findFirst();

  }

  private Optional<BeanDefinition> processName(InjectableVariableDefinition point, Named named) {
    Set<BeanDefinition> subclasses = getSubClasses(point.getVariableElement().asType());
    return subclasses.stream().filter(bean -> !isInterfaceOrAbstractClass(bean.getType()))
        .filter(elm -> (MoreTypes.asTypeElement(elm.getType()).getAnnotation(Named.class) != null
            && MoreTypes.asTypeElement(elm.getType()).getAnnotation(Named.class).value()
                .equals(named.value())))
        .findFirst();
  }

  private Set<BeanDefinition> getSubClasses(TypeMirror point) {
    BeanDefinition type = context.getBean(point);
    return getSubClasses(type);
  }

  private Set<BeanDefinition> getSubClasses(BeanDefinition beanDefinition) {
    Set<BeanDefinition> result = new HashSet<>();
    Queue<BeanDefinition> queue = new LinkedList<>();
    queue.addAll(beanDefinition.getSubclasses());
    while (!queue.isEmpty()) {
      BeanDefinition poll = queue.poll();
      result.add(poll);
      queue.addAll(poll.getSubclasses());
    }
    return result;
  }

  private boolean isInterfaceOrAbstractClass(TypeMirror type) {
    return MoreTypes.asTypeElement(type).getKind().isInterface()
        || MoreTypes.asTypeElement(type).getModifiers().contains(Modifier.ABSTRACT);
  }

  private Set<AnnotationMirror> getAnnotationMirrors(InjectableVariableDefinition point) {
    return point.getVariableElement().getAnnotationMirrors().stream()
        .filter(anno -> anno.getAnnotationType().asElement().getAnnotation(Qualifier.class) != null)
        .collect(Collectors.toSet());
  }
}
