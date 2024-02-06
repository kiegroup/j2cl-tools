/*
 * Copyright © 2020 Treblereel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.j2cl.tools.xml.mapper.client.tests.generics;

import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 7/15/20 */
@XMLMapper
public class SampleGenericType extends BaseGenericType<String, Integer> {

  private String field3;

  @Override
  public int hashCode() {
    return getField3().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SampleGenericType)) {
      return false;
    }

    SampleGenericType that = (SampleGenericType) o;

    return getField3().equals(that.getField3())
        && getField2().equals(that.getField2())
        && getField1().equals(that.getField1());
  }

  public String getField3() {
    return field3;
  }

  public void setField3(String field3) {
    this.field3 = field3;
  }
}
