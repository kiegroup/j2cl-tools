/*
 * Copyright 2015 Google Inc.
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
package com.google.j2cl.jre.java8.util;

import static com.google.j2cl.jre.testing.TestUtils.isWasm;

import java.util.Collections;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import junit.framework.TestCase;
import org.jspecify.annotations.Nullable;

/**
 * Tests for PrimitiveIterator JRE emulation.
 */
public class PrimitiveIteratorTest extends TestCase {

  public void testForEachRemainingDoubleConsumer() {
    PrimitiveIterator.OfDouble it = createTestPrimitiveDoubleIterator();
    it.forEachRemaining(
        (Consumer<@Nullable Double>)
            new JanusDoubleConsumer() {
              @Override
              public void accept(@Nullable Double value) {
                fail();
              }

              @Override
              public void accept(double value) {}
            });
  }

  public void testForEachRemainingDoubleConsumer_null() {
    if (isWasm()) {
      // TODO(b/183769034): Re-enable when NPE on dereference is supported
      return;
    }

    try {
      createTestPrimitiveDoubleIterator().forEachRemaining((Consumer<@Nullable Double>) null);
      fail();
    } catch (NullPointerException e) {
      // expected
    }

    try {
      createTestPrimitiveDoubleIterator().forEachRemaining((DoubleConsumer) null);
      fail();
    } catch (NullPointerException e) {
      // expected
    }
  }

  public void testForEachRemainingIntConsumer() {
    PrimitiveIterator.OfInt it = createTestPrimitiveIntIterator();
    it.forEachRemaining(
        (Consumer<@Nullable Integer>)
            new JanusIntConsumer() {
              @Override
              public void accept(@Nullable Integer value) {
                fail();
              }

              @Override
              public void accept(int value) {}
            });
  }

  public void testForEachRemainingIntConsumer_null() {
    if (isWasm()) {
      // TODO(b/183769034): Re-enable when NPE on dereference is supported
      return;
    }

    try {
      createTestPrimitiveIntIterator().forEachRemaining((Consumer<@Nullable Integer>) null);
      fail();
    } catch (NullPointerException e) {
      // expected
    }

    try {
      createTestPrimitiveIntIterator().forEachRemaining((IntConsumer) null);
      fail();
    } catch (NullPointerException e) {
      // expected
    }
  }

  public void testForEachRemainingLongConsumer() {
    PrimitiveIterator.OfLong it = createTestPrimitiveLongIterator();
    it.forEachRemaining(
        (Consumer<@Nullable Long>)
            new JanusLongConsumer() {
              @Override
              public void accept(@Nullable Long value) {
                fail();
              }

              @Override
              public void accept(long value) {}
            });
  }

  public void testForEachRemainingLongConsumer_null() {
    if (isWasm()) {
      // TODO(b/183769034): Re-enable when NPE on dereference is supported
      return;
    }

    try {
      createTestPrimitiveLongIterator().forEachRemaining((Consumer<@Nullable Long>) null);
      fail();
    } catch (NullPointerException e) {
      // expected
    }

    try {
      createTestPrimitiveLongIterator().forEachRemaining((LongConsumer) null);
      fail();
    } catch (NullPointerException e) {
      // expected
    }
  }

  private static PrimitiveIterator.OfDouble createTestPrimitiveDoubleIterator() {
    final Iterator<Double> it = Collections.singletonList(1.).iterator();
    return new PrimitiveIterator.OfDouble() {
      @Override
      public double nextDouble() {
        return it.next();
      }

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }
    };
  }

  private static PrimitiveIterator.OfInt createTestPrimitiveIntIterator() {
    final Iterator<Integer> it = Collections.singletonList(1).iterator();
    return new PrimitiveIterator.OfInt() {
      @Override
      public int nextInt() {
        return it.next();
      }

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }
    };
  }

  private static PrimitiveIterator.OfLong createTestPrimitiveLongIterator() {
    final Iterator<Long> it = Collections.singletonList(1L).iterator();
    return new PrimitiveIterator.OfLong() {
      @Override
      public long nextLong() {
        return it.next();
      }

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }
    };
  }

  private interface JanusDoubleConsumer extends Consumer<@Nullable Double>, DoubleConsumer {}

  private interface JanusIntConsumer extends Consumer<@Nullable Integer>, IntConsumer {}

  private interface JanusLongConsumer extends Consumer<@Nullable Long>, LongConsumer {}
}
