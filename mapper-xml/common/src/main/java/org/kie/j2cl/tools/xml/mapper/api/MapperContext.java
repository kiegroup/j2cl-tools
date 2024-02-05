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

import java.util.Date;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.deser.bean.MapLike;
import org.kie.j2cl.tools.xml.mapper.api.stream.Stack;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * MapperContext interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface MapperContext {

  /**
   * dateFormat.
   *
   * @return a {@link DateFormat} object.
   */
  DateFormat dateFormat();

  /**
   * mapLikeFactory.
   *
   * @return a {@link MapLikeFactory} object.
   */
  MapLikeFactory mapLikeFactory();

  /**
   * defaultSerializerParameters.
   *
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters defaultSerializerParameters();

  /**
   * newSerializerParameters
   *
   * @return a new instance of {@link XMLSerializerParameters} object
   */
  XMLSerializerParameters newSerializerParameters();

  /**
   * defaultDeserializerParameters.
   *
   * @return a {@link XMLDeserializerParameters} object.
   */
  XMLDeserializerParameters defaultDeserializerParameters();

  /**
   * newDeserializerParameters
   *
   * @return a new instance of {@link XMLDeserializerParameters} object
   */
  XMLDeserializerParameters newDeserializerParameters();

  interface DateFormat {

    String format(Date date);

    String format(XMLSerializerParameters params, Date date);

    Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date);
  }

  interface IntegerStackFactory {

    Stack<Integer> make();
  }

  interface ValueStringifier {

    String stringify(String value);
  }

  interface MapLikeFactory {

    <T> MapLike<T> make();
  }

  interface StringArrayReader {

    String[] readArray(XMLReader reader) throws XMLStreamException;
  }

  interface ShortArrayReader {

    short[] readArray(XMLReader reader) throws XMLStreamException;
  }

  interface IntegerArrayReader {

    int[] readArray(XMLReader reader) throws XMLStreamException;
  }

  interface DoubleArrayReader {

    double[] readArray(XMLReader reader) throws XMLStreamException;
  }
}
