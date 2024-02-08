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

package org.kie.j2cl.tools.di.apt.processor;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import javax.lang.model.element.VariableElement;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.definition.InjectableVariableDefinition;
import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.util.TypeUtils;

public class FieldProcessor extends InjectionPointProcessor {

    public FieldProcessor(IOCContext context, TreeLogger logger) {
        super(context, logger);
    }

    @Override
    public void process(BeanDefinition bean) throws UnableToCompleteException {
        Set<VariableElement> fields = TypeUtils
                .getAllFieldsIn(context.getGenerationContext().getElements(),
                        MoreTypes.asTypeElement(bean.getType()))
                .stream().filter(field -> field.getKind().isField())
                .filter(elm -> elm.getAnnotation(Inject.class) != null)
                .map(elm -> MoreElements.asVariable(elm)).collect(Collectors.toSet());

        process(bean, fields);
    }

    @Override
    protected void process(BeanDefinition bean, VariableElement field)
            throws UnableToCompleteException {
        bean.getFields().add(new InjectableVariableDefinition(bean, field));
    }
}
