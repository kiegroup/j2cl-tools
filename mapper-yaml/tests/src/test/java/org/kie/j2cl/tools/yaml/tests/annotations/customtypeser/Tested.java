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

package org.kie.j2cl.tools.yaml.tests.annotations.customtypeser;

import java.util.List;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;

@YAMLMapper
public class Tested {
  private ValueHolder value;

  private ValueHolder[] values;

  private List<ValueHolder> listOfValues;

  private OneMoreValueHolder oneMoreValueHolder;

  private OneMoreValueHolder[] oneMoreValueHolders;

  private List<OneMoreValueHolder> listOfOneMoreValueHolders;

  public List<OneMoreValueHolder> getListOfOneMoreValueHolders() {
    return listOfOneMoreValueHolders;
  }

  public List<ValueHolder> getListOfValues() {
    return listOfValues;
  }

  public OneMoreValueHolder getOneMoreValueHolder() {
    return oneMoreValueHolder;
  }

  public OneMoreValueHolder[] getOneMoreValueHolders() {
    return oneMoreValueHolders;
  }

  public ValueHolder getValue() {
    return value;
  }

  public ValueHolder[] getValues() {
    return values;
  }

  public void setListOfOneMoreValueHolders(List<OneMoreValueHolder> listOfOneMoreValueHolders) {
    this.listOfOneMoreValueHolders = listOfOneMoreValueHolders;
  }

  public void setListOfValues(List<ValueHolder> listOfValues) {
    this.listOfValues = listOfValues;
  }

  public void setOneMoreValueHolder(OneMoreValueHolder oneMoreValueHolder) {
    this.oneMoreValueHolder = oneMoreValueHolder;
  }

  public void setOneMoreValueHolders(OneMoreValueHolder[] oneMoreValueHolders) {
    this.oneMoreValueHolders = oneMoreValueHolders;
  }

  public void setValue(ValueHolder value) {
    this.value = value;
  }

  public void setValues(ValueHolder[] values) {
    this.values = values;
  }
}
