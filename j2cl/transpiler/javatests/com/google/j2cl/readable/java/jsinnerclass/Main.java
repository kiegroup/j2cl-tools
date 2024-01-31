/*
 * Copyright 2017 Google Inc.
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
package jsinnerclass;

import jsinterop.annotations.JsType;

public class Main {
  static class Outer {
    int a = 2;

    @JsType(namespace = "com.google.test")
    public class Inner {
      private int b;

      public Inner() {
        this.b = a + 1;
      }

      public int getB() {
        return b;
      }
    }

    public int method() {
      return new Inner().getB() + a;
    }
  }

  public static void main(String... args) {
    assert new Outer().method() == 5;
  }
}
