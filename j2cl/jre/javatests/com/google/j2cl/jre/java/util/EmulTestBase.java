/*
 * Copyright 2007 Google Inc.
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
package com.google.j2cl.jre.java.util;

import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.junit.function.ThrowingRunnable;

/**
 * A common base class for emulation tests.
 */
public class EmulTestBase extends GWTTestCase {

  public static void assertEquals(Object[] x, Object[] y) {
    assertEquals(x.length, y.length);
    for (int i = 0; i < y.length; i++) {
      assertEquals(x[i], y[i]);
    }
  }

  /**
   * Easy way to test what should be in a list.
   */
  protected static void assertEquals(Object[] array, List target) {
    assertEquals(array.length, target.size());
    for (int i = 0; i < array.length; i++) {
      assertEquals(target.get(i), array[i]);
    }
  }

  public static void assertEquals(int[] expected, int[] actual) {
    assertTrue("expected: " + Arrays.toString(expected) + ", actual: " + Arrays.toString(actual),
        Arrays.equals(expected, actual));
  }

  public static void assertEquals(long[] expected, long[] actual) {
    assertTrue("expected: " + Arrays.toString(expected) + ", actual: " + Arrays.toString(actual),
        Arrays.equals(expected, actual));
  }

  public static void assertEquals(double[] expected, double[] actual) {
    assertTrue("expected: " + Arrays.toString(expected) + ", actual: " + Arrays.toString(actual),
        Arrays.equals(expected, actual));
  }

<<<<<<< HEAD
  public static void assertThrowsNullPointerException(ThrowingRunnable runnable) {
    assertThrows(NullPointerException.class, runnable);
=======
  @Override
  public String getModuleName() {
    return "com.google.gwt.emultest.EmulSuite";
>>>>>>> 67956f054c (Revert "Remove GWTTestCase from J2CL unit tests.")
  }
}
