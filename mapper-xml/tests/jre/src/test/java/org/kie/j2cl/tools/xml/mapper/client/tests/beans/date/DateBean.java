/*
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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.date;

import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.Date;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class DateBean {

  private Date val;

  @XmlAttribute private Date val2;

  public Date getVal() {
    return val;
  }

  public void setVal(Date val) {
    this.val = val;
  }

  public Date getVal2() {
    return val2;
  }

  public void setVal2(Date val2) {
    this.val2 = val2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DateBean)) {
      return false;
    }

    DateBean dateBean = (DateBean) o;

    if (getVal() != null ? !getVal().equals(dateBean.getVal()) : dateBean.getVal() != null) {
      return false;
    }
    return getVal2() != null ? getVal2().equals(dateBean.getVal2()) : dateBean.getVal2() == null;
  }

  @Override
  public int hashCode() {
    int result = getVal() != null ? getVal().hashCode() : 0;
    result = 31 * result + (getVal2() != null ? getVal2().hashCode() : 0);
    return result;
  }
}
