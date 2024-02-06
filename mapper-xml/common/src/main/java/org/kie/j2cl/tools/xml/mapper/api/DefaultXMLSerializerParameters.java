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

package org.kie.j2cl.tools.xml.mapper.api;

import elemental2.core.JsNumber;
import java.util.HashSet;
import java.util.Set;
import jsinterop.base.JsPropertyMap;
import org.kie.j2cl.tools.xml.mapper.api.ser.bean.TypeSerializationInfo;

/**
 * This class includes parameters defined through properties annotations like { XMLFormat}. They are
 * specific to one {@link XMLSerializer} and that's why they are not contained inside {@link
 * XMLSerializationContext}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class DefaultXMLSerializerParameters implements XMLSerializerParameters {

  /** Constant <code>DEFAULT</code> */
  public static final XMLSerializerParameters DEFAULT = new DefaultXMLSerializerParameters();

  /**
   * Datatype-specific additional piece of configuration that may be used to further refine
   * formatting aspects. This may, for example, determine low-level format String used for {@link
   * java.util.Date} serialization; however, exact use is determined by specific {@link
   * XMLSerializer}
   */
  private String pattern;

  /** Locale to use for serialization (if needed). */
  private String locale;

  /** Names of properties to ignore. */
  private Set<String> ignoredProperties;

  /** Bean type informations */
  private TypeSerializationInfo typeInfo;

  /** If true, all the properties of an object will be serialized inside the current object. */
  private boolean unwrapped = false;

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>pattern</code>.
   */
  @Override
  public String getPattern() {
    return pattern;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>pattern</code>.
   */
  @Override
  public XMLSerializerParameters setPattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>locale</code>.
   */
  @Override
  public String getLocale() {
    return locale;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>locale</code>.
   */
  @Override
  public XMLSerializerParameters setLocale(String locale) {
    this.locale = locale;
    return this;
  }

  @Override
  public Object getTimezone() {
    return null;
  }

  @Override
  public XMLSerializerParameters setTimezone(Object timezone) {
    return null;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>ignoredProperties</code>.
   */
  @Override
  public Set<String> getIgnoredProperties() {
    return ignoredProperties;
  }

  /**
   * {@inheritDoc}
   *
   * <p>addIgnoredProperty
   */
  @Override
  public XMLSerializerParameters addIgnoredProperty(String ignoredProperty) {
    if (null == ignoredProperties) {
      ignoredProperties = new HashSet<String>();
    }
    ignoredProperties.add(ignoredProperty);
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>typeInfo</code>.
   */
  @Override
  public TypeSerializationInfo getTypeInfo() {
    return typeInfo;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>typeInfo</code>.
   */
  @Override
  public XMLSerializerParameters setTypeInfo(TypeSerializationInfo typeInfo) {
    this.typeInfo = typeInfo;
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isUnwrapped
   */
  @Override
  public boolean isUnwrapped() {
    return unwrapped;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Setter for the field <code>unwrapped</code>.
   */
  @Override
  public XMLSerializerParameters setUnwrapped(boolean unwrapped) {
    this.unwrapped = unwrapped;
    return this;
  }

  @Override
  public String doubleValue(Double value) {
    // TODO reuse options
    JsPropertyMap options = JsPropertyMap.of();
    options.set("useGrouping", false);
    options.set("minimumFractionDigits", 1);

    return new JsNumber(value)
        .toLocaleString(JsNumber.ToLocaleStringLocalesUnionType.of("us"), options);
  }
}
