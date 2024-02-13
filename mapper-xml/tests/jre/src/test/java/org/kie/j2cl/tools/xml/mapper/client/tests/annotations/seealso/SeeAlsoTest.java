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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.seealso;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.seealso.type.SeeAlsoAnimalXsiTypeHolder;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.seealso.type.SeeAlsoAnimalXsiTypeHolder_XMLMapperImpl;

@J2clTestInput(SeeAlsoTest.class)
public class SeeAlsoTest {

  private final String XML_FOO =
      "<?xml version='1.0' encoding='UTF-8'?><SeeAlsoHolder><first xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"First\"><test1>first</test1><test>Test1</test></first><second xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Second\"><test2>second</test2><test>Test1</test></second></SeeAlsoHolder>";
  private final String XML_ANIMAL =
      "<?xml version='1.0' encoding='UTF-8'?><SeeAlsoAnimalHolder><first xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></first><second xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></second><animal><name>Animal</name></animal></SeeAlsoAnimalHolder>";
  private final String XML_XSI =
      "<?xml version='1.0' encoding='UTF-8'?><SeeAlsoAnimalXsiTypeHolder xmlns=\"http://www.omg.org/bpmn20\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><first xsi:type=\"Cat\"><nickname>Cat</nickname></first><second xsi:type=\"Dog\"><nickname>Dog</nickname></second><animal><name>Animal</name></animal></SeeAlsoAnimalXsiTypeHolder>";
  private final String XML_COLLECTION_XSI =
      "<?xml version='1.0' encoding='UTF-8'?><SeeAlsoAnimalCollection><animals><Cat xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></Cat><Dog xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></Dog><Animal><name>Animal</name></Animal></animals><list><list xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></list><list xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></list><list><name>Animal</name></list></list><map><entry><key xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></key><value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></value></entry><entry><key xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></key><value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></value></entry><entry><key><name>Animal</name></key><value><name>Animal</name></value></entry></map><vehicle xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Car\"><name>CAR</name></vehicle></SeeAlsoAnimalCollection>";

  private final String XML_COLLECTION_XSI_UNWRAP =
      "<?xml version='1.0' encoding='UTF-8'?><SeeAlsoAnimalCollection><animals><Cat xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></Cat><Dog xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></Dog><Animal><name>Animal</name></Animal></animals><list xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></list><list xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></list><list><name>Animal</name></list><map><entry><key xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></key><value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Dog\"><nickname>Dog</nickname></value></entry><entry><key xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></key><value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Cat\"><nickname>Cat</nickname></value></entry><entry><key><name>Animal</name></key><value><name>Animal</name></value></entry></map><vehicle xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Car\"><name>CAR</name></vehicle></SeeAlsoAnimalCollection>";

  @Test
  public void testFoo() throws XMLStreamException {
    final SeeAlsoTest_SeeAlsoHolder_XMLMapperImpl mapper =
        SeeAlsoTest_SeeAlsoHolder_XMLMapperImpl.INSTANCE;

    First first = new First();
    first.setTest1("first");
    first.setTest("Test1");

    Second second = new Second();
    second.setTest2("second");
    second.setTest("Test1");

    SeeAlsoHolder holder = new SeeAlsoHolder();
    holder.first = first;
    holder.second = second;

    String result = mapper.write(holder);
    assertEquals(XML_FOO, result);
    assertEquals(holder, mapper.read(mapper.write(holder)));
  }

