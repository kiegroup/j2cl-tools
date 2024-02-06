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

package org.kie.j2cl.tools.yaml.mapper.definition;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.yaml.mapper.context.GenerationContext;

public abstract class FieldDefinition extends Definition {

  protected FieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
  }

  public abstract Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu);

  public abstract Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu);
}
