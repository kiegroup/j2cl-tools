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

package org.kie.j2cl.tools.di.ui.navigation.generator;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import javax.lang.model.element.TypeElement;

import org.kie.j2cl.tools.di.apt.generator.ManagedBeanGenerator;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.WiringElementType;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.ui.navigation.client.annotation.Page;
import org.kie.j2cl.tools.di.ui.navigation.client.internal.NavigationGraph;

@Generator
public class NavigationGenerator extends ManagedBeanGenerator {

    public NavigationGenerator(TreeLogger treeLogger, IOCContext iocContext) {
        super(treeLogger, iocContext);
    }

    @Override
    public void register() {
        iocContext.register(ApplicationScoped.class, NavigationGraph.class, WiringElementType.BEAN,
                this);
    }

    @Override
    public void before() {
        Set<TypeElement> pages = iocContext.getTypeElementsByAnnotation(Page.class.getCanonicalName());
        if (!pages.isEmpty()) {
            new NavigationGraphGenerator(iocContext, pages)
                    .generate(logger.branch(TreeLogger.DEBUG, " starting generating navigation"));
        }
    }

}
