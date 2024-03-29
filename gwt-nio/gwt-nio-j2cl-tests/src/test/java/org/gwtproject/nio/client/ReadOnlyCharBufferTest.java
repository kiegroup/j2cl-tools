/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.gwtproject.nio.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import org.junit.Before;
import org.junit.Test;

@J2clTestInput(ReadOnlyCharBufferTest.class)
public class ReadOnlyCharBufferTest extends CharBufferTest {

  @Before
  public void gwtSetUp() {
    super.gwtSetUp();
    buf = buf.asReadOnlyBuffer();
    baseBuf = buf;
  }

  @Test
  public void testArray() {
    try {
      buf.array();
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
    }
  }

  @Test
  public void testArrayOffset() {
    try {
      buf.arrayOffset();
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
    }
  }

  @Test
  public void testCompact() {
    try {
      buf.compact();
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }

  @Test
  public void testHashCode() {
    CharBuffer duplicate = buf.duplicate();
    assertEquals(buf.hashCode(), duplicate.hashCode());
  }

  @Test
  public void testPutchar() {
    try {
      buf.put((char) 0);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }

  @Test
  public void testPutcharArray() {
    char array[] = new char[1];
    try {
      buf.put(array);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((char[]) null);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (Exception e) {
      // expected
    }
  }

  @Test
  public void testPutcharArrayintint() {
    char array[] = new char[1];
    try {
      buf.put(array, 0, array.length);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((char[]) null, 0, 1);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put(new char[buf.capacity() + 1], 0, buf.capacity() + 1);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put(array, -1, array.length);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }

  @Test
  public void testPutCharBuffer() {
    CharBuffer other = CharBuffer.allocate(1);
    try {
      buf.put(other);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((CharBuffer) null);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put(buf);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }

  @Test
  public void testPutintchar() {
    try {
      buf.put(0, (char) 0);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put(-1, (char) 0);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }

  @Test
  public void testPutString() {
    String str = " ";
    try {
      buf.put(str);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((String) null);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (Exception e) {
      // expected
    }
  }

  @Test
  public void testPutStringintint() {
    buf.clear();
    String str = String.valueOf(new char[buf.capacity()]);
    try {
      buf.put(str, 0, str.length());
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((String) null, 0, 0);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (Exception e) {
      // expected
    }
    try {
      buf.put(str, -1, str.length());
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (IndexOutOfBoundsException e) {
      // expected
    }
    String longStr = String.valueOf(new char[buf.capacity() + 1]);
    try {
      buf.put(longStr, 0, longStr.length());
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }

  @Test
  public void testIsReadOnly() {
    assertTrue(buf.isReadOnly());
  }

  @Test
  public void testHasArray() {
    assertFalse(buf.hasArray());
  }
}
