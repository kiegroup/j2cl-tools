/*
 * Copyright 2010 Google Inc.
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

import junit.framework.TestCase;
import org.jspecify.annotations.NullMarked;

/** Tests for JRE emulation of java.lang.Math. */
@NullMarked
public class MathTest extends TestCase {

  private static void assertNegativeZero(String description, double x) {
    assertTrue(description, isNegativeZero(x));
  }

  private static void assertNegativeZero(double x) {
    assertTrue(isNegativeZero(x));
  }

  private static void assertNegativeZero(float x) {
    assertTrue(isNegativeZero(x));
  }

  private static void assertPositiveZero(String description, double x) {
    assertEquals(description, 0.0, x);
    assertFalse(description, isNegativeZero(x));
  }

  private static void assertPositiveZero(double x) {
    assertEquals(0.0, x);
    assertFalse(isNegativeZero(x));
  }

  private static void assertPositiveZero(float x) {
    assertEquals(0.0f, x);
    assertFalse(isNegativeZero(x));
  }

  private static void assertNaN(double x) {
    assertTrue(Double.isNaN(x));
  }

  private static void assertNaN(String description, double x) {
    assertTrue(description + x, Double.isNaN(x));
  }

  private static void assertNaN(float x) {
    assertTrue(Float.isNaN(x));
  }

  private static void assertEquals(double expected, double actual) {
    assertEquals(expected, actual, 0.0);
  }

  private static void assertEquals(float expected, float actual) {
    assertEquals(expected, actual, 0.0f);
  }

  private static boolean isNegativeZero(double x) {
    return Double.doubleToLongBits(-0.0) == Double.doubleToLongBits(x);
  }

  private static boolean isNegativeZero(float x) {
    return Float.floatToIntBits(-0.0f) == Float.floatToIntBits(x);
  }

  @Override
  public String getModuleName() {
    return "com.google.gwt.emultest.EmulSuite";
  }

  @Override
  protected void gwtSetUp() throws Exception {
    // Ensure -0.0 vs 0.0 behavior
    assertPositiveZero(0.0);
    assertNegativeZero(-0.0);
    assertFalse(isNegativeZero(0.0));
  }

  public void testAbs() {
    double v = Math.abs(-1.0);
    assertEquals(1.0, v);
    v = Math.abs(1.0);
    assertEquals(1.0, v);
    v = Math.abs(-0.0);
    assertPositiveZero(v);
    v = Math.abs(0.0);
    assertPositiveZero(v);
    v = Math.abs(Double.NEGATIVE_INFINITY);
    assertEquals(Double.POSITIVE_INFINITY, v);
    v = Math.abs(Double.POSITIVE_INFINITY);
    assertEquals(Double.POSITIVE_INFINITY, v);
    v = Math.abs(Double.NaN);
    assertNaN(v);
  }

  public void testAbs_int() {
    assertEquals(1, Math.abs(-1));
    assertEquals(1, Math.abs(1));
    assertEquals(0, Math.abs(0));
    assertEquals(Integer.MAX_VALUE, Math.abs(Integer.MAX_VALUE));
    assertEquals(Integer.MIN_VALUE, Math.abs(Integer.MIN_VALUE));
  }

  public void testAbs_long() {
    assertEquals(1L, Math.abs(-1L));
    assertEquals(1L, Math.abs(1L));
    assertEquals(0L, Math.abs(0L));
    assertEquals(Long.MAX_VALUE, Math.abs(Long.MAX_VALUE));
    assertEquals(Long.MIN_VALUE, Math.abs(Long.MIN_VALUE));
  }

