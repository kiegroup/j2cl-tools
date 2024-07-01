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
package overwrittentypevariables;

interface MyFunction<F, T> {
  T apply(F input);
}

interface HashFunction<T> extends MyFunction<T, String> {}

public class HashFunctions {
  public static final <T> HashFunction<T> hashFunction() {
    return new HashFunction<T>() {
      @Override
      public String apply(T input) {
        return "";
      }
    };
  }

  public static final <T extends Enum<T>> HashFunction<T> enumHashFunction() {
    return new HashFunction<T>() {
      @Override
      public String apply(T input) {
        return "" + input.ordinal();
      }
    };
  }
}
