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

import java.util.Objects;
import java.util.Set;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 3/29/20 */
@XMLMapper
public class BoxedSets {

  private Set<String> strings;

  private Set<Boolean> booleans;

  private Set<Character> chars;

  private Set<Byte> bytes;

  private Set<Double> doubles;

  private Set<Integer> ints;

  private Set<Long> longs;

  private Set<Short> shorts;

  public BoxedSets() {}

  public BoxedSets(
      Set<String> strings,
      Set<Boolean> booleans,
      Set<Character> chars,
      Set<Byte> bytes,
      Set<Double> doubles,
      Set<Integer> ints,
      Set<Long> longs,
      Set<Short> shorts) {
    this.strings = strings;
    this.booleans = booleans;
    this.chars = chars;
    this.bytes = bytes;
    this.doubles = doubles;
    this.ints = ints;
    this.longs = longs;
    this.shorts = shorts;
  }

  public Set<String> getStrings() {
    return strings;
  }

  public void setStrings(Set<String> strings) {
    this.strings = strings;
  }

  public Set<Boolean> getBooleans() {
    return booleans;
  }

  public void setBooleans(Set<Boolean> booleans) {
    this.booleans = booleans;
  }

  public Set<Character> getChars() {
    return chars;
  }

  public void setChars(Set<Character> chars) {
    this.chars = chars;
  }

  public Set<Byte> getBytes() {
    return bytes;
  }

  public void setBytes(Set<Byte> bytes) {
    this.bytes = bytes;
  }

  public Set<Double> getDoubles() {
    return doubles;
  }

  public void setDoubles(Set<Double> doubles) {
    this.doubles = doubles;
  }

  public Set<Integer> getInts() {
    return ints;
  }

  public void setInts(Set<Integer> ints) {
    this.ints = ints;
  }

  public Set<Long> getLongs() {
    return longs;
  }

  public void setLongs(Set<Long> longs) {
    this.longs = longs;
  }

  public Set<Short> getShorts() {
    return shorts;
  }

  public void setShorts(Set<Short> shorts) {
    this.shorts = shorts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BoxedSets)) {
      return false;
    }
    BoxedSets boxedSets = (BoxedSets) o;
    return Objects.equals(getStrings(), boxedSets.getStrings())
        && Objects.equals(getBooleans(), boxedSets.getBooleans())
        && Objects.equals(getChars(), boxedSets.getChars())
        && Objects.equals(getBytes(), boxedSets.getBytes())
        && Objects.equals(getDoubles(), boxedSets.getDoubles())
        && Objects.equals(getInts(), boxedSets.getInts())
        && Objects.equals(getLongs(), boxedSets.getLongs())
        && Objects.equals(getShorts(), boxedSets.getShorts());
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
}
