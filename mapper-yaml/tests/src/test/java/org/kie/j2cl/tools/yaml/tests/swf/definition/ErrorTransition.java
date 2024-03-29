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

import jsinterop.annotations.JsType;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.StateEndDefinitionYamlTypeSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.StateTransitionDefinitionYamlTypeSerializer;

@JsType
public class ErrorTransition {

  public String errorRef;

  @YamlTypeSerializer(StateTransitionDefinitionYamlTypeSerializer.class)
  @YamlTypeDeserializer(StateTransitionDefinitionYamlTypeSerializer.class)
  private Object transition;

  @YamlTypeSerializer(StateEndDefinitionYamlTypeSerializer.class)
  @YamlTypeDeserializer(StateEndDefinitionYamlTypeSerializer.class)
  private Object end;

  public ErrorTransition() {}

  public String getErrorRef() {
    return errorRef;
  }

  public ErrorTransition setErrorRef(String errorRef) {
    this.errorRef = errorRef;
    return this;
  }

  public Object getTransition() {
    return transition;
  }

  public void setTransition(Object transition) {
    this.transition = transition;
  }

  public Object getEnd() {
    return end;
  }

  public void setEnd(Object end) {
    this.end = end;
  }
}
