/*
 * Copyright 2014 Google Inc.
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
package com.google.j2cl.benchmarks.octane.deltablue;

/** Constrains two variables to have the same value. */
class EqualityConstraint extends BinaryConstraint {

  EqualityConstraint(Variable var1, Variable var2, Strength strength) {
    super(var1, var2, strength);
    // this line needed to be added see comment in BinaryConstraint's constructor
    addConstraint();
  }

  /** Enforce this constraint. Assume that it is satisfied. */
  @Override
  void execute() {
    output().value = input().value;
  }
}
