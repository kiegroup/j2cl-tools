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

package org.kie.j2cl.tools.tests.jre.injection.managedinstance;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import org.kie.j2cl.tools.di.core.BeanManager;
import org.kie.j2cl.tools.di.core.ManagedInstance;
import org.kie.j2cl.tools.tests.jre.injection.dependent.SimpleBeanDependent;
import org.kie.j2cl.tools.tests.jre.produces.qualifier.QualifierBean;

@ApplicationScoped
public class ManagedInstanceBean {

  @Inject
  private BeanManager beanManager;

  @Inject
  private ManagedInstance<ComponentIface> managedInstanceBean;

  @Inject
  private ManagedInstance<ComponentIface> InstanceBean;

  @Inject
  private Instance<SimpleBeanDependent> bean;

  @Inject
  private Instance<QualifierBean> bean2;

  @Inject
  public Instance<SimpleBean> simpleBean1;

  @Inject
  public ManagedInstance<SimpleBean> simpleBean2;

  public Instance<SimpleBean> constructor_simpleBean1;

  public ManagedInstance<SimpleBean> constructor_simpleBean2;

  public ManagedInstanceBean() {

  }

  @Inject
  public ManagedInstanceBean(Instance<SimpleBean> simpleBean1,
      ManagedInstance<SimpleBean> simpleBean2) {
    this.constructor_simpleBean1 = simpleBean1;
    this.constructor_simpleBean2 = simpleBean2;
  }


  public ManagedInstance<ComponentIface> getManagedInstanceBean() {
    return managedInstanceBean;
  }

  public ManagedInstance<ComponentIface> getInstanceBean() {
    return InstanceBean;
  }

  public Instance<SimpleBeanDependent> getBean() {
    return bean;
  }

  public Instance<QualifierBean> getBean2() {
    return bean2;
  }
}
