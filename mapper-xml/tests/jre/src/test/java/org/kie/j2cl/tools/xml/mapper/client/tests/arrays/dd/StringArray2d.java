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
package org.kie.j2cl.tools.xml.mapper.client.tests.arrays.dd;

import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.Arrays;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 3/29/20 */
@XMLMapper
public class StringArray2d {

  private String check1;

  @XmlElementWrapper(name = "ZZZ")
  private String[][] array;

  private String[][] array2;

  private String check2;

  @Override
  public int hashCode() {
    return Arrays.hashCode(getArray());
  }

  public String[][] getArray() {
    return array;
  }

  public void setArray(String[][] array) {
    this.array = array;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StringArray2d)) {
      return false;
    }
    StringArray2d that = (StringArray2d) o;
    return Arrays.deepEquals(getArray(), that.getArray());
  }

  @Override
  public String toString() {
    return "StringArray2d{" + "array=" + Arrays.toString(array) + '}';
  }

  public String getCheck1() {
    return check1;
  }

  public void setCheck1(String check1) {
    this.check1 = check1;
  }

  public String getCheck2() {
    return check2;
  }

  public void setCheck2(String check2) {
    this.check2 = check2;
  }

  public String[][] getArray2() {
    return array2;
  }

  public void setArray2(String[][] array2) {
    this.array2 = array2;
  }
}
