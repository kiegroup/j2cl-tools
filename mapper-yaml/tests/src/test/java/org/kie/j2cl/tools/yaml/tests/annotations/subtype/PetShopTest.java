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
package org.kie.j2cl.tools.yaml.tests.annotations.subtype;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(PetShopTest.class)
public class PetShopTest extends AbstractYamlTest<PetShop> {

  private static final PetShop_YamlMapperImpl mapper = PetShop_YamlMapperImpl.INSTANCE;

  public PetShopTest() {
    super(mapper);
  }

  private final String YAML =
      "animalList:"
          + "\n"
          + "  - _type: cat"
          + "\n"
          + "    isCat: true"
          + "\n"
          + "  - _type: rat"
          + "\n"
          + "    isRat: true"
          + "\n"
          + "  - _type: dog"
          + "\n"
          + "    isDog: false";

  @Test
  public void test() throws IOException {
    Cat cat = new Cat();
    cat.setIsCat(true);

    Rat rat = new Rat();
    rat.setIsRat(true);

    Dog dog = new Dog();
    dog.setIsDog(false);

    List<Animal> animalList = new ArrayList<>();
    animalList.add(cat);
    animalList.add(rat);
    animalList.add(dog);

    PetShop shop = new PetShop();
    shop.setAnimalList(animalList);

    assertEquals(YAML, mapper.write(shop));

    assertEquals(mapper.write(shop), mapper.write(mapper.read(mapper.write(shop))));
  }
}