  @Test
  public void testAnimal() throws XMLStreamException {
    final SeeAlsoTest_SeeAlsoAnimalHolder_XMLMapperImpl mapper =
        SeeAlsoTest_SeeAlsoAnimalHolder_XMLMapperImpl.INSTANCE;
    final Animal_XMLMapperImpl mapperAnimal = Animal_XMLMapperImpl.INSTANCE;

    Animal animal = new Animal();
    animal.setName("Animal");

    Cat cat = new Cat();
    cat.setNickname("Cat");

    Dog dog = new Dog();
    dog.setNickname("Dog");

    SeeAlsoAnimalHolder holder = new SeeAlsoAnimalHolder();
    holder.first = cat;
    holder.second = dog;
    holder.animal = animal;

    String result = mapper.write(holder);
    assertEquals(XML_ANIMAL, result);
    assertEquals(holder, mapper.read(mapper.write(holder)));
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><Animal><name>Animal</name></Animal>",
        mapperAnimal.write(animal));
    assertEquals(animal, mapperAnimal.read(mapperAnimal.write(animal)));
  }

  @Test
  public void testAnimalCollection() throws XMLStreamException {
    SeeAlsoTest_SeeAlsoAnimalCollection_XMLMapperImpl mapper =
        SeeAlsoTest_SeeAlsoAnimalCollection_XMLMapperImpl.INSTANCE;

    SeeAlsoAnimalCollection collection = getSeeAlsoAnimalCollection();
    assertEquals(collection, mapper.read(mapper.write(collection)));
  }

  private SeeAlsoAnimalCollection getSeeAlsoAnimalCollection() {
    SeeAlsoAnimalCollection collection = new SeeAlsoAnimalCollection();
    List<Animal> list = new LinkedList<>();

    Animal animal = new Animal();
    animal.setName("Animal");

    Cat cat = new Cat();
    cat.setNickname("Cat");

    Dog dog = new Dog();
    dog.setNickname("Dog");

    list.add(animal);
    list.add(dog);
    list.add(dog);
    list.add(cat);
    list.add(cat);
    list.add(animal);
    list.add(dog);
    list.add(animal);

    Animal[] animals = new Animal[] {animal, dog, cat, dog, animal, animal};

    Map<Animal, Animal> map = new LinkedHashMap<>();
    map.put(dog, dog);
    map.put(cat, cat);
    map.put(animal, animal);

    collection.setList(list);
    collection.setMap(map);

    Car car = new Car("CAR");
    List<Vehicle> list2 = new ArrayList<>();
    list2.add(car);
    return collection;
  }

  @Test
  public void testSeeAlsoAnimalXsiTypeHolder() throws XMLStreamException {
    final SeeAlsoAnimalXsiTypeHolder_XMLMapperImpl mapper =
        SeeAlsoAnimalXsiTypeHolder_XMLMapperImpl.INSTANCE;

    Animal animal = new Animal();
    animal.setName("Animal");

    Cat cat = new Cat();
    cat.setNickname("Cat");

    Dog dog = new Dog();
    dog.setNickname("Dog");

    SeeAlsoAnimalXsiTypeHolder holder = new SeeAlsoAnimalXsiTypeHolder();
    holder.setFirst(cat);
    holder.setSecond(dog);
    holder.setAnimal(animal);

    String result = mapper.write(holder);

    assertEquals(XML_XSI, result);
    assertEquals(holder, mapper.read(mapper.write(holder)));
  }

  @XMLMapper
  public static class SeeAlsoHolder {

    private Foo first;
    private Foo second;

    @Override
    public int hashCode() {
      return Objects.hash(getFirst(), getSecond());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof SeeAlsoHolder)) {
        return false;
      }
      SeeAlsoHolder holder = (SeeAlsoHolder) o;
      return Objects.equals(getFirst(), holder.getFirst())
          && Objects.equals(getSecond(), holder.getSecond());
    }

    public Foo getFirst() {
      return first;
    }

    public void setFirst(Foo first) {
      this.first = first;
    }

    public Foo getSecond() {
      return second;
    }

    public void setSecond(Foo second) {
      this.second = second;
    }
  }

  @XMLMapper
  public static class SeeAlsoAnimalHolder {

    private Animal first;
    private Animal second;
    private Animal animal;

    @Override
    public int hashCode() {
      return Objects.hash(getFirst(), getSecond(), getAnimal());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof SeeAlsoAnimalHolder)) {
        return false;
      }
      SeeAlsoAnimalHolder that = (SeeAlsoAnimalHolder) o;
      return Objects.equals(getFirst(), that.getFirst())
          && Objects.equals(getSecond(), that.getSecond())
          && Objects.equals(getAnimal(), that.getAnimal());
    }

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
  }

  @XMLMapper
  public static class SeeAlsoAnimalArray {

    private Animal[] animals;

    private Vehicle[] vehicles2;

    public Animal[] getAnimals() {
      return animals;
    }

    public void setAnimals(Animal[] animals) {
      this.animals = animals;
    }

    public Vehicle[] getVehicles2() {
      return vehicles2;
    }

    public void setVehicles2(Vehicle[] vehicles2) {
      this.vehicles2 = vehicles2;
    }
  }

  @XMLMapper
  public static class SeeAlsoAnimalCollection {

    private List<Animal> list;
    private Map<Animal, Animal> map;
    private List<Vehicle> vehicles;

    public List<Animal> getList() {
      return list;
    }

    public void setList(List<Animal> list) {
      this.list = list;
    }

    public Map<Animal, Animal> getMap() {
      return map;
    }

    public void setMap(Map<Animal, Animal> map) {
      this.map = map;
    }

    public List<Vehicle> getVehicles() {
      return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
      this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof SeeAlsoAnimalCollection)) return false;

      SeeAlsoAnimalCollection that = (SeeAlsoAnimalCollection) o;

      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      if (getList() != null ? !getList().equals(that.getList()) : that.getList() != null)
        return false;
      if (getMap() != null ? !getMap().equals(that.getMap()) : that.getMap() != null) return false;
      if (getVehicles() != null
          ? !getVehicles().equals(that.getVehicles())
          : that.getVehicles() != null) return false;
      return true;
    }

    @Override
    public int hashCode() {
      int result = 31 * (getList() != null ? getList().hashCode() : 0);
      result = 31 * result + (getMap() != null ? getMap().hashCode() : 0);
      result = 31 * result + (getVehicles() != null ? getVehicles().hashCode() : 0);
      return result;
    }
  }
}
