/*
 * Copyright © 2021 Treblereel
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

import java.util.Arrays;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 3/29/20 */
@XMLMapper
public class IntegerArray2d {

  private int[][] array;
  private int[][] array2;

  @Override
  public int hashCode() {
    int result = Arrays.hashCode(array);
    result = 31 * result + Arrays.hashCode(array2);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IntegerArray2d that = (IntegerArray2d) o;
    return Arrays.deepEquals(array, that.array) && Arrays.deepEquals(array2, that.array2);
  }

  public int[][] getArray() {
    return array;
  }

  public void setArray(int[][] array) {
    this.array = array;
  }

  public int[][] getArray2() {
    return array2;
  }

  public void setArray2(int[][] array2) {
    this.array2 = array2;
  }
}
