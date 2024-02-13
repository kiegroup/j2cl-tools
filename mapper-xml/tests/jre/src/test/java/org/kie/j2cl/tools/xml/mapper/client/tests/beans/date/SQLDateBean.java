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

import java.sql.Date;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class SQLDateBean {

  private Date val;

  public Date getVal() {
    return val;
  }

  public void setVal(Date val) {
    this.val = val;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SQLDateBean)) {
      return false;
    }
    SQLDateBean that = (SQLDateBean) o;
    return Objects.equals(getVal(), that.getVal());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getVal());
  }
}
