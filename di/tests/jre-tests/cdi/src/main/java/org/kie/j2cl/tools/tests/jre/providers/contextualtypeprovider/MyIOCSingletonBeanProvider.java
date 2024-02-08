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

package org.kie.j2cl.tools.tests.jre.providers.contextualtypeprovider;

import java.lang.annotation.Annotation;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.kie.j2cl.tools.di.core.BeanManager;
import org.kie.j2cl.tools.di.core.ioc.ContextualTypeProvider;
import org.kie.j2cl.tools.di.core.ioc.IOCProvider;

@Singleton
@IOCProvider
public class MyIOCSingletonBeanProvider implements ContextualTypeProvider<MyIOCSingletonBean> {


  @Inject
  private BeanManager beanManager;

  private MyIOCSingletonBean myIOCBean;

  @Override
  public MyIOCSingletonBean provide(Class<?>[] typeargs, Annotation[] qualifiers) {
    if (beanManager == null) {
      throw new Error();
    }

    Class clazz1 = typeargs[0];
    Class clazz2 = typeargs[1];

    if(myIOCBean != null) {
      return myIOCBean;
    }

    myIOCBean = new MyIOCSingletonBean(clazz1, clazz2);
    return myIOCBean;
  }
}
