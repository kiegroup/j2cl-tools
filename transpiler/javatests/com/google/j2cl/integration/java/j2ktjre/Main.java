/*
 * Copyright 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package j2ktjre;

import static com.google.j2cl.integration.testing.Asserts.assertEquals;
import static com.google.j2cl.integration.testing.Asserts.assertFalse;
import static com.google.j2cl.integration.testing.Asserts.assertTrue;

import java.util.Locale;

public class Main {
  public static void main(String... args) {
    testPrimitives();
    testStringBuilder();
  }

  private static void testPrimitives() {
    testBoolean();
    testInt();
    testLong();
    testDouble();
    testShort();
    testByte();
    testFloat();
    testCharacter();
    testString();
  }

  private static void testStringBuilder() {
    testInsert();
  }

  private static void testBoolean() {
    assertEquals((Object) true, Boolean.TRUE);
    assertEquals((Object) false, Boolean.FALSE);
    assertTrue(Boolean.TRUE.booleanValue());
    assertFalse(Boolean.FALSE.booleanValue());
    assertEquals("true", Boolean.toString(true));
    assertEquals("false", Boolean.toString(false));
    assertTrue((Object) Boolean.TRUE == (Object) Boolean.valueOf(true));
    assertTrue((Object) Boolean.FALSE == (Object) Boolean.valueOf(false));
  }

  private static void testInt() {
    assertEquals((Object) 9, Integer.valueOf(9));
    assertEquals((Object) 9, Integer.valueOf("9"));
    assertEquals((Object) 14, Integer.valueOf("16", 8));

    assertTrue(Integer.compare(9, 9) == 0);
    assertTrue(Integer.compare(9, 16) < 0);
    assertTrue(Integer.compare(16, 9) > 0);

    assertEquals("9", Integer.toString(9));
    assertEquals("20", Integer.toString(16, 8));
    assertEquals("57", Integer.toHexString(87));

    assertEquals(16, Integer.parseInt("16"));
    assertEquals(14, Integer.parseInt("16", 8));

    assertEquals(87, Integer.hashCode(87));

    assertEquals(28, Integer.numberOfLeadingZeros(10));
    assertEquals(1, Integer.numberOfTrailingZeros(10));

    assertEquals(64, Integer.highestOneBit(87));
    assertEquals(5, Integer.bitCount(87));

    assertEquals(1, Integer.signum(87));
    assertEquals(-1, Integer.signum(-87));
    assertEquals(0, Integer.signum(0));
  }

  private static void testLong() {
    assertEquals((Object) 9L, Long.valueOf(9));
    assertEquals((Object) 9L, Long.valueOf("9"));
    assertEquals((Object) 14L, Long.valueOf("16", 8));

    assertTrue(Long.compare(9, 9) == 0);
    assertTrue(Long.compare(9, 16) < 0);
    assertTrue(Long.compare(16, 9) > 0);

    assertEquals("9", Long.toString(9));
    assertEquals("20", Long.toString(16, 8));
    assertEquals("57", Long.toHexString(87));
    assertEquals("ffffffffffffffa9", Long.toHexString(-87));

    assertEquals(16L, Long.parseLong("16"));
    assertEquals(14L, Long.parseLong("16", 8));

    assertEquals(87, Long.hashCode(87));
    assertEquals(86, Long.hashCode(-87));

    assertEquals(60, Long.numberOfLeadingZeros(10));
    assertEquals(1, Long.numberOfTrailingZeros(10));

    assertEquals(5, Long.bitCount(87));

    assertEquals(1, Long.signum(87));
    assertEquals(-1, Long.signum(-87));
    assertEquals(0, Long.signum(0));
  }

  private static void testDouble() {
    Double dNan = Double.NaN, dInf = Double.POSITIVE_INFINITY;

    assertEquals((Object) 9.5, Double.valueOf(9.5));
    assertEquals((Object) 9.5, Double.valueOf("9.5"));

    assertTrue(Double.compare(9.5, 9.5) == 0);
    assertTrue(Double.compare(9.5, 16.0) < 0);
    assertTrue(Double.compare(16, 9.5) > 0);

    assertEquals("9.5", Double.toString(9.5));

    assertEquals((Object) 16.0, Double.parseDouble("16"));

    // Alternate condition for JS
    assertTrue(Double.hashCode(8) == 1075838976 || Double.hashCode(8) == 8);

    assertTrue(Double.isNaN(dNan));
    assertFalse(Double.isNaN(0.0));

    assertTrue(Double.isInfinite(dInf));
    assertFalse(Double.isInfinite(0.0));
  }

  private static void testShort() {
    short a = 9, b = 16, c = 14;

    assertEquals((Object) a, Short.valueOf(a));
    assertEquals((Object) a, Short.valueOf("9"));
    assertEquals((Object) c, Short.valueOf("16", 8));

    assertTrue(Short.compare(a, a) == 0);
    assertTrue(Short.compare(a, b) < 0);
    assertTrue(Short.compare(b, a) > 0);

    assertEquals("9", Short.toString(a));

    assertEquals(b, Short.parseShort("16"));
    assertEquals(c, Short.parseShort("16", 8));

    assertEquals(9, Short.hashCode(a));
  }

  private static void testByte() {
    byte a = 9, b = 16, c = 14;

    assertEquals((Object) a, Byte.valueOf(a));
    assertEquals((Object) a, Byte.valueOf("9"));
    assertEquals((Object) c, Byte.valueOf("16", 8));

    assertTrue(Byte.compare(a, a) == 0);
    assertTrue(Byte.compare(a, b) < 0);
    assertTrue(Byte.compare(b, a) > 0);

    assertEquals("9", Byte.toString(a));

    assertEquals(b, Byte.parseByte("16"));
    assertEquals(c, Byte.parseByte("16", 8));

    assertEquals(9, Byte.hashCode(a));
  }

  private static void testFloat() {
    float a = 9, b = 16, fNan = Float.NaN, fInf = Float.POSITIVE_INFINITY;

    assertEquals((Object) 9F, Float.valueOf(a));
    assertEquals((Object) 9F, Float.valueOf("9"));

    assertTrue(Float.compare(a, a) == 0);
    assertTrue(Float.compare(a, b) < 0);
    assertTrue(Float.compare(b, a) > 0);

    // Alternate condition for JS
    assertTrue(Float.toString(a).equals("9") || Float.toString(a).equals("9.0"));

    assertEquals(16F, Float.parseFloat("16"));

    // Alternate condition for JS
    assertTrue(Float.hashCode(9) == 1091567616 || Float.hashCode(9) == 9);

    assertTrue(Float.isNaN(fNan));
    assertFalse(Float.isNaN(a));

    assertTrue(Float.isInfinite(fInf));
    assertFalse(Float.isInfinite(0));
  }

  private static void testCharacter() {
    assertEquals((Object) 'a', Character.valueOf('a'));

    assertEquals(0, Character.compare('a', 'a'));
    assertEquals(-1, Character.compare('a', 'b'));
    assertEquals(1, Character.compare('c', 'b'));

    assertEquals('a', Character.forDigit(10, 16));

    assertEquals(98, Character.hashCode('b'));

    assertEquals(1, Character.charCount(0x9999));
    assertEquals(2, Character.charCount(0x10001));

    char[] cArray1 = {'a', 'b'};
    assertEquals(1, Character.toChars(97, cArray1, 1));
    assertEquals('a', cArray1[1]);

    char[] cArray2 = {'a', 'b'};
    assertEquals(2, Character.toChars(80000, cArray2, 0));

    char[] cArray3 = {'a', 'b'};
    assertEquals(97, Character.codePointAt(cArray3, 0, 1));
  }

  private static void testString() {
    char[] cArray = {'h', 'e', 'l', 'l', 'o'};
    assertEquals("ello", new String(cArray, 1, 4));

    assertEquals("a", String.valueOf('a'));
    assertEquals("5", String.valueOf(5));
    assertEquals("null", String.valueOf((Object) null));

    assertEquals(0, "A".compareToIgnoreCase("a"));
    assertEquals(-1, "A".compareToIgnoreCase("b"));
    assertEquals(1, "b".compareToIgnoreCase("A"));

    assertEquals("HELLO", "hello".toUpperCase(Locale.US));

    char dstCharArray[] = new char[3];
    "abcde".getChars(1, 4, dstCharArray, 0);
    assertEquals("bcd", new String(dstCharArray));

    CharSequence target = "aa", replacement = "c";
    String targetStr = "aaabaa";
    assertEquals("cabc", targetStr.replace(target, replacement));

    String str1 = "hello1hello2hello";
    String[] strArray1 = str1.split("[12]");
    assertEquals(3, strArray1.length);
    assertEquals("hello", strArray1[0]);
    assertEquals("hello", strArray1[1]);
    assertEquals("hello", strArray1[2]);

    String str2 = "hello1hello2hello";
    String[] strArray2 = str2.split("[12]", 2);
    assertEquals(2, strArray2.length);
    assertEquals("hello", strArray2[0]);
    assertEquals("hello2hello", strArray2[1]);
  }

  private static void testInsert() {
    StringBuilder strBuilder1 = new StringBuilder("0123");
    char[] cArray = {'h', 'e', 'l', 'l', 'o'};

    strBuilder1.insert(1, cArray, 1, 3);
    assertEquals("0ell123", strBuilder1.toString());
    strBuilder1.insert(6, cArray, 0, 5);
    assertEquals("0ell12hello3", strBuilder1.toString());

    StringBuilder strBuilder2 = new StringBuilder("0123");
    CharSequence charSeq = "hello";

    strBuilder2.insert(1, charSeq, 1, 3);
    assertEquals("0el123", strBuilder2.toString());
    strBuilder2.insert(6, charSeq, 0, 5);
    assertEquals("0el123hello", strBuilder2.toString());
  }
}