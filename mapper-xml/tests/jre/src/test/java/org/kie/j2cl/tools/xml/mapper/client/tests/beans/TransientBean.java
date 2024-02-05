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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans;

import jakarta.xml.bind.annotation.XmlTransient;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class TransientBean {

  public static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><TransientBean><saveMe>YEAP</saveMe></TransientBean>";

  private transient String dontSaveMe;
  @XmlTransient private String dontSaveMeToo;
  private String saveMe;

  public String getSaveMe() {
    return saveMe;
  }

  public void setSaveMe(String saveMe) {
    this.saveMe = saveMe;
  }

  public String getDontSaveMe() {
    return dontSaveMe;
  }

  public void setDontSaveMe(String dontSaveMe) {
    this.dontSaveMe = dontSaveMe;
  }

  public String getDontSaveMeToo() {
    return dontSaveMeToo;
  }

  public void setDontSaveMeToo(String dontSaveMeToo) {
    this.dontSaveMeToo = dontSaveMeToo;
  }
}
