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
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.SubflowExecutionTypeYamlTypeSerializer;

@YAMLMapper
@JsType
public class SubFlowRef {

  public String workflowId;

  public String version;

  public FunctionRefType invoke;

  // TODO custom ser/deser because of continue is reserved java lit
  @YamlTypeSerializer(SubflowExecutionTypeYamlTypeSerializer.class)
  @YamlTypeDeserializer(SubflowExecutionTypeYamlTypeSerializer.class)
  private SubflowExecutionType onParentComplete;

  public final String getWorkflowId() {
    return workflowId;
  }

  public final void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  public final String getVersion() {
    return version;
  }

  public final void setVersion(String version) {
    this.version = version;
  }

  public final FunctionRefType getInvoke() {
    return invoke;
  }

  public final void setInvoke(FunctionRefType invoke) {
    this.invoke = invoke;
  }

  public final SubflowExecutionType getOnParentComplete() {
    return onParentComplete;
  }

  public final void setOnParentComplete(SubflowExecutionType onParentComplete) {
    this.onParentComplete = onParentComplete;
  }
}
