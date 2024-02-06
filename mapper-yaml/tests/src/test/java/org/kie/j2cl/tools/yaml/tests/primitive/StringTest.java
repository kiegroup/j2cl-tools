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

package org.kie.j2cl.tools.yaml.tests.primitive;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.impl.Yaml;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(StringTest.class)
public class StringTest extends AbstractYamlTest<StringTest.StringType> {

  private static final String YAML_0 = "value: ~";
  private static final String YAML_1 = "value: testSerializeValue";

  private static final StringTest_StringType_YamlMapperImpl mapper =
      StringTest_StringType_YamlMapperImpl.INSTANCE;

  public StringTest() {
    super(mapper);
  }

  @Test
  public void testSerializeValue() throws IOException {
    StringTest.StringType test = new StringTest.StringType();
    test.setValue("testSerializeValue");
    assertEquals(YAML_1, mapper.write(test));
  }

  @Test
  public void testDeserializeValue() throws IOException {
    assertNull(mapper.read(YAML_0).getValue());
    assertEquals("testSerializeValue", mapper.read(YAML_1).getValue());
  }

  @Test
  public void testCron() throws IOException {
    String yaml = "schedule:\n" + "  cron: 0 0/15 * * * ?";
    YamlMapping mapping = Yaml.fromString(yaml);
    YamlMapping cron = mapping.getMappingNode("schedule").asMapping();
    YamlNode node = cron.getNode("cron");

    assertEquals("0 0/15 * * * ?", node.<String>asScalar().value());
  }

  @Test
  public void testMarshallingWithEmptyValue() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithValue() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("Hello, World!");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithCronExpression() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("0 * * ? * *");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithJavaScriptRegex() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("/\\w+/");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithCustomRegexPattern() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("^[A-Za-z0-9]+$");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithRegexFlags() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("(?i)hello");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithRegexCharacterClasses() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("[a-z]");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithRegexQuantifiers() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("a{2,4}");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithRegexAlternation() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("cat|dog");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithRegexCaptureGroups() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("(\\d{3})-(\\d{3})-(\\d{4})");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    StringType stringType1 = new StringType();
    stringType1.setValue("Hello");

    StringType stringType2 = new StringType();
    stringType2.setValue("Hello");

    assertEquals(stringType1, stringType2);
    assertEquals(stringType1.hashCode(), stringType2.hashCode());

    assertMarshallingAndUnmarshalling(stringType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    StringType stringType1 = new StringType();
    stringType1.setValue("Hello");

    StringType stringType2 = new StringType();
    stringType2.setValue("World");

    assertNotEquals(stringType1, stringType2);

    assertMarshallingAndUnmarshalling(stringType1);
    assertMarshallingAndUnmarshalling(stringType2);
  }

  @Test
  public void testMarshallingWithSpecialCharactersEscaped() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue(
        "This string contains special characters: \\, \", \', |, &, *, [, ], {, }, <, >, #, !, @, $, %, ^, (, ), -, =, :, ,, .");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithSingleLineString() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("This is a single-line string.");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithMultiLineString() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("This is a\nmulti-line\nstring.");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithEmptyLines() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("This\n\nis\n\nan\n\nempty-line\n\nstring.");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithLeadingTrailingSpaces() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("   This is a string with leading and trailing spaces.   ");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithIndentation() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("This is a string\n    with indentation.");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithTabIndentation() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("This is a string\n\twith tab indentation.");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithSpecialCharacters() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue(
        "This is a string\nwith special characters: \\, \", \', |, &, *, [, ], {, }, <, >, #, !, @, $, %, ^, (, ), -, =, :, ,, .");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithMultilineYAMLStyle() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue("This is a string\nwith\nmultiline\nYAML\nstyle.");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @Test
  public void testMarshallingWithMultilineLiteralStyle() throws IOException {
    StringType stringType = new StringType();
    stringType.setValue(
        "|-\n    This is a string\n    with\n    multiline\n    literal\n    style.");

    assertMarshallingAndUnmarshalling(stringType);
  }

  @YAMLMapper
  public static class StringType {

    private String value;

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      StringType that = (StringType) o;
      return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }
}
