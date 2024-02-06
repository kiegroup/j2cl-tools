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
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLIterator;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * XMLDeserializationContext interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface XMLDeserializationContext extends XMLMappingContext {

  /**
   * isFailOnUnknownProperties.
   *
   * @return a boolean.
   */
  boolean isFailOnUnknownProperties();

  /**
   * isAcceptSingleValueAsArray.
   *
   * @return a boolean.
   */
  boolean isAcceptSingleValueAsArray();

  /**
   * isUseSafeEval.
   *
   * @return a boolean.
   */
  boolean isUseSafeEval();

  /**
   * isReadUnknownEnumValuesAsNull.
   *
   * @return a boolean.
   */
  boolean isReadUnknownEnumValuesAsNull();

  /**
   * isUseBrowserTimezone.
   *
   * @return a boolean.
   */
  boolean isUseBrowserTimezone();

  boolean isReadDateAsTimestamps();

  /**
   * newXMLReader.
   *
   * @param input a {@link String} object.
   * @return a {@link XMLReader} object.
   */
  XMLReader newXMLReader(String input) throws XMLStreamException;

  XMLIterator iterator();

  /**
   * traceError.
   *
   * @param message a {@link String} object.
   * @return a {@link XMLDeserializationException} object.
   */
  XMLDeserializationException traceError(String message) throws XMLStreamException;

  /**
   * traceError.
   *
   * @param message a {@link String} object.
   * @param reader a {@link XMLReader} object.
   * @return a {@link XMLDeserializationException} object.
   */
  XMLDeserializationException traceError(String message, XMLReader reader)
      throws XMLStreamException;

  /**
   * traceError.
   *
   * @param cause a {@link RuntimeException} object.
   * @return a {@link RuntimeException} object.
   */
  RuntimeException traceError(RuntimeException cause);

  /**
   * traceError.
   *
   * @param cause a {@link RuntimeException} object.
   * @param reader a {@link XMLReader} object.
   * @return a {@link RuntimeException} object.
   */
  RuntimeException traceError(RuntimeException cause, XMLReader reader) throws XMLStreamException;

  /**
   * defaultParameters.
   *
   * @return a {@link XMLDeserializerParameters} object.
   */
  XMLDeserializerParameters defaultParameters();
}
