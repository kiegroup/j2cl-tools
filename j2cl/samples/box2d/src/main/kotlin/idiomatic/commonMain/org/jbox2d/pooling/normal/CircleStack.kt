/*
 * ****************************************************************************
 * Copyright (c) 2013, Daniel Murphy All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this list of conditions
 *   and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice, this list of
 *   conditions and the following disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ****************************************************************************
 */
package org.jbox2d.pooling.normal

import org.jbox2d.pooling.IOrderedStack

abstract class CircleStack<E>(private val size: Int, argContainerSize: Int) : IOrderedStack<E> {
  private val pool: Array<Any?> = Array(size) { newInstance() }
  private var index: Int = 0
  private val container: Array<Any?> = arrayOfNulls(argContainerSize)

  override fun pop(): E {
    index++
    if (index >= size) {
      index = 0
    }
    @Suppress("UNCHECKED_CAST") return pool[index] as E
  }

  override fun pop(argNum: Int): Array<E> {
    // assert is not supported in KMP.
    // assert(argNum <= container.size) { "Container array is too small" }
    if (index + argNum < size) {
      pool.copyInto(container, 0, index, argNum)
      index += argNum
    } else {
      val overlap = index + argNum - size
      pool.copyInto(container, 0, index, argNum - overlap)
      pool.copyInto(container, argNum - overlap, 0, overlap)
      index = overlap
    }
    @Suppress("UNCHECKED_CAST") return container as Array<E>
  }

  override fun push(argNum: Int) {}

  /** Creates a new instance of the object contained by this stack. */
  protected abstract fun newInstance(): E
}
