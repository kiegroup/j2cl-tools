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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class DataHolder {

  private Data2 data2 = new Data2();

  private List<Data> dataList = new ArrayList<>();

  private List<Data> dataList2 = new ArrayList<>();

  public void addDataHolder(Data data) {
    dataList.add(data);
  }

  public void addDataHolder2(Data data) {
    dataList2.add(data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDataList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DataHolder)) {
      return false;
    }
    DataHolder holder = (DataHolder) o;
    return Objects.equals(getDataList(), holder.getDataList());
  }

  public List<Data> getDataList() {
    return dataList;
  }

  public void setDataList(List<Data> dataList) {
    this.dataList = dataList;
  }

  public Data2 getData2() {
    return data2;
  }

  public void setData2(Data2 data2) {
    this.data2 = data2;
  }

  public List<Data> getDataList2() {
    return dataList2;
  }

  public void setDataList2(List<Data> dataList2) {
    this.dataList2 = dataList2;
  }
}
