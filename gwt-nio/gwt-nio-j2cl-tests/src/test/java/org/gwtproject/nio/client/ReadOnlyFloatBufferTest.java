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
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;
import org.junit.Before;
import org.junit.Test;

@J2clTestInput(ReadOnlyFloatBufferTest.class)
public class ReadOnlyFloatBufferTest extends FloatBufferTest {

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
      // expected
    }
  }

  @Test
  public void testArrayOffset() {
    try {
      buf.arrayOffset();
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
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
  public void testHasArray() {
    assertFalse(buf.hasArray());
  }

  @Test
  public void testHashCode() {
    FloatBuffer duplicate = buf.duplicate();
    assertEquals(buf.hashCode(), duplicate.hashCode());
  }

  @Test
  public void testIsReadOnly() {
    assertTrue(buf.isReadOnly());
  }

  @Test
  public void testPutfloat() {
    try {
      buf.put(0);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }

  @Test
  public void testPutfloatArray() {
    float array[] = new float[1];
    try {
      buf.put(array);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((float[]) null);
      fail("Should throw Exception"); // $NON-NLS-1$
    } catch (Exception e) {
      // expected
    }
  }

  @Test
  public void testPutfloatArrayintint() {
    float array[] = new float[1];
    try {
      buf.put(array, 0, array.length);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((float[]) null, 0, 1);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put(new float[buf.capacity() + 1], 0, buf.capacity() + 1);
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
  public void testPutFloatBuffer() {
    FloatBuffer other = FloatBuffer.allocate(1);
    try {
      buf.put(other);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put((FloatBuffer) null);
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
  public void testPutintfloat() {
    try {
      buf.put(0, (float) 0);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
    try {
      buf.put(-1, (float) 0);
      fail("Should throw ReadOnlyBufferException"); // $NON-NLS-1$
    } catch (ReadOnlyBufferException e) {
      // expected
    }
  }
}
