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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler.marshaller;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.custom.CustomXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler.Bean;

public class IdMarshaller extends CustomXMLSerializer<Bean.Id> {

  @Override
  protected void doSerialize(
      XMLWriter writer, Bean.Id value, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    writer.writeAttribute(propertyName, value.getId());
  }
}
