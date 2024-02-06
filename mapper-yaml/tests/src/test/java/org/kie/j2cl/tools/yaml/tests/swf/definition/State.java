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
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlPropertyOrder;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.StateEndDefinitionYamlTypeSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.StateTransitionDefinitionYamlTypeSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.WorkflowTimeoutsYamlSerializer;

@JsType
@YAMLMapper
@YamlPropertyOrder({
  "name",
  "type",
  "end",
  "onErrors",
  "eventTimeout",
  "compensatedBy",
  "stateDataFilter",
  "transition",
  "timeouts"
})
public class State {

  public String name;

  public String type;

  public Metadata metadata;

  // TODO: Not all states supports this (eg: switch state)
  @YamlTypeSerializer(StateTransitionDefinitionYamlTypeSerializer.class)
  @YamlTypeDeserializer(StateTransitionDefinitionYamlTypeSerializer.class)
  private Object transition;

  // TODO: Not all states supports this (eg: switch state)
  @YamlTypeSerializer(StateEndDefinitionYamlTypeSerializer.class)
  @YamlTypeDeserializer(StateEndDefinitionYamlTypeSerializer.class)
  private Object end;

  public ErrorTransition[] onErrors;

  public String compensatedBy;

  public StateDataFilter stateDataFilter;

  @YamlTypeSerializer(WorkflowTimeoutsYamlSerializer.class)
  @YamlTypeDeserializer(WorkflowTimeoutsYamlSerializer.class)
  private Object timeouts;

  public State() {
    this.name = "State";
  }

  public State setName(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public State setType(String type) {
    this.type = type;
    return this;
  }

  public Object getTransition() {
    return transition;
  }

  public State setTransition(Object transition) {
    this.transition = transition;
    return this;
  }

  public Object getEnd() {
    return end;
  }

  public State setEnd(Object end) {
    this.end = end;
    return this;
  }

  public ErrorTransition[] getOnErrors() {
    return onErrors;
  }

  public State setOnErrors(ErrorTransition[] onErrors) {
    this.onErrors = onErrors;
    return this;
  }

  public String getCompensatedBy() {
    return compensatedBy;
  }

  public State setCompensatedBy(String compensatedBy) {
    this.compensatedBy = compensatedBy;
    return this;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public StateDataFilter getStateDataFilter() {
    return stateDataFilter;
  }

  public void setStateDataFilter(StateDataFilter stateDataFilter) {
    this.stateDataFilter = stateDataFilter;
  }

  public Object getTimeouts() {
    return timeouts;
  }

  public void setTimeouts(Object timeouts) {
    this.timeouts = timeouts;
  }
}
