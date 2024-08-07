/*
 * Copyright 2008 Google Inc.
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

import com.google.j2cl.jre.testing.J2ktIncompatible;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/** Tests <code>HashMap</code>. */
@NullMarked
public class HashMapTest extends TestMap {
  private static final int CAPACITY_16 = 16;
  private static final int CAPACITY_NEG_ONE_HALF = -1;
  private static final int CAPACITY_ZERO = 0;
  private static final Integer INTEGER_1 = new Integer(1);
  private static final Integer INTEGER_11 = new Integer(11);
  private static final Integer INTEGER_2 = new Integer(2);
  private static final Integer INTEGER_22 = new Integer(22);
  private static final Integer INTEGER_3 = new Integer(3);
  private static final Integer INTEGER_33 = new Integer(33);
  private static final Integer INTEGER_ZERO_KEY = new Integer(0);
  private static final String INTEGER_ZERO_VALUE = "integer zero";
  private static final String KEY_1 = "key1";
  private static final String KEY_2 = "key2";
  private static final String KEY_3 = "key3";
  private static final String KEY_4 = "key4";
  private static final String KEY_KEY = "key";
  private static final String KEY_TEST_CONTAINS_KEY = "testContainsKey";
  private static final String KEY_TEST_CONTAINS_VALUE = "testContainsValue";
  private static final String KEY_TEST_ENTRY_SET = "testEntrySet";
  private static final String KEY_TEST_GET = "testGet";
  private static final String KEY_TEST_KEY_SET = "testKeySet";
  private static final String KEY_TEST_PUT = "testPut";
  private static final String KEY_TEST_REMOVE = "testRemove";
  private static final float LOAD_FACTOR_NEG_ONE = -1.0F;
  private static final float LOAD_FACTOR_ONE_HALF = 0.5F;
  private static final float LOAD_FACTOR_ONE_TENTH = 0.1F;
  private static final float LOAD_FACTOR_ZERO = 0.0F;
  private static final Object ODD_ZERO_KEY =
      new Object() {
        @Override
        public int hashCode() {
          return 0;
        }
      };
  private static final String ODD_ZERO_VALUE = "odd zero";
  private static final int SIZE_ONE = 1;
  private static final int SIZE_THREE = 3;
  private static final int SIZE_TWO = 2;
  private static final int SIZE_ZERO = 0;
  private static final String STRING_ZERO_KEY = "0";
  private static final String STRING_ZERO_VALUE = "string zero";
  private static final String VALUE_1 = "val1";
  private static final String VALUE_2 = "val2";
  private static final String VALUE_3 = "val3";
  private static final String VALUE_4 = "val4";
  private static final String VALUE_TEST_CONTAINS_DOES_NOT_EXIST = "does not exist";
  private static final Integer VALUE_TEST_CONTAINS_KEY = new Integer(5);
  private static final String VALUE_TEST_ENTRY_SET_1 = KEY_TEST_ENTRY_SET + " - value1";
  private static final String VALUE_TEST_ENTRY_SET_2 = KEY_TEST_ENTRY_SET + " - value2";
  private static final String VALUE_TEST_GET = KEY_TEST_GET + " - Value";
  private static final String VALUE_TEST_KEY_SET = KEY_TEST_KEY_SET + " - value";
  private static final String VALUE_TEST_PUT_1 = KEY_TEST_PUT + " - value 1";
  private static final String VALUE_TEST_PUT_2 = KEY_TEST_PUT + " - value 2";
  private static final String VALUE_TEST_REMOVE = KEY_TEST_REMOVE + " - value";
  private static final String VALUE_VAL = "value";

  private static void assertEmptyIterator(Iterator<?> it) {
    assertNotNull(it);
    assertFalse(it.hasNext());
    try {
      it.next();
      fail("Expected NoSuchElementException");
    } catch (NoSuchElementException expected) {
    }
  }

