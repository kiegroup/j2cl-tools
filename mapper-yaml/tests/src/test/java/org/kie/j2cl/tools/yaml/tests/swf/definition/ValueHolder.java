/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
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

package org.kie.j2cl.tools.yaml.tests.swf.definition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.kie.j2cl.tools.yaml.mapper.api.node.NodeType;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;
import org.kie.j2cl.tools.yaml.mapper.api.node.impl.Yaml;

public class ValueHolder {

  private final transient YamlNode holder;

  public ValueHolder(YamlNode holder) {
    this.holder = holder;
  }

  public ValueHolder() {
    this.holder = Yaml.create();
  }

  public String getString(String key) {
    checkMapping(holder, key);
    if (!(holder.asMapping().getNode(key).asScalar().value() instanceof String)) {
      throw new IllegalArgumentException("Key " + key + " is not a String");
    }
    return holder.asMapping().<String>getScalarNode(key).value();
  }

  public Boolean getBoolean(String key) {
    checkMapping(holder, key);
    if (!(holder.asMapping().getNode(key).asScalar().value() instanceof Boolean)) {
      throw new IllegalArgumentException("Key " + key + " is not a Boolean");
    }
    return holder.asMapping().<Boolean>getScalarNode(key).value();
  }

  public Integer getInteger(String key) {
    checkMapping(holder, key);
    if (!(holder.asMapping().getNode(key).asScalar().value() instanceof Integer)) {
      throw new IllegalArgumentException("Key " + key + " is not a Integer");
    }
    return holder.asMapping().<Integer>getScalarNode(key).value();
  }

  public Double getDouble(String key) {
    checkMapping(holder, key);
    if (!(holder.asMapping().getNode(key).asScalar().value() instanceof Double)) {
      throw new IllegalArgumentException("Key " + key + " is not a Double");
    }
    return holder.asMapping().<Double>getScalarNode(key).value();
  }

  public int getSequenceSize() {
    if (holder.type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Holder is not a Sequence");
    }
    return holder.asSequence().size();
  }

  public ValueHolder getSequenceElement(int index) {
    if (holder.type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Holder is not a Sequence");
    }
    return new ValueHolder(holder.asSequence().node(index));
  }

  public Integer asInteger() {
    if (holder.type() != NodeType.SCALAR) {
      throw new IllegalArgumentException("Holder is not a Scalar");
    }
    return holder.<Integer>asScalar().value();
  }

  public String asString() {
    if (holder.type() != NodeType.SCALAR) {
      throw new IllegalArgumentException("Holder is not a Scalar");
    }
    return holder.<String>asScalar().value();
  }

  public Boolean asBoolean() {
    if (holder.type() != NodeType.SCALAR) {
      throw new IllegalArgumentException("Holder is not a Scalar");
    }
    return holder.<Boolean>asScalar().value();
  }

  public Double asDouble() {
    if (holder.type() != NodeType.SCALAR) {
      throw new IllegalArgumentException("Holder is not a Scalar");
    }
    return holder.<Double>asScalar().value();
  }

  public ValueHolder getValueHolder(String key) {
    checkMapping(holder, key);
    if (holder.asMapping().getNode(key).type() != NodeType.MAPPING) {
      throw new IllegalArgumentException("Key " + key + " is not a Mapping");
    }
    return new ValueHolder(holder.asMapping().getMappingNode(key));
  }

  public Collection<ValueHolder> getSequence(String key) {
    checkMapping(holder, key);
    if (holder.asMapping().getNode(key).type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Key " + key + " is not a Sequence");
    }
    List<ValueHolder> result = new ArrayList<>();
    for (int i = 0; i < holder.asMapping().getSequenceNode(key).size(); i++) {
      result.add(new ValueHolder(holder.asMapping().getSequenceNode(key).node(i)));
    }
    return result;
  }

  public void addToSequence(ValueHolder valueHolder) {
    if (holder.type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Holder is not a Sequence");
    }
    holder.asSequence().addNode(valueHolder.holder);
  }

  public void addToSequence(String value) {
    if (holder.type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Holder is not a Sequence");
    }
    holder.asSequence().addScalarNode(value);
  }

  public void addToSequence(Integer value) {
    if (holder.type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Holder is not a Sequence");
    }
    holder.asSequence().addScalarNode(value);
  }

  public void addToSequence(Double value) {
    if (holder.type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Holder is not a Sequence");
    }
    holder.asSequence().addScalarNode(value);
  }

  public void addToSequence(Boolean value) {
    if (holder.type() != NodeType.SEQUENCE) {
      throw new IllegalArgumentException("Holder is not a Sequence");
    }
    holder.asSequence().addScalarNode(value);
  }

  public ValueHolder addSequence(String key) {
    if (holder.type() != NodeType.MAPPING) {
      throw new IllegalArgumentException("Holder is not a Mapping");
    }
    YamlSequence seq = holder.asMapping().addSequenceNode(key);
    return new ValueHolder(seq);
  }

  public void addToValueHolder(String key, ValueHolder valueHolder) {
    if (holder.type() != NodeType.MAPPING) {
      throw new IllegalArgumentException("Holder is not a Mapping");
    }
    holder.asMapping().addNode(key, valueHolder.holder);
  }

  private void checkMapping(YamlNode holder, String key) {
    if (holder.type() == NodeType.MAPPING) {
      throw new IllegalArgumentException("Holder is not a Mapping");
    }
    YamlMapping mapping = holder.asMapping();
    if (!mapping.keys().contains(key)) {
      throw new IllegalArgumentException("Key " + key + " not found");
    }
    if (mapping.getNode(key).type() != NodeType.SCALAR) {
      throw new IllegalArgumentException("Key " + key + " is not a Scalar");
    }
  }

  public YamlNode getHolder() {
    return holder;
  }
}
