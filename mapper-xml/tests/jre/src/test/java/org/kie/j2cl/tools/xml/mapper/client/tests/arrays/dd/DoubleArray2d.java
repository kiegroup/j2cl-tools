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
package org.kie.j2cl.tools.xml.mapper.client.tests.arrays.dd;

import java.util.Arrays;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class DoubleArray2d {

  private double[][] array;
  private double[][] array2;

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
    DoubleArray2d that = (DoubleArray2d) o;
    return Arrays.deepEquals(array, that.array) && Arrays.deepEquals(array2, that.array2);
  }

  public double[][] getArray() {
    return array;
  }

  public void setArray(double[][] array) {
    this.array = array;
  }

  public double[][] getArray2() {
    return array2;
  }

  public void setArray2(double[][] array2) {
    this.array2 = array2;
  }
}