  public void testAddExact_int() {
    assertEquals(2, Math.addExact(1, 1));
    assertEquals(-2, Math.addExact(-1, -1));
    assertEquals(0, Math.addExact(0, 0));
    assertEquals(Integer.MIN_VALUE, Math.addExact(Integer.MIN_VALUE + 1, -1));
    assertEquals(Integer.MAX_VALUE, Math.addExact(Integer.MAX_VALUE - 1, 1));

    try {
      Math.addExact(Integer.MAX_VALUE, 1);
      fail("addExact(Integer.MAX_VALUE, 1)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.addExact(1, Integer.MAX_VALUE);
      fail("addExact(1, Integer.MAX_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.addExact(Integer.MIN_VALUE, -1);
      fail("addExact(Integer.MIN_VALUE, -1)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testAddExact_long() {
    assertEquals(2L, Math.addExact(1L, 1L));
    assertEquals(-2L, Math.addExact(-1L, -1L));
    assertEquals(0L, Math.addExact(0L, 0L));
    assertEquals(Long.MIN_VALUE, Math.addExact(Long.MIN_VALUE + 1L, -1L));
    assertEquals(Long.MAX_VALUE, Math.addExact(Long.MAX_VALUE - 1L, 1L));

    try {
      Math.addExact(Long.MAX_VALUE, 1L);
      fail("addExact(Long.MAX_VALUE, 1L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.addExact(1L, Long.MAX_VALUE);
      fail("addExact(1L, Long.MAX_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.addExact(Long.MIN_VALUE, -1L);
      fail("addExact(Long.MIN_VALUE, -1L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testAsin() {
    assertNaN(Math.asin(Double.NaN));
    assertNaN(Math.asin(1.1));
    assertNaN(Math.asin(Double.NEGATIVE_INFINITY));
    assertNaN(Math.asin(Double.POSITIVE_INFINITY));
    assertPositiveZero(Math.asin(0.0));
    assertNegativeZero(Math.asin(-0.0));

    assertEquals(0.0, Math.asin(0));
    assertEquals(1.570796326, Math.asin(1), 1e-7);
  }

  public void testAcos() {
    assertNaN(Math.acos(Double.NaN));
    assertNaN(Math.acos(1.1));
    assertNaN(Math.acos(Double.NEGATIVE_INFINITY));
    assertNaN(Math.acos(Double.POSITIVE_INFINITY));

    assertEquals(0.0, Math.acos(1));
    assertEquals(1.570796326, Math.acos(0), 1e-7);
  }

  public void testAtan() {
    assertNaN(Math.atan(Double.NaN));
    assertPositiveZero(Math.atan(0.0));
    assertNegativeZero(Math.atan(-0.0));
    assertEquals(-1.570796326, Math.atan(Double.NEGATIVE_INFINITY), 1e-7);
    assertEquals(1.570796326, Math.atan(Double.POSITIVE_INFINITY), 1e-7);
    assertEquals(0.785398163, Math.atan(1), 1e-7);
  }

  public void testAtan2() {
    assertNaN(Math.atan2(Double.NaN, 1));
    assertNaN(Math.atan2(1, Double.NaN));
    assertNaN(Math.atan2(Double.NaN, Double.NaN));
    assertPositiveZero(Math.atan2(0.0, 1.0));
    assertPositiveZero(Math.atan2(1.0, Double.POSITIVE_INFINITY));
    assertNegativeZero(Math.atan2(-0.0, 1.0));
    assertNegativeZero(Math.atan2(-1.0, Double.POSITIVE_INFINITY));
    assertEquals(Math.PI, Math.atan2(0.0, -1.0), 1e-7);
    assertEquals(Math.PI, Math.atan2(1.0, Double.NEGATIVE_INFINITY), 1e-7);
    assertEquals(-Math.PI, Math.atan2(-0.0, -1.0), 1e-7);
    assertEquals(-Math.PI, Math.atan2(-1.0, Double.NEGATIVE_INFINITY), 1e-7);
    assertEquals(Math.PI / 2, Math.atan2(1.0, 0.0), 1e-7);
    assertEquals(Math.PI / 2, Math.atan2(1.0, -0.0), 1e-7);
    assertEquals(Math.PI / 2, Math.atan2(Double.POSITIVE_INFINITY, 1.0), 1e-7);
    assertEquals(-Math.PI / 2, Math.atan2(-1.0, 0.0), 1e-7);
    assertEquals(-Math.PI / 2, Math.atan2(-1.0, -0.0), 1e-7);
    assertEquals(-Math.PI / 2, Math.atan2(Double.NEGATIVE_INFINITY, 1.0), 1e-7);
    assertEquals(Math.PI / 4, Math.atan2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), 1e-7);
    assertEquals(Math.PI * 3 / 4,
        Math.atan2(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY), 1e-7);
    assertEquals(-Math.PI / 4,
        Math.atan2(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), 1e-7);
    assertEquals(-3 * Math.PI / 4,
        Math.atan2(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), 1e-7);

    assertEquals(0.463647609, Math.atan2(1, 2), 1e-7);
  }

  public void testCbrt() {
    assertNaN(Math.cbrt(Double.NaN));
    assertEquals(Double.POSITIVE_INFINITY, Math.cbrt(Double.POSITIVE_INFINITY));
    assertEquals(Double.NEGATIVE_INFINITY, Math.cbrt(Double.NEGATIVE_INFINITY));
    assertPositiveZero(Math.cbrt(0.0));
    assertNegativeZero(Math.cbrt(-0.0));

    double v = Math.cbrt(1000.0);
    assertEquals(10.0, v, 1e-7);
  }

  public void testCeil() {
    assertNaN(Math.ceil(Double.NaN));
    assertEquals(Double.POSITIVE_INFINITY, Math.ceil(Double.POSITIVE_INFINITY));
    assertEquals(Double.NEGATIVE_INFINITY, Math.ceil(Double.NEGATIVE_INFINITY));
    assertPositiveZero(Math.ceil(0.0));
    assertNegativeZero(Math.ceil(-0.0));

    assertEquals(1.0, Math.ceil(0.5));
    assertNegativeZero(Math.ceil(-0.5));
  }

  public void testCopySign() {
    assertEquals(3.0, Math.copySign(3.0, 2.0));
    assertEquals(3.0, Math.copySign(-3.0, 2.0));
    assertEquals(-3.0, Math.copySign(3.0, -2.0));
    assertEquals(-3.0, Math.copySign(-3.0, -2.0));

    assertEquals(2.0, Math.copySign(2.0, 0.0));
    assertEquals(2.0, Math.copySign(-2.0, 0.0));
    assertEquals(-2.0, Math.copySign(2.0, -0.0));
    assertEquals(-2.0, Math.copySign(-2.0, -0.0));
    assertEquals(-2.0, Math.copySign(-2.0, Double.NEGATIVE_INFINITY));
    assertEquals(2.0, Math.copySign(-2.0, Double.POSITIVE_INFINITY));
    assertEquals(2.0, Math.copySign(-2.0, Double.NaN));

    assertPositiveZero(Math.copySign(0.0, 4.0));
    assertPositiveZero(Math.copySign(-0.0, 4.0));
    assertNegativeZero(Math.copySign(0.0, -4.0));
    assertNegativeZero(Math.copySign(-0.0, -4.0));

    assertPositiveZero(Math.copySign(0.0, 0.0));
    assertPositiveZero(Math.copySign(-0.0, 0.0));
    assertNegativeZero(Math.copySign(0.0, -0.0));
    assertNegativeZero(Math.copySign(-0.0, -0.0));

    assertEquals(Double.POSITIVE_INFINITY, Math.copySign(Double.POSITIVE_INFINITY, 1));
    assertEquals(Double.NEGATIVE_INFINITY, Math.copySign(Double.POSITIVE_INFINITY, -1));
    assertEquals(Double.POSITIVE_INFINITY, Math.copySign(Double.NEGATIVE_INFINITY, 1));
    assertEquals(Double.NEGATIVE_INFINITY, Math.copySign(Double.NEGATIVE_INFINITY, -1));

    assertNaN(Math.copySign(Double.NaN, 1));
    assertNaN(Math.copySign(Double.NaN, -1));
  }

  public void testCos() {
    double v = Math.cos(0.0);
    assertEquals(1.0, v, 1e-7);
    v = Math.cos(-0.0);
    assertEquals(1.0, v, 1e-7);
    v = Math.cos(Math.PI * .5);
    assertEquals(0.0, v, 1e-7);
    v = Math.cos(Math.PI);
    assertEquals(-1.0, v, 1e-7);
    v = Math.cos(Math.PI * 1.5);
    assertEquals(0.0, v, 1e-7);
    v = Math.cos(Double.NaN);
    assertNaN(v);
    v = Math.cos(Double.NEGATIVE_INFINITY);
    assertNaN(v);
    v = Math.cos(Double.POSITIVE_INFINITY);
    assertNaN(v);
  }

  public void testCosh() {
    double v = Math.cosh(0.0);
    assertEquals(1.0, v, 1e-7);
    v = Math.cosh(1.0);
    assertEquals(1.5430806348, v, 1e-7);
    v = Math.cosh(-1.0);
    assertEquals(1.5430806348, v, 1e-7);
    v = Math.cosh(Double.NaN);
    assertNaN(v);
    v = Math.cosh(Double.NEGATIVE_INFINITY);
    assertEquals(Double.POSITIVE_INFINITY, v);
    v = Math.cosh(Double.POSITIVE_INFINITY);
    assertEquals(Double.POSITIVE_INFINITY, v);
  }

  public void testDecrementExact_int() {
    assertEquals(-1, Math.decrementExact(0));
    assertEquals(Integer.MIN_VALUE, Math.decrementExact(Integer.MIN_VALUE + 1));

    try {
      Math.decrementExact(Integer.MIN_VALUE);
      fail("decrementExact(Integer.MIN_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testDecrementExact_long() {
    assertEquals(-1L, Math.decrementExact(0L));
    assertEquals(Long.MIN_VALUE, Math.decrementExact(Long.MIN_VALUE + 1L));

    try {
      Math.decrementExact(Long.MIN_VALUE);
      fail("decrementExact(Long.MIN_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testExp() {
    assertNaN(Math.exp(Double.NaN));
    assertEquals(Double.POSITIVE_INFINITY, Math.exp(Double.POSITIVE_INFINITY));
    assertPositiveZero(Math.exp(Double.NEGATIVE_INFINITY));
    assertEquals(1, Math.exp(0));
    assertEquals(2.718281, Math.exp(1), 0.000001);
  }

  public void testExpm1() {
    assertNegativeZero(Math.expm1(-0.0));
    assertPositiveZero(Math.expm1(0.0));
    assertNaN(Math.expm1(Double.NaN));
    assertEquals(Double.POSITIVE_INFINITY, Math.expm1(Double.POSITIVE_INFINITY));
    assertEquals(-1.0, Math.expm1(Double.NEGATIVE_INFINITY));
    assertEquals(-0.632, Math.expm1(-1), 0.001);
    assertEquals(1.718, Math.expm1(1), 0.001);
  }

  public void testFloor() {
    double v = Math.floor(0.5);
    assertEquals(0, v, 0);
    v = Math.floor(Double.POSITIVE_INFINITY);
    assertEquals(Double.POSITIVE_INFINITY, v);
    v = Math.floor(Double.NEGATIVE_INFINITY);
    assertEquals(Double.NEGATIVE_INFINITY, v);
    v = Math.floor(Double.NaN);
    assertNaN(v);
    assertPositiveZero(Math.floor(0.0));
    assertNegativeZero(Math.floor(-0.0));

    v = Math.floor(Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, v, 0);
    v = Math.floor(-Double.MAX_VALUE);
    assertEquals(-Double.MAX_VALUE, v, 0);
  }

  public void testHypot() {
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(0, Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(0, Double.NEGATIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.POSITIVE_INFINITY, 0));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.NEGATIVE_INFINITY, 0));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.NaN, Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.NaN, Double.NEGATIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.POSITIVE_INFINITY, Double.NaN));
    assertEquals(Double.POSITIVE_INFINITY,
        Math.hypot(Double.NEGATIVE_INFINITY, Double.NaN));
    assertNaN(Math.hypot(Double.NaN, 0));
    assertNaN(Math.hypot(0, Double.NaN));

    assertEquals(1.414213562, Math.hypot(1, 1), 1e-7);
    assertEquals(5, Math.hypot(3, 4));
  }

  public void testIncrementExact_int() {
    assertEquals(1, Math.incrementExact(0));
    assertEquals(Integer.MAX_VALUE, Math.incrementExact(Integer.MAX_VALUE - 1));

    try {
      Math.incrementExact(Integer.MAX_VALUE);
      fail("incrementExact(Integer.MAX_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testIncrementExact_long() {
    assertEquals(1L, Math.incrementExact(0L));
    assertEquals(Long.MAX_VALUE, Math.incrementExact(Long.MAX_VALUE - 1L));

    try {
      Math.incrementExact(Long.MAX_VALUE);
      fail("incrementExact(Long.MAX_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testLog() {
    assertNaN(Math.log(Double.NaN));
    assertNaN(Math.log(Double.NEGATIVE_INFINITY));
    assertNaN(Math.log(-1));
    assertEquals(Double.POSITIVE_INFINITY, Math.log(Double.POSITIVE_INFINITY));
    assertEquals(Double.NEGATIVE_INFINITY, Math.log(0.0));
    assertEquals(Double.NEGATIVE_INFINITY, Math.log(-0.0));

    double v = Math.log(Math.E);
    assertEquals(1.0, v, 1e-15);

    for (double d = -10; d < 10; d += 0.5) {
      double answer = Math.log(Math.exp(d));
      assertEquals(d, answer, 0.000000001);
    }
  }

  public void testLog10() {
    assertNaN(Math.log10(Double.NaN));
    assertNaN(Math.log10(Double.NEGATIVE_INFINITY));
    assertNaN(Math.log10(-1));
    assertNaN(Math.log10(-2541.057456872342));
    assertNaN(Math.log10(-0.1));
    assertEquals(Double.POSITIVE_INFINITY, Math.log10(Double.POSITIVE_INFINITY));
    assertEquals(Double.NEGATIVE_INFINITY, Math.log10(0.0));
    assertEquals(Double.NEGATIVE_INFINITY, Math.log10(-0.0));
    assertEquals(3.0, Math.log10(1000.0), 1e-15);
    assertEquals(14.0, Math.log10(Math.pow(10, 14)));
    assertEquals(3.73895612695404, Math.log10(5482.2158), 1e-15);
    assertEquals(308.25471555991675, Math.log10(Double.MAX_VALUE));
    assertEquals(-323.30621534311575, Math.log10(Double.MIN_VALUE), 1e-10);
  }

  public void testLog1p() {
    assertNaN("log1p(NaN)", Math.log1p(Double.NaN));
    assertNaN("log1p(-1.1)", Math.log1p(-1.1));
    assertNaN("log1p(-2)", Math.log1p(-2));
    assertNaN("log1p(-INF)", Math.log1p(Double.NEGATIVE_INFINITY));
    assertEquals("log1p(INF)", Double.POSITIVE_INFINITY, Math.log1p(Double.POSITIVE_INFINITY));
    assertEquals("log1p(-1)", Double.NEGATIVE_INFINITY, Math.log1p(-1));
    assertEquals("log1p(MIN)", Double.MIN_VALUE, Math.log1p(Double.MIN_VALUE), 1e-25);
    assertEquals("log1p(MAX)", 709.782712893384, Math.log1p(Double.MAX_VALUE));
    assertPositiveZero("log1p(0.0)", Math.log1p(0.0));
    assertNegativeZero("log1p(-0.0)", Math.log1p(-0.0));

    assertEquals(-0.693147180, Math.log1p(-0.5), 1e-7);
    assertEquals(1.313261687, Math.log1p(Math.E), 1e-7);
    assertEquals(-0.2941782295312541, Math.log1p(-0.254856327), 1e-7);
    assertEquals(7.368050685564151, Math.log1p(1583.542));
    assertEquals(0.4633708685409921, Math.log1p(0.5894227), 1e-15);
  }

  public void testMax() {
    assertEquals(2d, Math.max(1d, 2d));
    assertEquals(2d, Math.max(2d, 1d));
    assertEquals(0d, Math.max(-0d, 0d));
    assertEquals(0d, Math.max(0d, -0d));
    assertEquals(1d, Math.max(-1d, 1d));
    assertEquals(1d, Math.max(1d, -1d));
    assertEquals(-1d, Math.max(-1d, -2d));
    assertEquals(-1d, Math.max(-2d, -1d));
    assertNaN(Math.max(Double.NaN, 1d));
    assertNaN(Math.max(1d, Double.NaN));
    assertNaN(Math.max(Double.NaN, Double.POSITIVE_INFINITY));
    assertNaN(Math.max(Double.POSITIVE_INFINITY, Double.NaN));
    assertNaN(Math.max(Double.NaN, Double.NEGATIVE_INFINITY));
    assertNaN(Math.max(Double.NEGATIVE_INFINITY, Double.NaN));

    assertEquals(2f, Math.max(1f, 2f));
    assertEquals(2f, Math.max(2f, 1f));
    assertEquals(0f, Math.max(-0f, 0f));
    assertEquals(0f, Math.max(0f, -0f));
    assertEquals(1f, Math.max(-1f, 1f));
    assertEquals(1f, Math.max(1f, -1f));
    assertEquals(-1f, Math.max(-1f, -2f));
    assertEquals(-1f, Math.max(-2f, -1f));
    assertTrue(Float.isNaN(Math.max(Float.NaN, 1f)));
    assertTrue(Float.isNaN(Math.max(1f, Float.NaN)));
    assertTrue(Float.isNaN(Math.max(Float.NaN, Float.POSITIVE_INFINITY)));
    assertTrue(Float.isNaN(Math.max(Float.POSITIVE_INFINITY, Float.NaN)));
    assertTrue(Float.isNaN(Math.max(Float.NaN, Float.NEGATIVE_INFINITY)));
    assertTrue(Float.isNaN(Math.max(Float.NEGATIVE_INFINITY, Float.NaN)));
  }

  public void testMin() {
    assertEquals(1d, Math.min(1d, 2d));
    assertEquals(1d, Math.min(2d, 1d));
    assertEquals(-0d, Math.min(-0d, 0d));
    assertEquals(-0d, Math.min(0d, -0d));
    assertEquals(-1d, Math.min(-1d, 1d));
    assertEquals(-1d, Math.min(1d, -1d));
    assertEquals(-2d, Math.min(-1d, -2d));
    assertEquals(-2d, Math.min(-2d, -1d));
    assertNaN(Math.min(Double.NaN, 1d));
    assertNaN(Math.min(1d, Double.NaN));
    assertNaN(Math.min(Double.NaN, Double.POSITIVE_INFINITY));
    assertNaN(Math.min(Double.POSITIVE_INFINITY, Double.NaN));
    assertNaN(Math.min(Double.NaN, Double.NEGATIVE_INFINITY));
    assertNaN(Math.min(Double.NEGATIVE_INFINITY, Double.NaN));

    assertEquals(1f, Math.min(1f, 2f));
    assertEquals(1f, Math.min(2f, 1f));
    assertEquals(-0f, Math.min(-0f, 0f));
    assertEquals(-0f, Math.min(0f, -0f));
    assertEquals(-1f, Math.min(-1f, 1f));
    assertEquals(-1f, Math.min(1f, -1f));
    assertEquals(-2f, Math.min(-1f, -2f));
    assertEquals(-2f, Math.min(-2f, -1f));
    assertTrue(Float.isNaN(Math.min(Float.NaN, 1f)));
    assertTrue(Float.isNaN(Math.min(1f, Float.NaN)));
    assertTrue(Float.isNaN(Math.min(Float.NaN, Float.POSITIVE_INFINITY)));
    assertTrue(Float.isNaN(Math.min(Float.POSITIVE_INFINITY, Float.NaN)));
    assertTrue(Float.isNaN(Math.min(Float.NaN, Float.NEGATIVE_INFINITY)));
    assertTrue(Float.isNaN(Math.min(Float.NEGATIVE_INFINITY, Float.NaN)));
  }

  public void testMultiplyExact_int() {
    assertEquals(0, Math.multiplyExact(0, 1));
    assertEquals(1, Math.multiplyExact(1, 1));
    assertEquals(-1, Math.multiplyExact(-1, 1));
    assertEquals(1, Math.multiplyExact(-1, -1));
    assertEquals(2, Math.multiplyExact(1, 2));
    assertEquals(4, Math.multiplyExact(2, 2));
    assertEquals(2147483646, Math.multiplyExact(1073741823, 2));
    assertEquals(-2147483648, Math.multiplyExact(-1073741824, 2));

    try {
      Math.multiplyExact(1073741824, 2);
      fail("multiplyExact(1073741824, 2)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.multiplyExact(-1073741825, 2);
      fail("multiplyExact(-1073741825, 2)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testMultiplyExact_long() {
    assertEquals(0L, Math.multiplyExact(0L, 1L));
    assertEquals(1L, Math.multiplyExact(1L, 1L));
    assertEquals(-1L, Math.multiplyExact(-1L, 1L));
    assertEquals(1L, Math.multiplyExact(-1L, -1L));
    assertEquals(2L, Math.multiplyExact(1L, 2L));
    assertEquals(4L, Math.multiplyExact(2L, 2L));
    assertEquals(9_223_372_036_854_775_806L, Math.multiplyExact(4_611_686_018_427_387_903L, 2L));
    assertEquals(-9_223_372_036_854_775_808L, Math.multiplyExact(-4_611_686_018_427_387_904L, 2L));

    try {
      Math.multiplyExact(4_611_686_018_427_387_904L, 2L);
      fail("multiplyExact(4_611_686_018_427_387_904L, 2L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.multiplyExact(-4_611_686_018_427_387_905L, 2L);
      fail("multiplyExact(-4_611_686_018_427_387_905L, 2L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testNegateExact_int() {
    assertEquals(-1, Math.negateExact(1));
    assertEquals(1, Math.negateExact(-1));
    assertEquals(-Integer.MAX_VALUE, Math.negateExact(Integer.MAX_VALUE));
    assertEquals(Integer.MAX_VALUE, Math.negateExact(Integer.MIN_VALUE + 1));

    try {
      Math.negateExact(Integer.MIN_VALUE);
      fail("negateExact(Integer.MIN_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testNegateExact_long() {
    assertEquals(-1L, Math.negateExact(1L));
    assertEquals(1L, Math.negateExact(-1L));
    assertEquals(-Long.MAX_VALUE, Math.negateExact(Long.MAX_VALUE));
    assertEquals(Long.MAX_VALUE, Math.negateExact(Long.MIN_VALUE + 1));

    try {
      Math.negateExact(Long.MIN_VALUE);
      fail("negateExact(Long.MIN_VALUE)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testPow() {
    assertEquals("pow(2, 0)", 1.0, Math.pow(2, 0.0));
    assertEquals("pow(2, -0)", 1.0, Math.pow(2, -0.0));
    assertEquals("pow(2, 1)", 2.0, Math.pow(2, 1));
    assertEquals("pow(-2, 1)", -2.0, Math.pow(-2, 1));
    assertNaN("pow(1, NaN)", Math.pow(1, Double.NaN));
    assertNaN("pow(NaN, NaN)", Math.pow(Double.NaN, Double.NaN));
    assertNaN("pow(NaN, 1)", Math.pow(Double.NaN, 1));
    assertEquals("pow(NaN, 0)", 1.0, Math.pow(Double.NaN, 0.0));
    assertEquals("pow(NaN, -0)", 1.0, Math.pow(Double.NaN, -0.0));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(1.1, Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(-1.1, Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(0.9, Double.NEGATIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(-0.9, Double.NEGATIVE_INFINITY));
    assertPositiveZero(Math.pow(1.1, Double.NEGATIVE_INFINITY));
    assertPositiveZero(Math.pow(-1.1, Double.NEGATIVE_INFINITY));
    assertPositiveZero(Math.pow(0.9, Double.POSITIVE_INFINITY));
    assertPositiveZero(Math.pow(-0.9, Double.POSITIVE_INFINITY));
    assertNaN(Math.pow(1, Double.POSITIVE_INFINITY));
    assertNaN(Math.pow(-1, Double.POSITIVE_INFINITY));
    assertNaN(Math.pow(1, Double.NEGATIVE_INFINITY));
    assertNaN(Math.pow(-1, Double.NEGATIVE_INFINITY));
    assertPositiveZero(Math.pow(0.0, 1));
    assertPositiveZero(Math.pow(Double.POSITIVE_INFINITY, -1));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(0.0, -1));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(Double.POSITIVE_INFINITY, 1));
    assertPositiveZero(Math.pow(-0.0, 2));
    assertPositiveZero(Math.pow(Double.NEGATIVE_INFINITY, -2));
    assertNegativeZero(Math.pow(-0.0, 1));
    assertNegativeZero(Math.pow(Double.NEGATIVE_INFINITY, -1));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(-0.0, -2));
    assertEquals(Double.POSITIVE_INFINITY, Math.pow(Double.NEGATIVE_INFINITY, 2));
    assertEquals(Double.NEGATIVE_INFINITY, Math.pow(-0.0, -1));
    assertEquals(Double.NEGATIVE_INFINITY, Math.pow(Double.NEGATIVE_INFINITY, 1));
    assertEquals(Double.NEGATIVE_INFINITY, Math.pow(-10.0, 3.093403029238847E15));

    assertEquals(9, Math.pow(3, 2));
  }

  public void testRound_float() {
    assertEquals(1, Math.round(0.5f));
    assertEquals(Integer.MAX_VALUE, Math.round(Float.POSITIVE_INFINITY));
    assertEquals(Integer.MIN_VALUE, Math.round(Float.NEGATIVE_INFINITY));
    assertEquals(0, Math.round(Float.NaN));
  }

  public void testRound() {
    long v = Math.round(0.5);
    assertEquals(1L, v);
    v = Math.round(Double.POSITIVE_INFINITY);
    assertEquals(Long.MAX_VALUE, v);
    v = Math.round(Double.NEGATIVE_INFINITY);
    assertEquals(Long.MIN_VALUE, v);
    v = Math.round(Double.NaN);
    assertEquals(0L, v);

    v = Math.round(Double.MAX_VALUE);
    assertEquals(Long.MAX_VALUE, v);
    v = Math.round(-Double.MAX_VALUE);
    assertEquals(Long.MIN_VALUE, v);
  }

  public void testRint() {
    final double twoTo52 = 1L << 52;
    // format: value to be round and expected value
    final double[] testValues = {
        0.0, 0.0,
        0.5, 0.0,
        0.75, 1,
        1.5, 2,
        1.75, 2,
        -0.0, -0.0,
        -0.5, -0.0,
        -1.25, -1,
        -1.5, -2,
        -2.5, -2,
        twoTo52, twoTo52,
        twoTo52 - 0.25, twoTo52,
        twoTo52 + 0.25, twoTo52,
        twoTo52 + 0.5, twoTo52,
        twoTo52 - 0.5, twoTo52,
        twoTo52 + 0.75, twoTo52 + 1,
        twoTo52 - 0.75, twoTo52 - 1,
        -twoTo52, -twoTo52,
        -twoTo52 + 0.25, -twoTo52,
        -twoTo52 - 0.25, -twoTo52,
        -twoTo52 + 0.5, -twoTo52,
        -twoTo52 - 0.5, -twoTo52,
        -twoTo52 + 0.75, -twoTo52 + 1,
        -twoTo52 - 0.75, -twoTo52 - 1,
        Double.MIN_VALUE, 0.0,
        Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
        Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
        Double.NaN, Double.NaN,
        Double.MAX_VALUE, Double.MAX_VALUE,
        -Double.MAX_VALUE, -Double.MAX_VALUE,
    };
    for (int i = 0; i < testValues.length;) {
      double v = testValues[i++];
      double expected = testValues[i++];
      double actual = Math.rint(v);
      assertEquals("value: " + v + ", expected: " + expected + ", actual: " + actual,
          expected, actual, 0);
    }
  }

  public void testSignum() {
    assertNaN(Math.signum(Double.NaN));
    assertNegativeZero(Math.signum(-0.0));
    assertPositiveZero(Math.signum(0.0));
    assertEquals(-1, Math.signum(-2));
    assertEquals(1, Math.signum(2));
    assertEquals(-1.0, Math.signum(-Double.MAX_VALUE));
    assertEquals(1.0, Math.signum(Double.MAX_VALUE));
    assertEquals(-1.0, Math.signum(Double.NEGATIVE_INFINITY));
    assertEquals(1.0, Math.signum(Double.POSITIVE_INFINITY));
  }

  public void testSignum_float() {
    assertNaN(Math.signum(Float.NaN));
    assertNegativeZero(Math.signum(-0.0f));
    assertPositiveZero(Math.signum(0.0f));
    assertEquals(-1f, Math.signum(-2f));
    assertEquals(1f, Math.signum(2f));
    assertEquals(-1.0f, Math.signum(-Float.MAX_VALUE));
    assertEquals(1.0f, Math.signum(Float.MAX_VALUE));
    assertEquals(-1.0f, Math.signum(Float.NEGATIVE_INFINITY));
    assertEquals(1.0f, Math.signum(Float.POSITIVE_INFINITY));
  }

  public void testSin() {
    double v = Math.sin(0.0);
    assertPositiveZero(v);
    v = Math.sin(-0.0);
    assertNegativeZero(v);
    v = Math.sin(Math.PI * .5);
    assertEquals(1.0, v, 1e-7);
    v = Math.sin(Math.PI);
    assertEquals(0.0, v, 1e-7);
    v = Math.sin(Math.PI * 1.5);
    assertEquals(-1.0, v, 1e-7);
    v = Math.sin(Double.NaN);
    assertNaN(v);
    v = Math.sin(Double.NEGATIVE_INFINITY);
    assertNaN(v);
    v = Math.sin(Double.POSITIVE_INFINITY);
    assertNaN(v);
  }

  public void testSinh() {
    double v = Math.sinh(0.0);
    assertPositiveZero(v);
    v = Math.sinh(-0.0);
    assertNegativeZero(v);
    v = Math.sinh(1.0);
    assertEquals(1.175201193, v, 1e-7);
    v = Math.sinh(-1.0);
    assertEquals(-1.175201193, v, 1e-7);
    v = Math.sinh(Double.NaN);
    assertNaN(v);
    v = Math.sinh(Double.NEGATIVE_INFINITY);
    assertEquals(Double.NEGATIVE_INFINITY, v);
    v = Math.sinh(Double.POSITIVE_INFINITY);
    assertEquals(Double.POSITIVE_INFINITY, v);
  }

  public void testSqrt() {
    assertNaN(Math.sqrt(Double.NaN));
    assertNaN(Math.sqrt(Double.NEGATIVE_INFINITY));
    assertNaN(Math.sqrt(-1));
    assertEquals(Double.POSITIVE_INFINITY, Math.sqrt(Double.POSITIVE_INFINITY));
    assertPositiveZero(Math.sqrt(0.0));
    assertNegativeZero(Math.sqrt(-0.0));

    assertEquals(1.732050807, Math.sqrt(3), 1e-7);
  }

  public void testSubtractExact_int() {
    assertEquals(1, Math.subtractExact(2, 1));
    assertEquals(-1, Math.subtractExact(-2, -1));
    assertEquals(0, Math.subtractExact(0, 0));
    assertEquals(Integer.MIN_VALUE, Math.subtractExact(Integer.MIN_VALUE + 1, 1));
    assertEquals(Integer.MAX_VALUE, Math.subtractExact(Integer.MAX_VALUE - 1, -1));

    try {
      Math.subtractExact(Integer.MIN_VALUE, 1);
      fail("subtractExact(Integer.MIN_VALUE, 1)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.subtractExact(Integer.MAX_VALUE, -1);
      fail("subtractExact(Integer.MAX_VALUE, -1)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testSubtractExact_long() {
    assertEquals(1L, Math.subtractExact(2L, 1L));
    assertEquals(-1L, Math.subtractExact(-2L, -1L));
    assertEquals(0L, Math.subtractExact(0L, 0L));
    assertEquals(Long.MIN_VALUE, Math.subtractExact(Long.MIN_VALUE + 1L, 1L));
    assertEquals(Long.MAX_VALUE, Math.subtractExact(Long.MAX_VALUE - 1L, -1L));

    try {
      Math.subtractExact(Long.MIN_VALUE, 1L);
      fail("subtractExact(Long.MIN_VALUE, 1L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.subtractExact(Long.MAX_VALUE, -1L);
      fail("subtractExact(Long.MAX_VALUE, -1L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testTan() {
    double v = Math.tan(0.0);
    assertPositiveZero(v);
    v = Math.tan(-0.0);
    assertNegativeZero(v);
    v = Math.tan(Double.NaN);
    assertNaN(v);
    v = Math.tan(Double.NEGATIVE_INFINITY);
    assertNaN(v);
    v = Math.tan(Double.POSITIVE_INFINITY);
    assertNaN(v);
  }

  public void testTanh() {
    double v = Math.tanh(0.0);
    assertPositiveZero(v);
    v = Math.tanh(-0.0);
    assertNegativeZero(v);
    v = Math.tanh(1.0);
    assertEquals(0.761594155, v, 1e-7);
    v = Math.tanh(-1.0);
    assertEquals(-0.761594155, v, 1e-7);
    v = Math.tanh(500);
    assertEquals(1.0, v, 1e-7);
    v = Math.tanh(-500);
    assertEquals(-1.0, v, 1e-7);
    v = Math.tanh(Double.NaN);
    assertNaN(v);
    v = Math.tanh(Double.MAX_VALUE);
    assertEquals(1.0, v, 1e-7);
    v = Math.tanh(Double.NEGATIVE_INFINITY);
    assertEquals(-1.0, v, 1e-7);
    v = Math.tanh(Double.POSITIVE_INFINITY);
    assertEquals(1.0, v, 1e-7);
  }

  public void testToIntExact() {
    assertEquals(0, Math.toIntExact(0L));
    assertEquals(Integer.MIN_VALUE, Math.toIntExact((long) Integer.MIN_VALUE));
    assertEquals(Integer.MAX_VALUE, Math.toIntExact((long) Integer.MAX_VALUE));

    try {
      Math.toIntExact((long) Integer.MIN_VALUE - 1L);
      fail("incrementExact(Integer.MIN_VALUE - 1L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }

    try {
      Math.toIntExact((long) Integer.MAX_VALUE + 1L);
      fail("incrementExact(Integer.MAX_VALUE + 1L)");
    } catch (ArithmeticException e) {
      // Expected behavior
    }
  }

  public void testScalb() {
    for (int scaleFactor = -32; scaleFactor <= 32; scaleFactor++) {
      assertNaN(Math.scalb(Double.NaN, scaleFactor));
      assertEquals(Double.POSITIVE_INFINITY, Math.scalb(Double.POSITIVE_INFINITY, scaleFactor));
      assertEquals(Double.NEGATIVE_INFINITY, Math.scalb(Double.NEGATIVE_INFINITY, scaleFactor));
      assertPositiveZero(Math.scalb(0.0, scaleFactor));
      assertNegativeZero(Math.scalb(-0.0, scaleFactor));
    }

    assertEquals(40.0d, Math.scalb(5d, 3));
    assertEquals(40.0f, Math.scalb(5f, 3));

    assertEquals(64.0d, Math.scalb(64d, 0));
    assertEquals(64.0f, Math.scalb(64f, 0));

    // Cases in which we can't use integer shift (|scaleFactor| >= 31):

    assertEquals(2147483648.0d, Math.scalb(1d, 31));
    assertEquals(4294967296.0d, Math.scalb(1d, 32));
    assertEquals(2.3283064e-10d, Math.scalb(1d, -32), 1e-7d);

    assertEquals(2147483648.0f, Math.scalb(1f, 31));
    assertEquals(4294967296.0f, Math.scalb(1f, 32));
    assertEquals(2.3283064e-10f, Math.scalb(1f, -32), 1e-7f);
  }

  public void testNextAfterFloat() {
    // Test the "special cases" described by the Javadoc.
    assertNaN(Math.nextAfter(Float.NaN, Double.NaN));
    assertNaN(Math.nextAfter(Float.NaN, 0.0d));
    assertNaN(Math.nextAfter(0, Double.NaN));

    assertNegativeZero(Math.nextAfter(0.0f, -0.0d));
    assertNegativeZero(Math.nextAfter(-0.0f, -0.0d));
    assertPositiveZero(Math.nextAfter(0.0f, 0.0d));
    assertPositiveZero(Math.nextAfter(-0.0f, 0.0d));

    assertNegativeZero(Math.nextAfter(-Float.MIN_VALUE, 1.0d));
    assertPositiveZero(Math.nextAfter(Float.MIN_VALUE, -1.d));

    assertEquals(Float.MAX_VALUE, Math.nextAfter(Float.POSITIVE_INFINITY, -1.0d));
    assertEquals(
        Float.MAX_VALUE, Math.nextAfter(Float.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertEquals(-Float.MAX_VALUE, Math.nextAfter(Float.NEGATIVE_INFINITY, 1.0d));
    assertEquals(
        -Float.MAX_VALUE, Math.nextAfter(Float.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));

    assertEquals(
        Float.POSITIVE_INFINITY, Math.nextAfter(Float.MAX_VALUE, Double.POSITIVE_INFINITY));
    assertEquals(
        Float.NEGATIVE_INFINITY, Math.nextAfter(-Float.MAX_VALUE, Double.NEGATIVE_INFINITY));

    // General rules: if values compare as equal, return "direction" (exceptions covered above)
    assertEquals(
        Float.POSITIVE_INFINITY, Math.nextAfter(Float.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertEquals(
        Float.NEGATIVE_INFINITY, Math.nextAfter(Float.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertEquals(Float.MAX_VALUE, Math.nextAfter(Float.MAX_VALUE, Float.MAX_VALUE));
    assertEquals(Float.POSITIVE_INFINITY, Math.nextAfter(Float.MAX_VALUE, Double.MAX_VALUE));

    // Return number adjacent to "start" in the relative direction of "direction". Using hex to
    // easily see bit patterns in the sample data.
    assertEquals(0x1.fffffcp127f, Math.nextAfter(Float.MAX_VALUE, 0.0d));
    assertEquals(0x1.fffffcp127f, Math.nextAfter(Float.MAX_VALUE, Double.NEGATIVE_INFINITY));
    assertEquals(-0x1.fffffcp127f, Math.nextAfter(-Float.MAX_VALUE, 0.0d));
    assertEquals(-0x1.fffffcp127f, Math.nextAfter(-Float.MAX_VALUE, Double.POSITIVE_INFINITY));
    assertEquals(0x1.fffffep124f, Math.nextAfter(0x1.0p125f, 0.0));
    assertEquals(0x1.0p125f, Math.nextAfter(0x1.fffffep124f, Double.POSITIVE_INFINITY));

    // Test near zero (minvalue -> zero is tested above), mantissa sign flips positive/negative
    assertEquals(Float.MIN_VALUE, Math.nextAfter(0.0f, 1.0d));
    assertEquals(Float.MIN_VALUE, Math.nextAfter(-0.0f, 1.0d));
    assertEquals(-Float.MIN_VALUE, Math.nextAfter(0.0f, -1.0d));
    assertEquals(-Float.MIN_VALUE, Math.nextAfter(-0.0f, -1.0d));

    // Test near 1, where exponent sign flips positive/negative
    assertEquals(0x1.000002p0f, Math.nextAfter(1.0f, 2.0d));
    assertEquals(0x1.fffffep-1f, Math.nextAfter(1.0f, 0.0d));
    assertEquals(1.0f, Math.nextAfter(0x1.fffffep-1f, 2.0d));
    assertEquals(1.0f, Math.nextAfter(0x1.000002p0f, 0.0d));

    // Repeat near -1
    assertEquals(-0x1.000002p0f, Math.nextAfter(-1.0f, -2.0d));
    assertEquals(-0x1.fffffep-1f, Math.nextAfter(-1.0f, 0.0d));
    assertEquals(-1.0f, Math.nextAfter(-0x1.fffffep-1f, -2.0d));
    assertEquals(-1.0f, Math.nextAfter(-0x1.000002p0f, 0.0d));
  }

  public void testNextUpFloat() {
    // Special cases from javadoc
    assertNaN(Math.nextUp(Float.NaN));
    assertEquals(Float.POSITIVE_INFINITY, Math.nextUp(Float.POSITIVE_INFINITY));
    assertEquals(Float.MIN_VALUE, Math.nextUp(0.0f));
    assertEquals(Float.MIN_VALUE, Math.nextUp(-0.0f));

    assertEquals(Float.POSITIVE_INFINITY, Math.nextUp(Float.MAX_VALUE));
    assertEquals(-Float.MAX_VALUE, Math.nextUp(Float.NEGATIVE_INFINITY));

    assertNegativeZero(Math.nextUp(-Float.MIN_VALUE));

    assertEquals(0x1.0p2f, Math.nextUp(0x1.fffffep1f));
    assertEquals(0x1.000002p2f, Math.nextUp(0x1.0p2f));
  }

  public void testNextDownFloat() {
    // Special cases from javadoc
    assertNaN(Math.nextDown(Float.NaN));
    assertEquals(Float.NEGATIVE_INFINITY, Math.nextDown(Float.NEGATIVE_INFINITY));
    assertEquals(-Float.MIN_VALUE, Math.nextDown(0.0f));
    assertEquals(-Float.MIN_VALUE, Math.nextDown(-0.0f));

    assertEquals(Float.NEGATIVE_INFINITY, Math.nextDown(-Float.MAX_VALUE));
    assertEquals(Float.MAX_VALUE, Math.nextDown(Float.POSITIVE_INFINITY));

    assertPositiveZero(Math.nextDown(Float.MIN_VALUE));

    assertEquals(0x1.fffffep1f, Math.nextDown(0x1.0p2f));
    assertEquals(0x1.fffffcp1f, Math.nextDown(0x1.fffffep1f));
  }

  public void testNextAfterDouble() {
    // Test the "special cases" described by the Javadoc
    assertNaN(Math.nextAfter(Double.NaN, Double.NaN));
    assertNaN(Math.nextAfter(Double.NaN, 0.0d));
    assertNaN(Math.nextAfter(0d, Double.NaN));

    assertNegativeZero(Math.nextAfter(0.0d, -0.0d));
    assertNegativeZero(Math.nextAfter(-0.0d, -0.0d));
    assertPositiveZero(Math.nextAfter(0.0d, 0.0d));
    assertPositiveZero(Math.nextAfter(-0.0d, 0.0d));

    assertNegativeZero(Math.nextAfter(-Double.MIN_VALUE, 1.0d));
    assertPositiveZero(Math.nextAfter(Double.MIN_VALUE, -1.0d));

    assertEquals(Double.MAX_VALUE, Math.nextAfter(Double.POSITIVE_INFINITY, -1.0d));
    assertEquals(
        Double.MAX_VALUE, Math.nextAfter(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertEquals(-Double.MAX_VALUE, Math.nextAfter(Double.NEGATIVE_INFINITY, 1.0d));
    assertEquals(
        -Double.MAX_VALUE, Math.nextAfter(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));

    assertEquals(
        Double.POSITIVE_INFINITY, Math.nextAfter(Double.MAX_VALUE, Double.POSITIVE_INFINITY));
    assertEquals(
        Double.NEGATIVE_INFINITY, Math.nextAfter(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY));

    // General rules: if values compare as equal, return "direction" (exceptions covered above)
    assertEquals(
        Double.POSITIVE_INFINITY,
        Math.nextAfter(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertEquals(
        Double.NEGATIVE_INFINITY,
        Math.nextAfter(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertEquals(Double.MAX_VALUE, Math.nextAfter(Double.MAX_VALUE, Double.MAX_VALUE));

    // Return number adjacent to "start" in the relative direction of "direction". Using hex to
    // easily see bit patterns in the sample data.
    assertEquals(0x1.ffffffffffffep1023, Math.nextAfter(Double.MAX_VALUE, 0.0d));
    assertEquals(
        0x1.ffffffffffffep1023, Math.nextAfter(Double.MAX_VALUE, Double.NEGATIVE_INFINITY));
    assertEquals(-0x1.ffffffffffffep1023, Math.nextAfter(-Double.MAX_VALUE, 0.0d));
    assertEquals(
        -0x1.ffffffffffffep1023, Math.nextAfter(-Double.MAX_VALUE, Double.POSITIVE_INFINITY));
    assertEquals(0x1.fffffffffffffp124, Math.nextAfter(0x1.0p125, 0.0d));
    assertEquals(0x1.0p125, Math.nextAfter(0x1.fffffffffffffp124, Double.POSITIVE_INFINITY));

    // Test near zero (minvalue -> zero is tested above), mantissa sign flips positive/negative
    assertEquals(Double.MIN_VALUE, Math.nextAfter(0.0d, 1.0d));
    assertEquals(Double.MIN_VALUE, Math.nextAfter(-0.0d, 1.0d));
    assertEquals(-Double.MIN_VALUE, Math.nextAfter(0.0d, -1.0d));
    assertEquals(-Double.MIN_VALUE, Math.nextAfter(-0.0d, -1.0d));

    // Test near 1, where exponent sign flips positive/negative
    assertEquals(0x1.0000000000001p0d, Math.nextAfter(1.0d, 2.0d));
    assertEquals(0x1.fffffffffffffp-1d, Math.nextAfter(1.0d, 0.0d));
    assertEquals(1.0d, Math.nextAfter(0x1.fffffffffffffp-1d, 2.0d));
    assertEquals(1.0d, Math.nextAfter(0x1.0000000000001p0d, 0.0d));

    // Repeat near -1
    assertEquals(-0x1.0000000000001p0d, Math.nextAfter(-1.0d, -2.0d));
    assertEquals(-0x1.fffffffffffffp-1d, Math.nextAfter(-1.0d, 0.0d));
    assertEquals(-1.0d, Math.nextAfter(-0x1.fffffffffffffp-1d, -2.0d));
    assertEquals(-1.0d, Math.nextAfter(-0x1.0000000000001p0d, 0.0d));
  }

  public void testNextUpDouble() {
    // Special cases from javadoc
    assertNaN(Math.nextUp(Double.NaN));
    assertEquals(Double.POSITIVE_INFINITY, Math.nextUp(Double.POSITIVE_INFINITY));
    assertEquals(Double.MIN_VALUE, Math.nextUp(0.0d));
    assertEquals(Double.MIN_VALUE, Math.nextUp(-0.0d));

    assertEquals(Double.POSITIVE_INFINITY, Math.nextUp(Double.MAX_VALUE));
    assertEquals(-Double.MAX_VALUE, Math.nextUp(Double.NEGATIVE_INFINITY));

    assertNegativeZero(Math.nextUp(-Double.MIN_VALUE));

    assertEquals(0x1.0p2d, Math.nextUp(0x1.fffffffffffffp1d));
    assertEquals(0x1.0000000000001p2d, Math.nextUp(0x1.0p2d));

    // Test near zero (minvalue -> zero is tested above), mantissa sign flips positive/negative
    assertEquals(Double.MIN_VALUE, Math.nextUp(0.0d));
    assertEquals(Double.MIN_VALUE, Math.nextUp(-0.0d));

    // Test near 1, where exponent sign flips positive/negative
    assertEquals(0x1.0000000000001p0d, Math.nextUp(1.0d));
    assertEquals(1.0d, Math.nextUp(0x1.fffffffffffffp-1d));

    // Repeat near -1
    assertEquals(-0x1.fffffffffffffp-1d, Math.nextUp(-1.0d));
    assertEquals(-1.0d, Math.nextUp(-0x1.0000000000001p0d));
  }

  public void testNextDownDouble() {
    // Special cases from javadoc
    assertNaN(Math.nextDown(Double.NaN));
    assertEquals(Double.NEGATIVE_INFINITY, Math.nextDown(Double.NEGATIVE_INFINITY));
    assertEquals(-Double.MIN_VALUE, Math.nextDown(0.0d));
    assertEquals(-Double.MIN_VALUE, Math.nextDown(-0.0d));

    assertEquals(Double.NEGATIVE_INFINITY, Math.nextDown(-Double.MAX_VALUE));
    assertEquals(Double.MAX_VALUE, Math.nextDown(Double.POSITIVE_INFINITY));

    assertPositiveZero(Math.nextDown(Double.MIN_VALUE));

    assertEquals(0x1.fffffffffffffp1d, Math.nextDown(0x1.0p2d));
    assertEquals(0x1.ffffffffffffep1d, Math.nextDown(0x1.fffffffffffffp1d));

    // Test near zero (minvalue -> zero is tested above), mantissa sign flips positive/negative
    assertEquals(-Double.MIN_VALUE, Math.nextDown(0.0d));
    assertEquals(-Double.MIN_VALUE, Math.nextDown(-0.0d));

    // Test near 1, where exponent sign flips positive/negative
    assertEquals(0x1.fffffffffffffp-1d, Math.nextDown(1.0d));
    assertEquals(1.0d, Math.nextDown(0x1.0000000000001p0d));

    // Repeat near -1
    assertEquals(-0x1.0000000000001p0d, Math.nextDown(-1.0d));
    assertEquals(-1.0d, Math.nextDown(-0x1.fffffffffffffp-1d));
  }
}
