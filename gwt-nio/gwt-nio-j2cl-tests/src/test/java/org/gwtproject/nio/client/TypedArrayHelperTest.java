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
import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import elemental2.core.ArrayBuffer;
import java.nio.ByteBuffer;
import org.gwtproject.nio.TypedArrayHelper;
import org.junit.Test;

@J2clTestInput(TypedArrayHelperTest.class)
public class TypedArrayHelperTest {

  @Test
  public void wrap() {
    ArrayBuffer ab = new ArrayBuffer(10);
    ByteBuffer tested = TypedArrayHelper.wrap(ab);
    assertTrue(tested.capacity() == 10);
    assertEquals(0, tested.position());
  }

  @Test
  public void unwrap() {
    ArrayBuffer ab = new ArrayBuffer(10);
    ByteBuffer tested = TypedArrayHelper.wrap(ab);
    assertEquals(ab, TypedArrayHelper.unwrap(tested).buffer);
  }

  @Test
  public void stringToByteBuffer() {
    ByteBuffer tested = TypedArrayHelper.stringToByteBuffer("test");
    assertEquals(4, tested.capacity());
    assertEquals(0, tested.position());
  }
}
