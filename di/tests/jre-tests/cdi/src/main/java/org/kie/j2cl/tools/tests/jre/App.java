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

package org.kie.j2cl.tools.tests.jre;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import org.kie.j2cl.tools.di.annotation.Application;
import org.kie.j2cl.tools.di.core.BeanManager;
import org.kie.j2cl.tools.di.ui.translation.client.annotation.Bundle;
import org.kie.j2cl.tools.tests.jre.injection.applicationscoped.SimpleBeanApplicationScoped;
import org.kie.j2cl.tools.tests.jre.injection.dependent.SimpleBeanDependent;
import org.kie.j2cl.tools.tests.jre.injection.dependent.SimpleDependentTest;
import org.kie.j2cl.tools.tests.jre.injection.inheritance.InheritanceBean;
import org.kie.j2cl.tools.tests.jre.injection.managedinstance.ManagedInstanceBean;
import org.kie.j2cl.tools.tests.jre.injection.named.NamedTestBean;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.QualifierConstructorInjection;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.QualifierFieldInjection;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.controls.NodeBuilderControl;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.specializes.SpecializesBeanHolder;
import org.kie.j2cl.tools.tests.jre.injection.singleton.SimpleBeanSingleton;
import org.kie.j2cl.tools.tests.jre.injection.singleton.SimpleSingletonTest;
import org.kie.j2cl.tools.tests.jre.postconstruct.PostConstructs;
import org.kie.j2cl.tools.tests.jre.produces.SimpleBeanProducerTest;
import org.kie.j2cl.tools.tests.jre.produces.qualifier.QualifierBeanProducerHolder;

@Application
@Bundle("i18n/simple/i18n.properties")
public class App {

  public String testPostConstruct;
  @Inject
  public QualifierFieldInjection qualifierFieldInjection;
  @Inject
  public QualifierConstructorInjection qualifierConstructorInjection;
  @Inject
  public SimpleDependentTest simpleDependentTest;
  @Inject
  public SimpleSingletonTest simpleSingletonTest;
  @Inject
  private SimpleBeanApplicationScoped simpleBeanApplicationScoped;
  @Inject
  private SimpleBeanSingleton simpleBeanSingleton;
  @Inject
  private SimpleBeanDependent simpleBeanDependent;
  @Inject
  private NamedTestBean namedTestBean;

  @Inject
  private SimpleBeanProducerTest simpleBeanProducerTest;

  @Inject
  private QualifierBeanProducerHolder qualifierBeanProducerTest;

  @Inject
  private ManagedInstanceBean managedInstanceBean;

  @Inject
  public BeanManager beanManager;

  @Inject
  protected PostConstructs postConstructs;

  @Inject
  public InheritanceBean inheritanceBean;

  @Inject
  public NodeBuilderControl nodeBuilderControl;

  @Inject
  public SpecializesBeanHolder specializesBeanHolder;

  public void onModuleLoad() {
    new AppBootstrap(this).initialize();
  }

  @PostConstruct
  public void init() {
    this.testPostConstruct = "PostConstructChild";
  }

  public String getTestPostConstruct() {
    return testPostConstruct;
  }

  public SimpleBeanApplicationScoped getSimpleBeanApplicationScoped() {
    return simpleBeanApplicationScoped;
  }

  public QualifierConstructorInjection getQualifierConstructorInjection() {
    return qualifierConstructorInjection;
  }

  public SimpleBeanSingleton getSimpleBeanSingleton() {
    return simpleBeanSingleton;
  }

  public SimpleBeanDependent getSimpleBeanDependent() {
    return simpleBeanDependent;
  }

  public QualifierFieldInjection getQualifierFieldInjection() {
    return qualifierFieldInjection;
  }

  public NamedTestBean getNamedTestBean() {
    return namedTestBean;
  }

  public SimpleBeanProducerTest getSimpleBeanProducerTest() {
    return simpleBeanProducerTest;
  }

  public void setSimpleBeanProducerTest(SimpleBeanProducerTest simpleBeanProducerTest) {
    this.simpleBeanProducerTest = simpleBeanProducerTest;
  }

  public QualifierBeanProducerHolder getQualifierBeanProducerTest() {
    return qualifierBeanProducerTest;
  }

  public ManagedInstanceBean getManagedInstanceBean() {
    return managedInstanceBean;
  }

}
