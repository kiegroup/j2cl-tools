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
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlPropertyOrder;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.FunctionRefYamlSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.SubFlowRefYamlSerializer;

@JsType
@YamlPropertyOrder({
  "name",
  "id",
  "functionRef",
  "eventRef",
  "subFlowRef",
  "retryRef",
  "sleep",
  "retryableErrors",
  "nonRetryableErrors",
  "actionDataFilter",
  "condition"
})
public class ActionNode {

  public String id;

  public String name;

  @YamlTypeSerializer(FunctionRefYamlSerializer.class)
  @YamlTypeDeserializer(FunctionRefYamlSerializer.class)
  private Object functionRef;

  public ActionEventRef eventRef;

  @YamlTypeSerializer(SubFlowRefYamlSerializer.class)
  @YamlTypeDeserializer(SubFlowRefYamlSerializer.class)
  private Object subFlowRef;

  public String retryRef;

  public Sleep sleep;

  public String[] retryableErrors;

  public String[] nonRetryableErrors;

  public ActionDataFilters actionDataFilter;

  public String condition;

  public ActionNode() {}

  public ActionNode setName(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public ActionNode setId(String id) {
    this.id = id;
    return this;
  }

  public Object getFunctionRef() {
    return functionRef;
  }

  public ActionNode setFunctionRef(Object functionRef) {
    this.functionRef = functionRef;
    return this;
  }

  public ActionEventRef getEventRef() {
    return eventRef;
  }

  public ActionNode setEventRef(ActionEventRef eventRef) {
    this.eventRef = eventRef;
    return this;
  }

  public Object getSubFlowRef() {
    return subFlowRef;
  }

  public ActionNode setSubFlowRef(Object subFlowRef) {
    this.subFlowRef = subFlowRef;
    return this;
  }

  public String getRetryRef() {
    return retryRef;
  }

  public void setRetryRef(String retryRef) {
    this.retryRef = retryRef;
  }

  public Sleep getSleep() {
    return sleep;
  }

  public void setSleep(Sleep sleep) {
    this.sleep = sleep;
  }

  public String[] getRetryableErrors() {
    return retryableErrors;
  }

  public void setRetryableErrors(String[] retryableErrors) {
    this.retryableErrors = retryableErrors;
  }

  public String[] getNonRetryableErrors() {
    return nonRetryableErrors;
  }

  public void setNonRetryableErrors(String[] nonRetryableErrors) {
    this.nonRetryableErrors = nonRetryableErrors;
  }

  public ActionDataFilters getActionDataFilter() {
    return actionDataFilter;
  }

  public void setActionDataFilter(ActionDataFilters actionDataFilter) {
    this.actionDataFilter = actionDataFilter;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }
}
