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

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.VariableElement;

import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.exception.GenerationException;
import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.validation.InjectionPointValidator;


public abstract class InjectionPointProcessor {

    protected final IOCContext context;
    protected final TreeLogger logger;

    public InjectionPointProcessor(IOCContext context, TreeLogger logger) {
        this.context = context;
        this.logger = logger;
    }

    public abstract void process(BeanDefinition bean) throws UnableToCompleteException;

    protected void process(BeanDefinition bean, Set<VariableElement> points)
            throws UnableToCompleteException {

        Set<UnableToCompleteException> errors = new HashSet<>();

        for (VariableElement field : points) {
            try {
                new InjectionPointValidator(context, field.getEnclosingElement()).validate(field);
                process(bean, field);
            } catch (UnableToCompleteException e) {
                errors.add(e);
            }
        }

        if (!errors.isEmpty()) {
            TreeLogger logger =
                    this.logger.branch(TreeLogger.ERROR, "Unable to process bean: " + bean.getType());
            for (UnableToCompleteException error : errors) {
                logger.log(TreeLogger.ERROR, error.getMessage());
            }
            throw new GenerationException();
        }
    }


    protected abstract void process(BeanDefinition bean, VariableElement field)
            throws UnableToCompleteException;


}
