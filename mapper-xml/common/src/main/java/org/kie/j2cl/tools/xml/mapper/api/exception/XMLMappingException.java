/*
 * Copyright 2013 Nicolas Morel
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

package org.kie.j2cl.tools.xml.mapper.api.exception;

/**
 * Base exception for mapping process
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class XMLMappingException extends RuntimeException {

  /** Constructor for XMLMappingException. */
  public XMLMappingException() {}

  /**
   * Constructor for XMLMappingException.
   *
   * @param message a {@link String} object.
   */
  public XMLMappingException(String message) {
    super(message);
  }

  /**
   * Constructor for XMLMappingException.
   *
   * @param message a {@link String} object.
   * @param cause a {@link Throwable} object.
   */
  public XMLMappingException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for XMLMappingException.
   *
   * @param cause a {@link Throwable} object.
   */
  public XMLMappingException(Throwable cause) {
    super(cause);
  }
}