  /**
   * Check the state of a newly constructed, empty HashMap.
   *
   * @param hashMap
   */
  private static void checkEmptyHashMapAssumptions(HashMap<?, ?> hashMap) {
    assertNotNull(hashMap);
    assertTrue(hashMap.isEmpty());

    assertNotNull(hashMap.values());
    assertTrue(hashMap.values().isEmpty());
    assertTrue(hashMap.values().size() == 0);

    assertNotNull(hashMap.keySet());
    assertTrue(hashMap.keySet().isEmpty());
    assertTrue(hashMap.keySet().size() == 0);

    assertNotNull(hashMap.entrySet());
    assertTrue(hashMap.entrySet().isEmpty());
    assertTrue(hashMap.entrySet().size() == 0);

    assertEmptyIterator(hashMap.entrySet().iterator());
  }

  @Override
  public String getModuleName() {
    return "com.google.gwt.emultest.EmulSuite";
  }

  public void testAddEqualKeys() {
    final HashMap<Number, Object> expected = new HashMap<Number, Object>();
    assertEquals(0, expected.size());
    iterateThrough(expected);
    expected.put(new Long(45), new Object());
    assertEquals(1, expected.size());
    iterateThrough(expected);
    expected.put(new Integer(45), new Object());
    assertNotSame(new Integer(45), new Long(45));
    assertEquals(2, expected.size());
    iterateThrough(expected);
  }

  public void testAddWatch() {
    HashMap<String, String> m = new HashMap<String, String>();
    m.put("watch", "watch");
    assertEquals("watch", m.get("watch"));
  }

  /*
   * Test method for 'java.util.HashMap.clear()'
   */
  public void testClear() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    hashMap.put("Hello", "Bye");
    assertFalse(hashMap.isEmpty());
    assertTrue(hashMap.size() == SIZE_ONE);

