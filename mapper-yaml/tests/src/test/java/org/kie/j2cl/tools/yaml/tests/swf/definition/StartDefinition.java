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
import org.kie.j2cl.tools.yaml.tests.swf.definition.custom.yaml.ScheduleYamlTypeSerializer;

@YAMLMapper
@JsType
public class StartDefinition {

  public String stateName;

  @YamlTypeSerializer(ScheduleYamlTypeSerializer.class)
  @YamlTypeDeserializer(ScheduleYamlTypeSerializer.class)
  private Object schedule;

  public final String getStateName() {
    return stateName;
  }

  public final void setStateName(String stateName) {
    this.stateName = stateName;
  }

  public final Object getSchedule() {
    return schedule;
  }

  public final void setSchedule(Object schedule) {
    this.schedule = schedule;
  }
}
