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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.seealso.type;

import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.seealso.Animal;

@XMLMapper
public class SeeAlsoAnimalXsiTypeHolder {

  private Animal first;
  private Animal second;
  private Animal animal;

  public Animal getFirst() {
    return first;
  }

  public void setFirst(Animal first) {
    this.first = first;
  }

  public Animal getSecond() {
    return second;
  }

  public void setSecond(Animal second) {
    this.second = second;
  }

  public Animal getAnimal() {
    return animal;
  }

  public void setAnimal(Animal animal) {
    this.animal = animal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SeeAlsoAnimalXsiTypeHolder)) {
      return false;
    }
    SeeAlsoAnimalXsiTypeHolder that = (SeeAlsoAnimalXsiTypeHolder) o;
    return Objects.equals(getFirst(), that.getFirst())
        && Objects.equals(getSecond(), that.getSecond())
        && Objects.equals(getAnimal(), that.getAnimal());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFirst(), getSecond(), getAnimal());
  }
}
