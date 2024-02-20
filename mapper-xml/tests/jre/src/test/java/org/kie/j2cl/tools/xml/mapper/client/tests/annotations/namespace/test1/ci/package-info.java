/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@XmlSchema(
    namespace = "http://www.omg.org/bpmn20",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
      @XmlNs(prefix = "ci", namespaceURI = "http://www.ci"),
      @XmlNs(prefix = "cl", namespaceURI = "http://www.cl")
    })
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test1.ci;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;