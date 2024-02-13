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

package org.kie.j2cl.tools.xml.mapper.client.tests.arrays;

import java.util.Arrays;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlUnwrappedCollection;

@XMLMapper
public class PrimitiveArraysUnwrapped {

  @XmlUnwrappedCollection private boolean[] booleans;

  @XmlUnwrappedCollection private String[] strings;

  @XmlUnwrappedCollection private char[] chars;

  @XmlUnwrappedCollection private byte[] bytes;

  @XmlUnwrappedCollection private double[] doubles;

  @XmlUnwrappedCollection private int[] ints;

  @XmlUnwrappedCollection private long[] longs;

  @XmlUnwrappedCollection private short[] shorts;

  public PrimitiveArraysUnwrapped() {}

  public PrimitiveArraysUnwrapped(
      String[] strings,
      boolean[] booleans,
      char[] chars,
      byte[] bytes,
      double[] doubles,
      int[] ints,
      long[] longs,
      short[] shorts) {
    this.strings = strings;
    this.booleans = booleans;
    this.chars = chars;
    this.bytes = bytes;
    this.doubles = doubles;
    this.ints = ints;
    this.longs = longs;
    this.shorts = shorts;
  }

  public String[] getStrings() {
    return strings;
  }

  public void setStrings(String[] strings) {
    this.strings = strings;
  }

  public boolean[] getBooleans() {
    return booleans;
  }

  public void setBooleans(boolean[] booleans) {
    this.booleans = booleans;
  }

  public char[] getChars() {
    return chars;
  }

  public void setChars(char[] chars) {
    this.chars = chars;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public double[] getDoubles() {
    return doubles;
  }

  public void setDoubles(double[] doubles) {
    this.doubles = doubles;
  }

  public int[] getInts() {
    return ints;
  }

  public void setInts(int[] ints) {
    this.ints = ints;
  }

  public long[] getLongs() {
    return longs;
  }

  public void setLongs(long[] longs) {
    this.longs = longs;
  }

  public short[] getShorts() {
    return shorts;
  }

  public void setShorts(short[] shorts) {
    this.shorts = shorts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PrimitiveArraysUnwrapped)) return false;

    PrimitiveArraysUnwrapped that = (PrimitiveArraysUnwrapped) o;

    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    if (!Arrays.equals(getStrings(), that.getStrings())) return false;
    if (!Arrays.equals(getBooleans(), that.getBooleans())) return false;
    if (!Arrays.equals(getChars(), that.getChars())) return false;
    if (!Arrays.equals(getBytes(), that.getBytes())) return false;
    if (!Arrays.equals(getDoubles(), that.getDoubles())) return false;
    if (!Arrays.equals(getInts(), that.getInts())) return false;
    if (!Arrays.equals(getLongs(), that.getLongs())) return false;
    return Arrays.equals(getShorts(), that.getShorts());
  }

  @Override
  public int hashCode() {
    int result = Arrays.hashCode(getStrings());
    result = 31 * result + Arrays.hashCode(getBooleans());
    result = 31 * result + Arrays.hashCode(getChars());
    result = 31 * result + Arrays.hashCode(getBytes());
    result = 31 * result + Arrays.hashCode(getDoubles());
    result = 31 * result + Arrays.hashCode(getInts());
    result = 31 * result + Arrays.hashCode(getLongs());
    result = 31 * result + Arrays.hashCode(getShorts());
    return result;
  }

  @Override
  public String toString() {
    return "PrimitiveArraysUnwrapped{"
        + "\nstrings="
        + Arrays.toString(strings)
        + ",\n booleans="
        + Arrays.toString(booleans)
        + ",\n chars="
        + Arrays.toString(chars)
        + ",\n bytes="
        + Arrays.toString(bytes)
        + ",\n doubles="
        + Arrays.toString(doubles)
        + ",\n ints="
        + Arrays.toString(ints)
        + ",\n longs="
        + Arrays.toString(longs)
        + ",\n shorts="
        + Arrays.toString(shorts)
        + '}';
  }
}
