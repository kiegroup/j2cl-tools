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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 4/6/20 */
@XMLMapper
public class Data2 {

  private List<Data> collection = new ArrayList<>();

  public List<Data> getCollection() {
    return collection;
  }

  public void setCollection(List<Data> collection) {
    this.collection = collection;
  }

  public void addDataHolder(Data data) {
    collection.add(data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCollection());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Data2)) {
      return false;
    }
    Data2 data2 = (Data2) o;
    return Objects.equals(getCollection(), data2.getCollection());
  }
}
