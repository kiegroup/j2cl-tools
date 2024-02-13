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

package org.kie.j2cl.tools.xml.mapper.client.tests.doctype;

import static org.junit.Assert.assertNotNull;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(DoctypedTest.class)
public class DoctypedTest {

  private static final Doctyped_XMLMapperImpl mapper = Doctyped_XMLMapperImpl.INSTANCE;

  private static final String XML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
          + "<!DOCTYPE ldml SYSTEM \"../../common/dtd/ldml.dtd\">\n"
          + "<!-- Copyright © 1991-2021 Unicode, Inc.\n"
          + "For terms of use, see http://www.unicode.org/copyright.html\n"
          + "Unicode and the Unicode Logo are registered trademarks of Unicode, Inc. in the U.S. and other countries.\n"
          + "CLDR data files are interpreted according to the LDML specification  (http://unicode.org/reports/tr35/)\n"
          + "\n"
          + "Warnings: All cp values have U+FE0F characters removed. See /annotationsDerived/ for // derived annotations.\n"
          + "-->\n"
          + "<ldml></ldml>";

  @Test
  public void testSerializeValue() throws XMLStreamException {
    Doctyped test = new Doctyped();
    test.setField("testSerializeValue");
    assertNotNull(mapper.read(XML));
  }
}
