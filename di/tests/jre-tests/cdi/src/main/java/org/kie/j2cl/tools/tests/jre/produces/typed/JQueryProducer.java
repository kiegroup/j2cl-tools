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

package org.kie.j2cl.tools.tests.jre.produces.typed;

import jakarta.enterprise.inject.Produces;

import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

public class JQueryProducer {

  @Produces
  public static JQuery get() {

    return new JQuery() {

      @Override
      public JQueryElement wrap(Element element) {
        return null;
      }
    };
  }

  public interface JQuery<T extends JQueryElement> {

    T wrap(Element element);
  }

  public static abstract class JQueryElement extends HTMLElement {
  }
}
