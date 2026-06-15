/*
 * Copyright 2017 Ahmad Bawaneh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.j2cl.tools.yaml.mapper;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlGetter;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlSetter;
import org.kie.j2cl.tools.yaml.mapper.context.GenerationContext;
import org.kie.j2cl.tools.yaml.mapper.exception.GenerationException;

/**
 * Type class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class TypeUtils {

  public static final String BEAN_YAML_SERIALIZER_IMPL = "_YamlSerializerImpl";
  public static final String BEAN_YAML_DESERIALIZER_IMPL = "_YamlDeserializerImpl";
  private final Types types;
  private final Elements elements;
  private final TypeRegistry typeRegistry;
  private final TypeUtils typeUtils;

  public TypeUtils(GenerationContext context) {
    this.types = context.getProcessingEnv().getTypeUtils();
    this.elements = context.getProcessingEnv().getElementUtils();
    this.typeRegistry = context.getTypeRegistry();
    this.typeUtils = context.getTypeUtils();
  }

  /**
   * wrapperType.
   *
   * @param type a {@link TypeMirror} object.
   * @return a object.
   */
  public String wrapperType(TypeMirror type) {
    if (isPrimitive(type)) {
      type = types.getPrimitiveType(type.getKind());
      if ("boolean".equals(type.toString())) {
        return Boolean.class.getSimpleName();
      } else if ("byte".equals(type.toString())) {
        return Byte.class.getSimpleName();
      } else if ("short".equals(type.toString())) {
        return Short.class.getSimpleName();
      } else if ("int".equals(type.toString())) {
        return Integer.class.getSimpleName();
      } else if ("long".equals(type.toString())) {
        return Long.class.getSimpleName();
      } else if ("char".equals(type.toString())) {
        return Character.class.getSimpleName();
      } else if ("float".equals(type.toString())) {
        return Float.class.getSimpleName();
      } else if ("double".equals(type.toString())) {
        return Double.class.getSimpleName();
      } else {
        throw new IllegalArgumentException("Unknown primitive type: " + type.toString());
      }
    } else if (type.getKind().equals(TypeKind.ARRAY)) {
      ArrayType arrayType = (ArrayType) type;
      return arrayType.toString();
    } else if (type.getKind().equals(TypeKind.DECLARED)) {
      return MoreTypes.asTypeElement(type).getQualifiedName().toString();
    } else {
      return MoreTypes.asElement(type).toString();
    }
  }

  private static boolean isPrimitive(TypeMirror typeMirror) {
    return typeMirror.getKind().isPrimitive();
  }

  /**
   * isPrimitiveArray.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public static boolean isPrimitiveArray(TypeMirror typeMirror) {
    return (isArray(typeMirror) && isPrimitive(arrayComponentType(typeMirror)))
        || isPrimitive2dArray(typeMirror);
  }

  private static boolean isPrimitive2dArray(TypeMirror typeMirror) {
    return is2dArray(typeMirror) && isPrimitiveArray(arrayComponentType(typeMirror));
  }

  /**
   * is2dArray.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public static boolean is2dArray(TypeMirror typeMirror) {
    return isArray(typeMirror) && isArray(arrayComponentType(typeMirror));
  }

  /**
   * isArray.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public static boolean isArray(TypeMirror typeMirror) {
    return TypeKind.ARRAY.compareTo(typeMirror.getKind()) == 0;
  }

  /**
   * arrayComponentType.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link TypeMirror} object.
   */
  public static TypeMirror arrayComponentType(TypeMirror typeMirror) {
    return ((ArrayType) typeMirror).getComponentType();
  }

  /**
   * see: typetools/checker-framework Return all methods declared in the given type or any
   * superclass/interface. Note that no constructors will be returned. TODO: should this use
   * javax.lang.model.util.Elements.getAllMembers(TypeElement) instead of our own getSuperTypes?
   */
  public Collection<VariableElement> getAllFieldsIn(TypeElement type) {
    Map<String, VariableElement> fields = new LinkedHashMap<>();
    ElementFilter.fieldsIn(type.getEnclosedElements())
        .forEach(field -> fields.put(field.getSimpleName().toString(), field));

    List<TypeElement> alltypes = getSuperTypes(elements, type);
    for (TypeElement atype : alltypes) {
      ElementFilter.fieldsIn(atype.getEnclosedElements()).stream()
          .filter(field -> !fields.containsKey(field.getSimpleName().toString()))
          .forEach(field -> fields.put(field.getSimpleName().toString(), field));
    }
    return fields.values();
  }

  /**
   * see: typetools/checker-framework Determine all type elements for the classes and interfaces
   * referenced in the extends/implements clauses of the given type element. TODO: can we learn from
   * the implementation of com.sun.tools.javac.model.JavacElements.getAllMembers(TypeElement)?
   */
  public List<TypeElement> getSuperTypes(Elements elements, TypeElement type) {

    List<TypeElement> superelems = new ArrayList<>();
    if (type == null) {
      return superelems;
    }

    // Set up a stack containing type, which is our starting point.
    Deque<TypeElement> stack = new ArrayDeque<>();
    stack.push(type);

    while (!stack.isEmpty()) {
      TypeElement current = stack.pop();

      // For each direct supertype of the current type element, if it
      // hasn't already been visited, push it onto the stack and
      // add it to our superelems set.
      TypeMirror supertypecls = current.getSuperclass();
      if (supertypecls.getKind() != TypeKind.NONE) {
        TypeElement supercls = (TypeElement) ((DeclaredType) supertypecls).asElement();
        if (!superelems.contains(supercls)) {
          stack.push(supercls);
          superelems.add(supercls);
        }
      }
      for (TypeMirror supertypeitf : current.getInterfaces()) {
        TypeElement superitf = (TypeElement) ((DeclaredType) supertypeitf).asElement();
        if (!superelems.contains(superitf)) {
          stack.push(superitf);
          superelems.add(superitf);
        }
      }
    }

    // Include java.lang.Object as implicit superclass for all classes and interfaces.
    TypeElement jlobject = elements.getTypeElement(Object.class.getCanonicalName());
    if (!superelems.contains(jlobject)) {
      superelems.add(jlobject);
    }

    return Collections.unmodifiableList(superelems);
  }

  /**
   * If given type is bounded wildcard, remove the wildcard and returns extends bound if exists. If
   * extends bounds is non existing - return the super bound.
   *
   * <p>
   *
   * <p>If given type is not wildcard, returns type.
   *
   * @param type TypeMirror to be processed
   * @return extends or super bounds for given wildcard type
   */
  public TypeMirror removeOuterWildCards(TypeMirror type) {
    return type.accept(
        new SimpleTypeVisitor8<TypeMirror, Void>() {
          @Override
          public TypeMirror visitPrimitive(PrimitiveType t, Void p) {
            return types.getPrimitiveType(t.getKind());
          }

          @Override
          public TypeMirror visitArray(ArrayType t, Void p) {
            return types.getArrayType(visit(t.getComponentType(), p));
          }

          @Override
          public TypeMirror visitDeclared(DeclaredType t, Void p) {
            return t;
          }

          @Override
          public TypeMirror visitTypeVariable(TypeVariable t, Void p) {
            return t;
          }

          @Override
          public TypeMirror visitWildcard(WildcardType t, Void p) {
            return t.getExtendsBound() != null
                ? visit(t.getExtendsBound(), p)
                : t.getSuperBound() != null
                    ? visit(t.getSuperBound(), p)
                    : types.getDeclaredType(elements.getTypeElement(Object.class.getName()));
          }
        },
        null);
  }

  /**
   * isCollection.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public boolean isCollection(TypeMirror typeMirror) {
    return !TypeUtils.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Collection.class);
  }

  /**
   * isAssignableFrom.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @param targetClass a {@link Class} object.
   * @return a boolean.
   */
  public boolean isAssignableFrom(TypeMirror typeMirror, Class<?> targetClass) {
    return types.isAssignable(
        typeMirror, types.getDeclaredType(elements.getTypeElement(targetClass.getCanonicalName())));
  }

  /**
   * isIterable.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public boolean isIterable(TypeMirror typeMirror) {
    return !TypeUtils.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Iterable.class);
  }

  public boolean isAssignableFrom(Element element, Class<?> targetClass) {
    return types.isAssignable(
        element.asType(),
        types.getDeclaredType(elements.getTypeElement(targetClass.getCanonicalName())));
  }

  /**
   * isMap.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public boolean isMap(TypeMirror typeMirror) {
    return !TypeUtils.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Map.class);
  }

  public boolean isBasicType(TypeMirror typeMirror) {
    return typeRegistry.isBasicType(stringifyTypeWithPackage(typeMirror));
  }

  /**
   * Stringify given TypeMirror including generic arguments and append package name
   *
   * @param type a {@link TypeMirror} object
   * @return a {@link String} containing string representation of given TypeMirror
   */
  public String stringifyTypeWithPackage(TypeMirror type) {
    TypeKind kind = type.getKind();

    if (kind.isPrimitive()) {
      return types.getPrimitiveType(kind).toString();
    }

    if (type.getKind().equals(TypeKind.ARRAY)) {
      return stringifyTypeWithPackage(((ArrayType) type).getComponentType()) + "[]";
    }
    return stringifyType(type, true);
  }

  private String stringifyType(TypeMirror type, boolean appendPackage) {
    return type.accept(
        new SimpleTypeVisitor8<String, Void>() {

          @Override
          public String visitPrimitive(PrimitiveType t, Void p) {
            return t.toString();
          }

          @Override
          public String visitArray(ArrayType t, Void p) {
            return visit(t.getComponentType(), p) + "[]";
          }

          @Override
          public String visitDeclared(DeclaredType t, Void p) {
            String packageName = appendPackage ? getPackage(t) : "";
            String prefix = (!packageName.isEmpty()) ? packageName + "." : "";

            String simpleName = t.asElement().getSimpleName().toString();

            if (!t.getTypeArguments().isEmpty()) {
              String typeArgs =
                  t.getTypeArguments().stream()
                      .map(arg -> visit(arg, p))
                      .collect(Collectors.joining(", "));
              return prefix + simpleName + "<" + typeArgs + ">";
            }

            return prefix + simpleName;
          }

          @Override
          public String visitTypeVariable(TypeVariable t, Void p) {
            return t.toString();
          }

          @Override
          public String visitWildcard(WildcardType t, Void p) {
            if (t.getExtendsBound() != null) {
              return "? extends " + visit(t.getExtendsBound(), p);
            }
            if (t.getSuperBound() != null) {
              return "? super " + visit(t.getSuperBound(), p);
            }
            return "?";
          }

          @Override
          public String visitIntersection(IntersectionType t, Void p) {
            return t.getBounds().stream()
                .map(bound -> visit(bound, p))
                .collect(Collectors.joining(" & "));
          }

          @Override
          protected String defaultAction(TypeMirror e, Void p) {
            return e.toString();
          }
        },
        null);
  }

  /**
   * Returns package name of given TypeMirror as String. For primitive types, returns emtpy string
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link String} object.
   */
  public String getPackage(TypeMirror typeMirror) {
    return types.asElement(types.erasure(typeMirror)) != null
        ? elements.getPackageOf(types.asElement(types.erasure(typeMirror))).toString()
        : "";
  }

  /**
   * isBasicType.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public boolean isSimpleType(TypeMirror typeMirror) {
    return typeRegistry.isBasicType(stringifyTypeWithPackage(typeMirror))
        || typeRegistry.isSimpleType(stringifyTypeWithPackage(typeMirror));
  }

  /**
   * simpleName.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link Name} object.
   */
  public Name simpleName(TypeMirror typeMirror) {
    return types.asElement(typeMirror).getSimpleName();
  }

  /**
   * Create serializer name for given packageName and beanType. Package name for corresponding
   * serializer is prepended to the result.
   *
   * @param beanType {@link TypeMirror} object
   * @return fully-qualified serializer class name
   */
  public String canonicalSerializerName(TypeMirror beanType) {
    return getPackage(beanType) + "." + serializerName(beanType);
  }

  public String serializerName(TypeMirror mirror) {
    if (typeRegistry.containsSerializer(mirror.toString())) {
      return typeRegistry.getCustomSerializer(mirror).getQualifiedName().toString();
    }
    TypeElement type = MoreTypes.asTypeElement(mirror);
    return (type.getEnclosingElement().getKind().equals(ElementKind.PACKAGE)
            ? ""
            : MoreElements.asType(type.getEnclosingElement()).getSimpleName().toString() + "_")
        + type.getSimpleName()
        + BEAN_YAML_SERIALIZER_IMPL;
  }

  /**
   * Returns deserializer name for given typeMirror. Package name for corresponding deserializer is
   * prepended to the result.
   *
   * @param beanType a {@link TypeMirror} object
   * @return fully qualified deserializer name
   */
  public String canonicalDeserializerName(TypeMirror beanType) {
    return getPackage(beanType) + "." + deserializerName(beanType);
  }

  public String deserializerName(TypeMirror mirror) {
    if (typeRegistry.containsDeserializer(mirror.toString())) {
      return typeRegistry.getCustomDeserializer(mirror).getQualifiedName().toString();
    }
    TypeElement type = MoreTypes.asTypeElement(mirror);
    return (type.getEnclosingElement().getKind().equals(ElementKind.PACKAGE)
            ? ""
            : MoreElements.asType(type.getEnclosingElement()).getSimpleName().toString() + "_")
        + type.getSimpleName()
        + BEAN_YAML_DESERIALIZER_IMPL;
  }

  public Optional<TypeMirror> getClassValueFromAnnotation(
      Element element, Class<? extends Annotation> annotation, String paramName) {
    for (AnnotationMirror am : element.getAnnotationMirrors()) {
      if (types.isSameType(
          am.getAnnotationType(),
          elements.getTypeElement(annotation.getCanonicalName()).asType())) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry :
            am.getElementValues().entrySet()) {
          if (paramName.equals(entry.getKey().getSimpleName().toString())) {
            AnnotationValue annotationValue = entry.getValue();
            return Optional.of((DeclaredType) annotationValue.getValue());
          }
        }
      }
    }
    return Optional.empty();
  }

  public boolean hasGetter(VariableElement variable) {
    return getGetter(variable) != null;
  }

  public ExecutableElement getGetter(VariableElement variable) {
    List<String> getterNames = compileGetterMethodName(variable);
    List<ExecutableElement> methods =
        ElementFilter.methodsIn(variable.getEnclosingElement().getEnclosedElements());

    TypeMirror type = variable.asType();

    Predicate<ExecutableElement> isCandidate =
        e ->
            !e.getModifiers().contains(Modifier.PRIVATE)
                && !e.getModifiers().contains(Modifier.STATIC)
                && e.getParameters().isEmpty()
                && types.isSameType(e.getReturnType(), type);

    Predicate<ExecutableElement> matchByName =
        e -> getterNames.contains(e.getSimpleName().toString());

    Predicate<ExecutableElement> matchByAnnotation =
        e -> {
          YamlGetter yg = e.getAnnotation(YamlGetter.class);
          return yg != null && yg.value().equals(variable.getSimpleName().toString());
        };

    return Stream.concat(
            methods.stream().filter(isCandidate.and(matchByName)),
            methods.stream().filter(isCandidate.and(matchByAnnotation)))
        .findFirst()
        .orElseThrow(
            () ->
                new GenerationException(
                    String.format(
                        "Unable to find suitable getter for %s.%s",
                        variable.getEnclosingElement(), variable.getSimpleName())));
  }

  public List<String> compileGetterMethodName(VariableElement variable) {
    String varName = variable.getSimpleName().toString();
    boolean isBoolean = isBoolean(variable);
    List<String> result = new ArrayList<>();
    result.add("get" + capitalize(varName));
    if (isBoolean) {
      result.add("is" + capitalize(varName));
    }
    return result;
  }

  public boolean isBoolean(VariableElement variable) {
    return variable.asType().getKind().equals(TypeKind.BOOLEAN)
        || variable.asType().toString().equals(Boolean.class.getCanonicalName());
  }

  public boolean hasSetter(VariableElement variable) {
    return getSetter(variable) != null;
  }

  public ExecutableElement getSetter(VariableElement variable) {
    String setterName = compileSetterMethodName(variable);

    List<ExecutableElement> methods =
        ElementFilter.methodsIn(variable.getEnclosingElement().getEnclosedElements());

    Predicate<ExecutableElement> isCandidate =
        e ->
            !e.getModifiers().contains(Modifier.PRIVATE)
                && !e.getModifiers().contains(Modifier.STATIC)
                && e.getParameters().size() == 1
                && types.isSameType(e.getParameters().get(0).asType(), variable.asType());

    Predicate<ExecutableElement> matchByName = e -> setterName.equals(e.getSimpleName().toString());

    Predicate<ExecutableElement> matchByAnnotation =
        e -> {
          YamlSetter ys = e.getAnnotation(YamlSetter.class);
          return ys != null && ys.value().equals(variable.getSimpleName().toString());
        };

    return Stream.concat(
            methods.stream().filter(isCandidate.and(matchByName)),
            methods.stream().filter(isCandidate.and(matchByAnnotation)))
        .findFirst()
        .orElseThrow(
            () ->
                new GenerationException(
                    String.format(
                        "Unable to find suitable setter for %s.%s",
                        variable.getEnclosingElement(), variable.getSimpleName())));
  }

  private String compileSetterMethodName(VariableElement variable) {
    String varName = variable.getSimpleName().toString();
    StringBuffer sb = new StringBuffer();
    sb.append("set");
    sb.append(capitalize(varName));
    return sb.toString();
  }

  public TypeMirror toType(Class<?> clazz) {
    return elements.getTypeElement(clazz.getCanonicalName()).asType();
  }

  public TypeElement toTypeElement(TypeMirror type) {
    return ((TypeElement) types.asElement(type));
  }

  private TypeMirror objectType = null;

  public TypeMirror getObject() {
    if (objectType == null) {
      objectType = elements.getTypeElement(Object.class.getCanonicalName()).asType();
    }
    return objectType;
  }

  public boolean isObject(TypeMirror type) {
    return types.isSameType(type, getObject());
  }

  private String capitalize(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }
}
