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

package org.kie.j2cl.tools.tests.jre.injection.cycle.simple;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.kie.j2cl.tools.di.annotation.CircularDependency;

@Singleton
@CircularDependency
public class SimpleBeanTwoImpl implements SimpleBeanTwo {

  private SimpleBeanOneImpl simpleBeanOne;

  public String postConstruct;

  @Inject
  public FieldInjectBean fieldInjectBean;

  @Inject
  public SimpleBeanTwoImpl(SimpleBeanOneImpl simpleBeanOne) {
    this.simpleBeanOne = simpleBeanOne;
  }

  @PostConstruct
  public void init() {
    postConstruct = getClass().getSimpleName() + ".init";
  }

  @Override
  public String whoAmI() {
    return getClass().getSimpleName();
  }

  public String whoIsDep() {
    return simpleBeanOne.whoAmI();
  }

  @Override
  public String getPostConstruct() {
    return postConstruct;
  }
}
