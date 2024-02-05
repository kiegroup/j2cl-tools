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

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;

public class NIOSuite {
  public static Test suite() {
    GWTTestSuite suite = new GWTTestSuite("Test suite for all cellview classes");

    suite.addTestSuite(ByteBufferTest.class);
    suite.addTestSuite(CharBufferTest.class);
    suite.addTestSuite(BufferOverflowExceptionTest.class);
    suite.addTestSuite(BufferUnderflowExceptionTest.class);
    suite.addTestSuite(ByteOrderTest.class);

    // suite.addTestSuite(DirectByteBufferTest.class); //TODO
    suite.addTestSuite(DirectCharBufferTest.class);
    suite.addTestSuite(DirectDoubleBufferTest.class);
    // suite.addTestSuite(DirectFloatBufferTest.class); //TODO
    suite.addTestSuite(DirectIntBufferTest.class);
    suite.addTestSuite(DirectIntBufferTest.class);
    suite.addTestSuite(DoubleBufferTest.class);
    suite.addTestSuite(FloatBufferTest.class);
    suite.addTestSuite(IntBufferTest.class);

    suite.addTestSuite(HeapByteBufferTest.class);
    suite.addTestSuite(InvalidMarkExceptionTest.class);
    suite.addTestSuite(LongBufferTest.class);
    suite.addTestSuite(ReadOnlyBufferExceptionTest.class);
    suite.addTestSuite(ReadOnlyCharBufferTest.class);
    suite.addTestSuite(ReadOnlyDoubleBufferTest.class);
    suite.addTestSuite(ReadOnlyFloatBufferTest.class);
    suite.addTestSuite(ReadOnlyHeapByteBufferTest.class);
    suite.addTestSuite(ReadOnlyIntBufferTest.class);
    suite.addTestSuite(ReadOnlyLongBufferTest.class);
    suite.addTestSuite(ReadOnlyShortBufferTest.class);
    suite.addTestSuite(ShortBufferTest.class);
    return suite;
  }
}
