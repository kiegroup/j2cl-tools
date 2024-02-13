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

import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlUnwrappedCollection;

@J2clTestInput(ArrayAndListWrapTest.class)
public class ArrayAndListWrapTest {

  @Test
  public void test() throws XMLStreamException {

    Demo demo = new Demo();
    String[] arr = new String[3];
    arr[0] = "aaaa";
    arr[1] = "bbbb";
    arr[2] = "cccc";

    Boolean[] arr2 = new Boolean[3];
    arr2[0] = false;
    arr2[1] = true;
    arr2[2] = false;

    List<String> list = new ArrayList<>();
    list.add("AAA");
    list.add("BBB");
    list.add("CCC");

    demo.setArray2(arr);
    demo.setArray1(arr2);
    demo.setList(list);

    ArrayAndListWrapTest_Demo_XMLMapperImpl mapper =
        ArrayAndListWrapTest_Demo_XMLMapperImpl.INSTANCE;

    assertTrue(demo.equals(mapper.read(mapper.write(demo))));
    assertTrue(demo.equals(mapper.read(mapper.write(demo))));
    assertTrue(demo.equals(mapper.read(mapper.write(demo))));
    assertTrue(demo.equals(mapper.read(mapper.write(demo))));
  }

  @XMLMapper
  public static class Demo {
    @XmlUnwrappedCollection private Boolean[] array1;
    @XmlUnwrappedCollection private String[] array2;
    @XmlUnwrappedCollection private List<String> list;

    @Override
    public int hashCode() {
      int result = Arrays.hashCode(getArray1());
      result = 31 * result + Arrays.hashCode(getArray2());
      result = 31 * result + (getList() != null ? getList().hashCode() : 0);
      return result;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Demo)) return false;

      Demo demo = (Demo) o;

      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      if (!Arrays.equals(getArray1(), demo.getArray1())) return false;
      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      if (!Arrays.equals(getArray2(), demo.getArray2())) return false;
      return getList() != null ? getList().equals(demo.getList()) : demo.getList() == null;
    }

    public List<String> getList() {
      return list;
    }

    public void setList(List<String> list) {
      this.list = list;
    }

    public Boolean[] getArray1() {
      return array1;
    }

    public void setArray1(Boolean[] array1) {
      this.array1 = array1;
    }

    public String[] getArray2() {
      return array2;
    }

    public void setArray2(String[] array2) {
      this.array2 = array2;
    }

    @Override
    public String toString() {
      return "Demo{"
          + "array1="
          + Arrays.toString(array1)
          + ", array2="
          + Arrays.toString(array2)
          + ", list="
          + list
          + '}';
    }
  }
}
