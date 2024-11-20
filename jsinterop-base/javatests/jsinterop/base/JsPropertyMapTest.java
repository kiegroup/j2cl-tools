/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package jsinterop.base;

import static com.google.common.truth.Truth.assertThat;

import com.google.gwt.junit.client.GWTTestCase;

public class JsPropertyMapTest extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "jsinterop.base.TestModule";
  }

  public void testOf() {
    // Check primitives not boxed.
    assertThat(JsPropertyMap.of("a", 42).getAsAny("a").asInt()).isEqualTo(42);
    assertThat(JsPropertyMap.of("", "", "a", 42).getAsAny("a").asInt()).isEqualTo(42);
    assertThat(JsPropertyMap.of("", "", "", "", "a", 42).getAsAny("a").asInt()).isEqualTo(42);
  }

  public void testSetGet() {
    JsPropertyMap<Object> o = JsPropertyMap.of();
    assertThat(o.get("foo")).isNull();
    o.set("foo", "str");
    assertThat(o.get("foo")).isEqualTo("str");
  }

  public void testNestedGet() {
    JsPropertyMap<Object> o = JsPropertyMap.of();
    o.set("a", JsPropertyMap.of("b", JsPropertyMap.of("c", o)));
    assertThat(o.nestedGet("a.b.c")).isSameInstanceAs(o);
    assertThat(o.nestedGet("k.l.m")).isNull();
  }

  public void testDeleteHas() {
    JsPropertyMap<Object> o = JsPropertyMap.of();
    assertThat(o.has("foo")).isFalse();
    o.set("foo", null);
    assertThat(o.has("foo")).isTrue();
    o.delete("foo");
    assertThat(o.has("foo")).isFalse();
  }

  public void testSetgetAsAny() {
    JsPropertyMap<Object> o = JsPropertyMap.of();
    o.set("p0", 15.5d);
    o.set("p1", 15.5f);
    o.set("p2", 15L);
    o.set("p3", 15);
    o.set("p4", (short) 15);
    o.set("p5", (char) 15);
    o.set("p6", (byte) 15);
    o.set("p7", true);

    assertThat(o.getAsAny("p0").asDouble()).isEqualTo(15.5);
    assertThat(o.getAsAny("p1").asDouble()).isEqualTo(15.5);
    assertThat(o.getAsAny("p2").asLong()).isEqualTo(15L);
    assertThat(o.getAsAny("p3").asInt()).isEqualTo(15);
    assertThat(o.getAsAny("p4").asShort()).isEqualTo(15);
    assertThat(o.getAsAny("p5").asChar()).isEqualTo(15);
    assertThat(o.getAsAny("p6").asByte()).isEqualTo(15);
    assertThat(o.getAsAny("p7").asBoolean()).isTrue();
  }

  public void testForEach() {
    StringBuilder result = new StringBuilder();
    JsPropertyMap.of("foo", "", "bar", "").forEach(t -> result.append(t));
    assertThat(result.toString()).isEqualTo("foobar");
  }
}
