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
package org.kie.j2cl.tools.xml.mapper.client.tests.arrays;

import java.util.Arrays;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 3/28/20 */
@XMLMapper
public class BoxedArrays {

  private String[] strings;

  private Boolean[] booleans;

  private Character[] chars;

  private Byte[] bytes;

  private Double[] doubles;

  private Integer[] ints;

  private Long[] longs;

  private Short[] shorts;

  public BoxedArrays() {}

  public BoxedArrays(
      String[] strings,
      Boolean[] booleans,
      Character[] chars,
      Byte[] bytes,
      Double[] doubles,
      Integer[] ints,
      Long[] longs,
      Short[] shorts) {
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

  public Boolean[] getBooleans() {
    return booleans;
  }

  public void setBooleans(Boolean[] booleans) {
    this.booleans = booleans;
  }

  public Character[] getChars() {
    return chars;
  }

  public void setChars(Character[] chars) {
    this.chars = chars;
  }

  public Byte[] getBytes() {
    return bytes;
  }

  public void setBytes(Byte[] bytes) {
    this.bytes = bytes;
  }

  public Double[] getDoubles() {
    return doubles;
  }

  public void setDoubles(Double[] doubles) {
    this.doubles = doubles;
  }

  public Integer[] getInts() {
    return ints;
  }

  public void setInts(Integer[] ints) {
    this.ints = ints;
  }

  public Long[] getLongs() {
    return longs;
  }

  public void setLongs(Long[] longs) {
    this.longs = longs;
  }

  public Short[] getShorts() {
    return shorts;
  }

  public void setShorts(Short[] shorts) {
    this.shorts = shorts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BoxedArrays)) {
      return false;
    }
    BoxedArrays that = (BoxedArrays) o;
    return Arrays.equals(strings, that.strings)
        && Arrays.equals(booleans, that.booleans)
        && Arrays.equals(chars, that.chars)
        && Arrays.equals(bytes, that.bytes)
        && Arrays.equals(doubles, that.doubles)
        && Arrays.equals(ints, that.ints)
        && Arrays.equals(longs, that.longs)
        && Arrays.equals(shorts, that.shorts);
  }

  @Override
  public int hashCode() {
    int result = Arrays.hashCode(strings);
    result = 31 * result + Arrays.hashCode(booleans);
    result = 31 * result + Arrays.hashCode(chars);
    result = 31 * result + Arrays.hashCode(bytes);
    result = 31 * result + Arrays.hashCode(doubles);
    result = 31 * result + Arrays.hashCode(ints);
    result = 31 * result + Arrays.hashCode(longs);
    result = 31 * result + Arrays.hashCode(shorts);
    return result;
  }
}
