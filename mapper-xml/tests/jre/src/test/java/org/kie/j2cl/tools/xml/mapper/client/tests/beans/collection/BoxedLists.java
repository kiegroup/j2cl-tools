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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection;

import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 3/29/20 */
@XMLMapper
public class BoxedLists {

  private List<String> strings;

  private List<Boolean> booleans;

  private List<Character> chars;

  private List<Byte> bytes;

  private List<Double> doubles;

  private List<Integer> ints;

  private List<Long> longs;

  private List<Short> shorts;

  public BoxedLists() {}

  public BoxedLists(
      List<String> strings,
      List<Boolean> booleans,
      List<Character> chars,
      List<Byte> bytes,
      List<Double> doubles,
      List<Integer> ints,
      List<Long> longs,
      List<Short> shorts) {
    this.strings = strings;
    this.booleans = booleans;
    this.chars = chars;
    this.bytes = bytes;
    this.doubles = doubles;
    this.ints = ints;
    this.longs = longs;
    this.shorts = shorts;
  }

  public List<String> getStrings() {
    return strings;
  }

  public void setStrings(List<String> strings) {
    this.strings = strings;
  }

  public List<Boolean> getBooleans() {
    return booleans;
  }

  public void setBooleans(List<Boolean> booleans) {
    this.booleans = booleans;
  }

  public List<Character> getChars() {
    return chars;
  }

  public void setChars(List<Character> chars) {
    this.chars = chars;
  }

  public List<Byte> getBytes() {
    return bytes;
  }

  public void setBytes(List<Byte> bytes) {
    this.bytes = bytes;
  }

  public List<Double> getDoubles() {
    return doubles;
  }

  public void setDoubles(List<Double> doubles) {
    this.doubles = doubles;
  }

  public List<Integer> getInts() {
    return ints;
  }

  public void setInts(List<Integer> ints) {
    this.ints = ints;
  }

  public List<Long> getLongs() {
    return longs;
  }

  public void setLongs(List<Long> longs) {
    this.longs = longs;
  }

  public List<Short> getShorts() {
    return shorts;
  }

  public void setShorts(List<Short> shorts) {
    this.shorts = shorts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BoxedLists)) {
      return false;
    }
    BoxedLists boxedList = (BoxedLists) o;
    return Objects.equals(getStrings(), boxedList.getStrings())
        && Objects.equals(getBooleans(), boxedList.getBooleans())
        && Objects.equals(getChars(), boxedList.getChars())
        && Objects.equals(getBytes(), boxedList.getBytes())
        && Objects.equals(getDoubles(), boxedList.getDoubles())
        && Objects.equals(getInts(), boxedList.getInts())
        && Objects.equals(getLongs(), boxedList.getLongs())
        && Objects.equals(getShorts(), boxedList.getShorts());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getStrings(),
        getBooleans(),
        getChars(),
        getBytes(),
        getDoubles(),
        getInts(),
        getLongs(),
        getShorts());
  }

  @Override
  public String toString() {
    return "BoxedLists{"
        + "strings="
        + strings
        + ", booleans="
        + booleans
        + ", chars="
        + chars
        + ", bytes="
        + bytes
        + ", doubles="
        + doubles
        + ", ints="
        + ints
        + ", longs="
        + longs
        + ", shorts="
        + shorts
        + '}';
  }
}
