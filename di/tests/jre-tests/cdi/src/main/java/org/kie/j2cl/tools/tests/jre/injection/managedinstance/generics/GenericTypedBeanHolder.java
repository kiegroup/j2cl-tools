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

package org.kie.j2cl.tools.tests.jre.injection.managedinstance.generics;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

import org.kie.j2cl.tools.di.core.ManagedInstance;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.controls.AbstractCanvasHandler;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.controls.CanvasCommandFactory;

@Dependent
public class GenericTypedBeanHolder {

    @Inject
    private CanvasCommandFactory<AbstractCanvasHandler> canvasCommandFactoryDirectField;
    @Inject
    private ManagedInstance<CanvasCommandFactory<AbstractCanvasHandler>> canvasCommandFactoryInstanceField;
    private ManagedInstance<CanvasCommandFactory<AbstractCanvasHandler>> canvasCommandFactoryInstanceConstructor;


    @Inject
    public GenericTypedBeanHolder(@Any ManagedInstance<CanvasCommandFactory<AbstractCanvasHandler>> canvasCommandFactoryInstance) {
        this.canvasCommandFactoryInstanceConstructor = canvasCommandFactoryInstance;
    }

    public ManagedInstance<CanvasCommandFactory<AbstractCanvasHandler>> getCanvasCommandFactoryInstanceField() {
        return canvasCommandFactoryInstanceField;
    }

    public ManagedInstance<CanvasCommandFactory<AbstractCanvasHandler>> getCanvasCommandFactoryInstanceConstructor() {
        return canvasCommandFactoryInstanceConstructor;
    }

    public CanvasCommandFactory<AbstractCanvasHandler> getCanvasCommandFactoryDirectField() {
        return canvasCommandFactoryDirectField;
    }
}
