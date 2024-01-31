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

package ${package};

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import jsinterop.annotations.JsType;

@JsType
public class ${module} {

    public static final String HELLO_WORLD = "Hello J2CL world!";

    public void onModuleLoad() {
        HTMLDivElement wrapper = (HTMLDivElement) DomGlobal.document.createElement("div");
        wrapper.classList.add("wrapper");

        HTMLButtonElement btn = (HTMLButtonElement) DomGlobal.document.createElement("button");
        btn.classList.add("myButton");
        btn.textContent = "J2CL !!!";

        btn.addEventListener("click", evt -> btn.textContent = helloWorldString());

        wrapper.appendChild(btn);

        DomGlobal.document.body.appendChild(wrapper);
    }

    public String helloWorldString() {
        return HELLO_WORLD;
    }
}