    hashMap.clear();
    assertTrue(hashMap.isEmpty());
    assertTrue(hashMap.size() == 0);
  }

  /*
   * Test method for 'java.util.HashMap.clone()'
   */
  @J2ktIncompatible // b/317230935
  public void testClone() {
    HashMap<String, String> srcMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(srcMap);

    HashMap<String, String> dstMap = cloneMap(srcMap);
    assertNotNull(dstMap);
    assertEquals(dstMap.size(), srcMap.size());
    // assertTrue(dstMap.values().toArray().equals(srcMap.values().toArray()));
    assertTrue(dstMap.keySet().equals(srcMap.keySet()));
    assertTrue(dstMap.entrySet().equals(srcMap.entrySet()));

    // Check non-empty clone behavior
    srcMap.put(KEY_1, VALUE_1);
    srcMap.put(KEY_2, VALUE_2);
    srcMap.put(KEY_3, VALUE_3);
    dstMap = cloneMap(srcMap);
    assertNotNull(dstMap);
    assertEquals(dstMap.size(), srcMap.size());

    assertTrue(dstMap.keySet().equals(srcMap.keySet()));

    assertTrue(dstMap.entrySet().equals(srcMap.entrySet()));
  }

  /*
   * Test method for 'java.util.HashMap.containsKey(Object)'
   */
  public void testContainsKey() {
    HashMap<@Nullable String, @Nullable Integer> hashMap = new HashMap<>();
    checkEmptyHashMapAssumptions(hashMap);

    assertFalse(hashMap.containsKey(KEY_TEST_CONTAINS_KEY));
    hashMap.put(KEY_TEST_CONTAINS_KEY, VALUE_TEST_CONTAINS_KEY);
    assertTrue(hashMap.containsKey(KEY_TEST_CONTAINS_KEY));
    assertFalse(hashMap.containsKey(VALUE_TEST_CONTAINS_DOES_NOT_EXIST));

    assertFalse(hashMap.containsKey(null));
    hashMap.put(null, VALUE_TEST_CONTAINS_KEY);
    assertTrue(hashMap.containsKey(null));
  }

  /*
   * Test method for 'java.util.HashMap.containsValue(Object)'
   */
  @SuppressWarnings("CollectionIncompatibleType")
  public void testContainsValue() {
    HashMap<@Nullable String, @Nullable Integer> hashMap = new HashMap<>();
    checkEmptyHashMapAssumptions(hashMap);

    assertFalse("check contains of empty map", hashMap.containsValue(VALUE_TEST_CONTAINS_KEY));
    hashMap.put(KEY_TEST_CONTAINS_VALUE, VALUE_TEST_CONTAINS_KEY);
    assertTrue(
        "check contains of map with element", hashMap.containsValue(VALUE_TEST_CONTAINS_KEY));
    assertFalse(
        "check contains of map other element",
        hashMap.containsValue(
            /* expected: Integer, actual: String */ VALUE_TEST_CONTAINS_DOES_NOT_EXIST));

    assertFalse(hashMap.containsValue(null));
    hashMap.put(KEY_TEST_CONTAINS_VALUE, null);
    assertTrue(hashMap.containsValue(null));
  }

  /*
   * Test method for 'java.util.HashMap.entrySet()'
   */
  public void testEntrySet() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    Set<Map.Entry<String, String>> entrySet = hashMap.entrySet();
    assertNotNull(entrySet);

    // Check that the entry set looks right
    hashMap.put(KEY_TEST_ENTRY_SET, VALUE_TEST_ENTRY_SET_1);
    entrySet = hashMap.entrySet();
    assertEquals(SIZE_ONE, entrySet.size());
    Iterator<Map.Entry<String, String>> itSet = entrySet.iterator();
    Map.Entry<String, String> entry = itSet.next();
    assertEquals(KEY_TEST_ENTRY_SET, entry.getKey());
    assertEquals(VALUE_TEST_ENTRY_SET_1, entry.getValue());
    assertEmptyIterator(itSet);

    // Check that entries in the entrySet are update correctly on overwrites
    hashMap.put(KEY_TEST_ENTRY_SET, VALUE_TEST_ENTRY_SET_2);
    entrySet = hashMap.entrySet();
    assertEquals(SIZE_ONE, entrySet.size());
    itSet = entrySet.iterator();
    entry = itSet.next();
    assertEquals(KEY_TEST_ENTRY_SET, entry.getKey());
    assertEquals(VALUE_TEST_ENTRY_SET_2, entry.getValue());
    assertEmptyIterator(itSet);

    // Check that entries are updated on removes
    hashMap.remove(KEY_TEST_ENTRY_SET);
    checkEmptyHashMapAssumptions(hashMap);
  }

  /*
   * Used to test the entrySet entry's set method.
   */
  public void testEntrySetEntrySetterNonString() {
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    hashMap.put(1, 2);
    Set<Map.Entry<Integer, Integer>> entrySet = hashMap.entrySet();
    Map.Entry<Integer, Integer> entry = entrySet.iterator().next();

    entry.setValue(3);
    assertEquals(3, hashMap.get(1).intValue());

    hashMap.put(1, 4);
    assertEquals(4, entry.getValue().intValue());

    assertEquals(1, hashMap.size());
  }

  /*
   * Used to test the entrySet entry's set method.
   */
  public void testEntrySetEntrySetterNull() {
    HashMap<@Nullable String, @Nullable Integer> hashMap = new HashMap<String, Integer>();
    hashMap.put(null, 2);
    Set<Map.Entry<@Nullable String, @Nullable Integer>> entrySet = hashMap.entrySet();
    Map.Entry<@Nullable String, @Nullable Integer> entry = entrySet.iterator().next();

    entry.setValue(3);
    assertEquals(3, hashMap.get(null).intValue());

    hashMap.put(null, 4);
    assertEquals(4, entry.getValue().intValue());

    assertEquals(1, hashMap.size());
  }

  /*
   * Used to test the entrySet entry's set method.
   */
  public void testEntrySetEntrySetterString() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    hashMap.put("A", "B");
    Set<Map.Entry<String, String>> entrySet = hashMap.entrySet();
    Map.Entry<String, String> entry = entrySet.iterator().next();

    entry.setValue("C");
    assertEquals("C", hashMap.get("A"));

    hashMap.put("A", "D");
    assertEquals("D", entry.getValue());

    assertEquals(1, hashMap.size());
  }

  /*
   * Used to test the entrySet remove method.
   */
  public void testEntrySetRemove() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    hashMap.put("A", "B");
    HashMap<String, String> dummy = new HashMap<String, String>();
    dummy.put("A", "b");
    Map.Entry<String, String> bogus = dummy.entrySet().iterator().next();
    Set<Map.Entry<String, String>> entrySet = hashMap.entrySet();
    boolean removed = entrySet.remove(bogus);
    assertEquals(false, removed);
    assertEquals("B", hashMap.get("A"));
  }

  /*
   * Test method for 'java.util.AbstractMap.equals(Object)'
   */
  @J2ktIncompatible // b/317230935
  public void testEquals() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    hashMap.put(KEY_KEY, VALUE_VAL);

    HashMap<String, String> copyMap = cloneMap(hashMap);

    assertTrue(hashMap.equals(copyMap));
    hashMap.put(VALUE_VAL, KEY_KEY);
    assertFalse(hashMap.equals(copyMap));
  }

  @SuppressWarnings("unchecked")
  @J2ktIncompatible // b/317230935
  private HashMap<String, String> cloneMap(HashMap<String, String> hashMap) {
    return (HashMap<String, String>) hashMap.clone();
  }

  /*
   * Test method for 'java.lang.Object.finalize()'.
   */
  public void testFinalize() {
    // no tests for finalize
  }

  /*
   * Test method for 'java.util.HashMap.get(Object)'.
   */
  public void testGet() {
    HashMap<@Nullable String, @Nullable String> hashMap = new HashMap<>();
    checkEmptyHashMapAssumptions(hashMap);

    assertNull(hashMap.get(KEY_TEST_GET));
    hashMap.put(KEY_TEST_GET, VALUE_TEST_GET);
    assertNotNull(hashMap.get(KEY_TEST_GET));

    assertNull(hashMap.get(null));
    hashMap.put(null, VALUE_TEST_GET);
    assertNotNull(hashMap.get(null));

    hashMap.put(null, null);
    assertNull(hashMap.get(null));
  }

  /*
   * Test method for 'java.util.AbstractMap.hashCode()'.
   */
  public void testHashCode() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    // Check that hashCode changes
    int hashCode1 = hashMap.hashCode();
    hashMap.put(KEY_KEY, VALUE_VAL);
    int hashCode2 = hashMap.hashCode();

    assertTrue(hashCode1 != hashCode2);
  }

  /*
   * Test method for 'java.util.HashMap.HashMap()'.
   */
  public void testHashMap() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);
  }

  /*
   * Test method for 'java.util.HashMap.HashMap(int)'
   */
  public void testHashMapInt() {
    HashMap<String, String> hashMap = new HashMap<String, String>(CAPACITY_16);
    checkEmptyHashMapAssumptions(hashMap);

    // TODO(mmendez): how do we verify capacity?
    boolean failed = true;
    try {
      new HashMap<String, String>(-SIZE_ONE);
    } catch (Throwable ex) {
      if (ex instanceof IllegalArgumentException) {
        failed = false;
      }
    }

    if (failed) {
      fail("Failure testing new HashMap(-1)");
    }

    HashMap<String, String> zeroSizedHashMap = new HashMap<String, String>(0);
    assertNotNull(zeroSizedHashMap);
  }

  /*
   * Test method for 'java.util.HashMap.HashMap(int, float)'
   */
  public void testHashMapIntFloat() {
    HashMap<String, String> hashMap =
        new HashMap<String, String>(CAPACITY_16, LOAD_FACTOR_ONE_HALF);
    checkEmptyHashMapAssumptions(hashMap);

    // TODO(mmendez): how do we verify capacity and load factor?

    // Test new HashMap(-1, 0.0F)
    boolean failed = true;
    try {
      new HashMap<String, String>(CAPACITY_NEG_ONE_HALF, LOAD_FACTOR_ZERO);
    } catch (Throwable ex) {
      if (ex instanceof IllegalArgumentException) {
        failed = false;
      }
    }

    if (failed) {
      fail("Failure testing new HashMap(-1, 0.0F)");
    }

    // Test new HashMap(0, -1.0F)
    failed = true;
    try {
      new HashMap<String, String>(CAPACITY_ZERO, LOAD_FACTOR_NEG_ONE);
    } catch (Throwable ex) {
      if (ex instanceof IllegalArgumentException) {
        failed = false;
      }
    }

    if (failed) {
      fail("Failure testing new HashMap(0, -1.0F)");
    }

    // Test new HashMap(0,0F);
    hashMap = new HashMap<String, String>(CAPACITY_ZERO, LOAD_FACTOR_ONE_TENTH);
    assertNotNull(hashMap);
  }

  /*
   * Test method for 'java.util.HashMap.HashMap(Map)'
   */
  public void testHashMapMap() {
    HashMap<Integer, Integer> srcMap = new HashMap<Integer, Integer>();
    assertNotNull(srcMap);
    checkEmptyHashMapAssumptions(srcMap);

    srcMap.put(INTEGER_1, INTEGER_11);
    srcMap.put(INTEGER_2, INTEGER_22);
    srcMap.put(INTEGER_3, INTEGER_33);

    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>(srcMap);
    assertFalse(hashMap.isEmpty());
    assertTrue(hashMap.size() == SIZE_THREE);

    Collection<Integer> valColl = hashMap.values();
    assertTrue(valColl.contains(INTEGER_11));
    assertTrue(valColl.contains(INTEGER_22));
    assertTrue(valColl.contains(INTEGER_33));

    Collection<Integer> keyColl = hashMap.keySet();
    assertTrue(keyColl.contains(INTEGER_1));
    assertTrue(keyColl.contains(INTEGER_2));
    assertTrue(keyColl.contains(INTEGER_3));
  }

  /*
   * Test method for 'java.util.AbstractMap.isEmpty()'
   */
  public void testIsEmpty() {
    HashMap<String, String> srcMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(srcMap);

    HashMap<String, String> dstMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(dstMap);

    dstMap.putAll(srcMap);
    assertTrue(dstMap.isEmpty());

    dstMap.put(KEY_KEY, VALUE_VAL);
    assertFalse(dstMap.isEmpty());

    dstMap.remove(KEY_KEY);
    assertTrue(dstMap.isEmpty());
    assertEquals(0, dstMap.size());
  }

  public void testKeysConflict() {
    HashMap<Object, String> hashMap = new HashMap<Object, String>();

    hashMap.put(STRING_ZERO_KEY, STRING_ZERO_VALUE);
    hashMap.put(INTEGER_ZERO_KEY, INTEGER_ZERO_VALUE);
    hashMap.put(ODD_ZERO_KEY, ODD_ZERO_VALUE);
    assertEquals(INTEGER_ZERO_VALUE, hashMap.get(INTEGER_ZERO_KEY));
    assertEquals(ODD_ZERO_VALUE, hashMap.get(ODD_ZERO_KEY));
    assertEquals(STRING_ZERO_VALUE, hashMap.get(STRING_ZERO_KEY));
    hashMap.remove(INTEGER_ZERO_KEY);
    assertEquals(ODD_ZERO_VALUE, hashMap.get(ODD_ZERO_KEY));
    assertEquals(STRING_ZERO_VALUE, hashMap.get(STRING_ZERO_KEY));
    assertEquals(hashMap.get(INTEGER_ZERO_KEY), null);
    hashMap.remove(ODD_ZERO_KEY);
    assertEquals(hashMap.get(INTEGER_ZERO_KEY), null);
    assertEquals(hashMap.get(ODD_ZERO_KEY), null);
    assertEquals(STRING_ZERO_VALUE, hashMap.get(STRING_ZERO_KEY));
    hashMap.remove(STRING_ZERO_KEY);
    assertEquals(hashMap.get(INTEGER_ZERO_KEY), null);
    assertEquals(hashMap.get(ODD_ZERO_KEY), null);
    assertEquals(hashMap.get(STRING_ZERO_KEY), null);
    assertEquals(0, hashMap.size());
  }

  /*
   * Test method for 'java.util.HashMap.keySet()'
   */
  public void testKeySet() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    Set<String> keySet = hashMap.keySet();
    assertNotNull(keySet);
    assertTrue(keySet.isEmpty());
    assertTrue(keySet.size() == 0);

    hashMap.put(KEY_TEST_KEY_SET, VALUE_TEST_KEY_SET);

    assertTrue(keySet.size() == SIZE_ONE);
    assertTrue(keySet.contains(KEY_TEST_KEY_SET));
    assertFalse(keySet.contains(VALUE_TEST_KEY_SET));
    assertFalse(keySet.contains(KEY_TEST_KEY_SET.toUpperCase(Locale.ROOT)));
  }

  /*
   * Test from issue 2499.
   * The problem was actually in other objects hashCode() function, as
   * the value was not coerced to an int and therefore parseInt in
   * AbstractHashMap.addAllHashEntries was failing.  This was fixed
   * both in our JRE classes to ensure int overflow is handled properly
   * in their hashCode() implementation and in HashMap so that user
   * objects which don't account for int overflow will still be
   * handled properly.  There is a slight performance penalty, as
   * the coercion will be done twice, but that should be fixeable with
   * improved compiler optimization.
   */
  public void testLargeHashCodes() {
    final int LIST_COUNT = 20;
    List<Integer> values = new ArrayList<Integer>(LIST_COUNT);
    for (int n = 0; n < LIST_COUNT; n++) {
      values.add(n);
    }
    Map<List<Integer>, String> testMap = new HashMap<List<Integer>, String>();
    testMap.put(values, "test");
    int count = 0;
    for (Map.Entry<List<Integer>, String> entry : testMap.entrySet()) {
      count++;
    }
    assertEquals(testMap.size(), count);
  }

  /*
   * Test method for 'java.util.HashMap.put(Object, Object)'
   */
  public void testPut() {
    HashMap<@Nullable String, @Nullable String> hashMap = new HashMap<>();
    checkEmptyHashMapAssumptions(hashMap);

    assertNull(hashMap.put(KEY_TEST_PUT, VALUE_TEST_PUT_1));
    assertEquals(VALUE_TEST_PUT_1, hashMap.put(KEY_TEST_PUT, VALUE_TEST_PUT_2));
    assertNull(hashMap.put(null, VALUE_TEST_PUT_1));
    assertEquals(VALUE_TEST_PUT_1, hashMap.put(null, VALUE_TEST_PUT_2));
  }

  /** Test method for 'java.util.HashMap.putAll(Map)'. */
  public void testPutAll() {
    HashMap<String, String> srcMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(srcMap);

    srcMap.put(KEY_1, VALUE_1);
    srcMap.put(KEY_2, VALUE_2);
    srcMap.put(KEY_3, VALUE_3);

    // Make sure that the data is copied correctly
    HashMap<String, String> dstMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(dstMap);

    dstMap.putAll(srcMap);
    assertEquals(srcMap.size(), dstMap.size());
    assertTrue(dstMap.containsKey(KEY_1));
    assertTrue(dstMap.containsValue(VALUE_1));
    assertFalse(dstMap.containsKey(KEY_1.toUpperCase(Locale.ROOT)));
    assertFalse(dstMap.containsValue(VALUE_1.toUpperCase(Locale.ROOT)));

    assertTrue(dstMap.containsKey(KEY_2));
    assertTrue(dstMap.containsValue(VALUE_2));
    assertFalse(dstMap.containsKey(KEY_2.toUpperCase(Locale.ROOT)));
    assertFalse(dstMap.containsValue(VALUE_2.toUpperCase(Locale.ROOT)));

    assertTrue(dstMap.containsKey(KEY_3));
    assertTrue(dstMap.containsValue(VALUE_3));
    assertFalse(dstMap.containsKey(KEY_3.toUpperCase(Locale.ROOT)));
    assertFalse(dstMap.containsValue(VALUE_3.toUpperCase(Locale.ROOT)));

    // Check that an empty map does not blow away the contents of the
    // destination map
    HashMap<String, String> emptyMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(emptyMap);
    dstMap.putAll(emptyMap);
    assertTrue(dstMap.size() == srcMap.size());

    // Check that put all overwrite any existing mapping in the destination map
    srcMap.put(KEY_1, VALUE_2);
    srcMap.put(KEY_2, VALUE_3);
    srcMap.put(KEY_3, VALUE_1);

    dstMap.putAll(srcMap);
    assertEquals(dstMap.size(), srcMap.size());
    assertEquals(VALUE_2, dstMap.get(KEY_1));
    assertEquals(VALUE_3, dstMap.get(KEY_2));
    assertEquals(VALUE_1, dstMap.get(KEY_3));

    // Check that a putAll does adds data but does not remove it

    srcMap.put(KEY_4, VALUE_4);
    dstMap.putAll(srcMap);
    assertEquals(dstMap.size(), srcMap.size());
    assertTrue(dstMap.containsKey(KEY_4));
    assertTrue(dstMap.containsValue(VALUE_4));
    assertEquals(VALUE_2, dstMap.get(KEY_1));
    assertEquals(VALUE_3, dstMap.get(KEY_2));
    assertEquals(VALUE_1, dstMap.get(KEY_3));
    assertEquals(VALUE_4, dstMap.get(KEY_4));

    dstMap.putAll(dstMap);
  }

  /** Test method for 'java.util.HashMap.remove(Object)'. */
  public void testRemove() {
    HashMap<@Nullable String, @Nullable String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    assertNull(hashMap.remove(null));
    hashMap.put(null, VALUE_TEST_REMOVE);
    assertNotNull(hashMap.remove(null));

    hashMap.put(KEY_TEST_REMOVE, VALUE_TEST_REMOVE);
    assertEquals(VALUE_TEST_REMOVE, hashMap.remove(KEY_TEST_REMOVE));
    assertNull(hashMap.remove(KEY_TEST_REMOVE));
  }

  /** Test method for 'java.util.HashMap.size()'. */
  public void testSize() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    // Test size behavior on put
    assertEquals(SIZE_ZERO, hashMap.size());
    hashMap.put(KEY_1, VALUE_1);
    assertEquals(SIZE_ONE, hashMap.size());
    hashMap.put(KEY_2, VALUE_2);
    assertEquals(SIZE_TWO, hashMap.size());
    hashMap.put(KEY_3, VALUE_3);
    assertEquals(SIZE_THREE, hashMap.size());

    // Test size behavior on remove
    hashMap.remove(KEY_1);
    assertEquals(SIZE_TWO, hashMap.size());
    hashMap.remove(KEY_2);
    assertEquals(SIZE_ONE, hashMap.size());
    hashMap.remove(KEY_3);
    assertEquals(SIZE_ZERO, hashMap.size());

    // Test size behavior on putAll
    hashMap.put(KEY_1, VALUE_1);
    hashMap.put(KEY_2, VALUE_2);
    hashMap.put(KEY_3, VALUE_3);
    HashMap<String, String> srcMap = new HashMap<String, String>(hashMap);
    hashMap.putAll(srcMap);
    assertEquals(SIZE_THREE, hashMap.size());

    // Test size behavior on clear
    hashMap.clear();
    assertEquals(SIZE_ZERO, hashMap.size());
  }

  /** Test method for 'java.util.AbstractMap.toString()'. */
  public void testToString() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);
    hashMap.put(KEY_KEY, VALUE_VAL);
    String entryString = makeEntryString(KEY_KEY, VALUE_VAL);
    assertTrue(entryString.equals(hashMap.toString()));
  }

  /** Test method for 'java.util.AbstractMap.values()'. */
  public void testValues() {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    checkEmptyHashMapAssumptions(hashMap);

    assertNotNull(hashMap.values());

    hashMap.put(KEY_KEY, VALUE_VAL);

    Collection<String> valColl = hashMap.values();
    assertNotNull(valColl);
    assertEquals(SIZE_ONE, valColl.size());

    Iterator<String> itVal = valColl.iterator();
    String val = itVal.next();
    assertEquals(VALUE_VAL, val);
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected Map makeEmptyMap() {
    return new HashMap();
  }

  // see: https://github.com/gwtproject/gwt/issues/9184
  public void testIteration_withCollidingHashCodes() {

    Object firstKey = createObjectWithHashCode(1);
    Object secondKey = createObjectWithHashCode(1);

    // make sure we actually have a hash collision for this test
    assertEquals(firstKey.hashCode(), secondKey.hashCode());

    HashMap<Object, String> testMap = new HashMap<>();
    testMap.put(firstKey, "one");
    testMap.put(secondKey, "two");

    Iterator<Object> it = testMap.keySet().iterator();

    assertTrue("new iterator should have next", it.hasNext());
    Object keyFromMap1 = it.next();

    it.remove();

    assertTrue("iterator should have next after first removal", it.hasNext());
    Object keyFromMap2 = it.next();

    it.remove();
    assertFalse(it.hasNext());

    // since iteration order is not defined for HashMap we need to make this test work with
    // both outcomes of the iteration
    if ((firstKey == keyFromMap1 && secondKey == keyFromMap2)
        || (firstKey == keyFromMap2 && secondKey == keyFromMap1)) {
      return;
    }
    fail("Wrong keys returned in iteration");
  }

  // see: https://github.com/gwtproject/gwt/issues/9184
  public void testIteration_withCollidingHashCodesAndFullIteration() {

    List<Object> keys = new ArrayList<>();
    HashMap<Object, String> testMap = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      Object key = createObjectWithHashCode((i + 10) / 10);
      keys.add(key);
      testMap.put(key, "" + i);
    }

    int expectedSize = keys.size();

    for (Iterator<Object> it = testMap.keySet().iterator(); it.hasNext(); expectedSize--) {
      Object key = it.next();
      assertTrue("Unexpected key was returned", keys.contains(key));
      keys.remove(key);
      it.remove();
      assertEquals("Map size", expectedSize - 1, testMap.size());
    }

    assertEquals("expected size", 0, expectedSize);
    assertTrue(testMap.isEmpty());
    assertTrue(keys.isEmpty());
  }

  public void testIteration_hasNextThenRemove() {
    HashMap<Object, String> testMap = new HashMap<>();
    testMap.put(createObjectWithHashCode(1), "one");
    testMap.put(createObjectWithHashCode(2), "two");

    Iterator<Object> it = testMap.keySet().iterator();
    it.next();
    assertTrue("iterator should have next after first", it.hasNext());
    it.remove();
    assertTrue("iterator should have next after removal", it.hasNext());

    it.next();
    it.remove();
    assertFalse(it.hasNext());
  }

  private Object createObjectWithHashCode(final int hashCode) {
    return new Object() {
      @Override
      public int hashCode() {
        return hashCode;
      }

      @Override
      public String toString() {
        return "" + hashCode;
      }
    };
  }

  private void iterateThrough(final HashMap<?, ?> expected) {
    Iterator<?> iter = expected.entrySet().iterator();
    for (int i = 0; i < expected.size(); i++) {
      iter.next();
    }
  }

  private String makeEntryString(final String key, final String value) {
    return "{" + key + "=" + value + "}";
  }
}
