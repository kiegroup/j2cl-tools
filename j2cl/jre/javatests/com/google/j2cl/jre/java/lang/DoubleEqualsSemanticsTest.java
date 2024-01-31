/*
 * Copyright 2019 Google Inc.
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
 */
package com.google.j2cl.jre.java.lang;

import com.google.gwt.junit.client.GWTTestCase;

/** Tests equals semantics for Double. */
public final class DoubleEqualsSemanticsTest extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.google.gwt.emultest.EmulSuite";
  }

  public void testEquals() {
    assertTrue(Double.valueOf(Double.NaN).equals(Double.NaN));
    assertFalse(Double.valueOf(0.0d).equals(-0.0d));

    // Also make sure the behavior doesn't change when Object trampoline is used.
    Object o;
    o = Double.valueOf(Double.NaN);
    assertTrue(o.equals(Double.NaN));
    o = Double.valueOf(0.0d);
    assertFalse(o.equals(-0.0d));
  }
}
