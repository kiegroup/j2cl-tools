/*
 * Copyright 2023 Google Inc.
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
package nativejstypes;

import javax.annotation.Nonnull;
import jsinterop.annotations.JsType;
import org.jspecify.nullness.NullMarked;
import org.jspecify.nullness.Nullable;

/** Native JsType with differing parameter nullability. */
@JsType(isNative = true)
public class Nullability {
  public native void acceptsNull(String arg);

  public native void doesNotAcceptNull(@Nonnull String arg);
}

/** NullMarked native JsType with differing parameter nullability. */
@JsType(isNative = true, name = "Nullability")
@NullMarked
class NullabilityNullMarked {
  public native void acceptsNull(@Nullable String arg);

  public native void doesNotAcceptNull(String arg);
}
