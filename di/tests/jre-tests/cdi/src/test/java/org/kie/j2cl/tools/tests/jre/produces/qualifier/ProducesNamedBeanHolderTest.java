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

package org.kie.j2cl.tools.tests.jre.produces.qualifier;

import java.lang.annotation.Annotation;

import org.junit.Test;
import org.kie.j2cl.tools.di.core.internal.QualifierUtil;
import org.kie.j2cl.tools.tests.jre.AbstractTest;

import static org.junit.Assert.assertEquals;

public class ProducesNamedBeanHolderTest extends AbstractTest {


    private final QualifierOne qualifierOne = new QualifierOne() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return QualifierOne.class;
        }
    };

    @Test
    public void testBean() {
        assertEquals("Default",
                app.beanManager.lookupBean(QualifierBean.class).getInstance().say());
        assertEquals("Default",
                app.beanManager.lookupBean(QualifierBeanProducerHolder.class).getInstance().getQualifierBean().say());
        assertEquals("Default",
                app.beanManager.lookupBean(QualifierBeanProducerHolder.class).getInstance().getQualifierBeanConstructor().say());
    }

    @Test
    public void testBeanDefault() {
        assertEquals("Default",
                app.beanManager.lookupBean(QualifierBean.class, QualifierUtil.DEFAULT_ANNOTATION).getInstance().say());
        assertEquals("Default",
                app.beanManager.lookupBean(QualifierBeanProducerHolder.class).getInstance().getQualifierBeanDefault().say());
        assertEquals("Default",
                app.beanManager.lookupBean(QualifierBeanProducerHolder.class).getInstance().getQualifierBeanConstructorDefault().say());
    }

    @Test
    public void testBeanNamedOne() {
        assertEquals("QualifierOne",
                app.beanManager.lookupBean(QualifierBean.class, qualifierOne).getInstance().say());
        assertEquals("QualifierOne",
                app.beanManager.lookupBean(QualifierBeanProducerHolder.class).getInstance().getQualifierBeanQualifierOne().say());
        assertEquals("QualifierOne",
                app.beanManager.lookupBean(QualifierBeanProducerHolder.class).getInstance().getQualifierBeanConstructorQualifierOne().say());

    }
}
