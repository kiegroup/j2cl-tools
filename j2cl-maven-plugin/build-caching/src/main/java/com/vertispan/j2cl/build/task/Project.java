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

package com.vertispan.j2cl.build.task;

import java.util.List;

/**
 *
 */
public interface Project {
    String getKey();

    List<? extends Dependency> getDependencies();

    boolean hasSourcesMapped();

    /**
     * NOTE: This method may not exist for long, if a cleaner approach can be found to handling
     * archives with JS content that shouldn't have javac/j2cl run on them.
     *
     * @return true if this project should only be used for its JS content, false otherwise
     */
    boolean isJsZip();
}
