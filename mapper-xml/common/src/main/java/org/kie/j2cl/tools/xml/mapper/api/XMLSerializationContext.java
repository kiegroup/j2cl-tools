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
package org.kie.j2cl.tools.xml.mapper.api;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLSerializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * XMLSerializationContext interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface XMLSerializationContext extends XMLMappingContext {
  /**
   * isSerializeNulls.
   *
   * @return a boolean.
   */
  boolean isSerializeNulls();

  /**
   * isWriteDatesAsTimestamps.
   *
   * @return a boolean.
   */
  boolean isWriteDatesAsTimestamps();

  /**
   * isWriteDateKeysAsTimestamps.
   *
   * @return a boolean.
   */
  boolean isWriteDateKeysAsTimestamps();

  /**
   * isWriteNullMapValues.
   *
   * @return a boolean.
   */
  boolean isWriteNullMapValues();

  /**
   * isWriteEmptyXMLArrays.
   *
   * @return a boolean.
   */
  boolean isWriteEmptyXMLArrays();

  /**
   * isOrderMapEntriesByKeys.
   *
   * @return a boolean.
   */
  boolean isOrderMapEntriesByKeys();

  /**
   * newXMLWriter.
   *
   * @return a {@link XMLWriter} object.
   */
  XMLWriter newXMLWriter() throws XMLStreamException;

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param message a {@link java.lang.String} object.
   * @return a {@link XMLSerializationException} object.
   */
  XMLSerializationException traceError(Object value, String message);

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param message a {@link java.lang.String} object.
   * @param writer a {@link XMLWriter} object.
   * @return a {@link XMLSerializationException} object.
   */
  XMLSerializationException traceError(Object value, String message, XMLWriter writer);

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param cause a {@link java.lang.RuntimeException} object.
   * @return a {@link java.lang.RuntimeException} object.
   */
  RuntimeException traceError(Object value, RuntimeException cause);

  /**
   * traceError.
   *
   * @param value a {@link java.lang.Object} object.
   * @param cause a {@link java.lang.RuntimeException} object.
   * @param writer a {@link XMLWriter} object.
   * @return a {@link java.lang.RuntimeException} object.
   */
  RuntimeException traceError(Object value, RuntimeException cause, XMLWriter writer);

  /**
   * defaultParameters.
   *
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters defaultParameters();
}
