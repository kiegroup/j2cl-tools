# mapper-yaml
mapper-yaml is an annotation-processor-based mapper that works both on the client side - GWT and J2CL - and on the JVM side with "Code-first" approach.

## Get started

1. Add relevant dependencies to your pom.xml

```xml
    <dependency>
        <groupId>org.kie.j2cl.tools.yaml.mapper</groupId>
        <artifactId>common</artifactId>
        <version>${project.version}</version>
    </dependency>

    <dependency>
        <groupId>org.kie.j2cl.tools.yaml.mapper</groupId>
        <artifactId>processor</artifactId>
        <version>${project.version}</version>
        <scope>provided</scope>
    </dependency>

```
2. In case you use GWT2, add the `inherits` directive to your `gwt.xml` file:

```xml
<inherits name='org.kie.j2cl.tools.yaml.Mapper' />
```

3. Annotate POJOs with the @JSONMapper annotation:

```xml
import org.kie.j2cl.tools.yaml.mapper.annotation.YAMLMapper;
    
@YAMLMapper
public class Person {
    
  private String firstName;
  private String secondName;
  private int age;
  private Address address;

  public Person() {}

  public Person(String firstName, String secondName, int age, Address address) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.age = age;
    this.address = address;
  }

  ...getters/setters omitted...
}
public class Address {

  private String country;
  private String city;
  private String street;

  public Address() {}

  public Address(String country, String city, String street) {
    this.country = country;
    this.city = city;
    this.street = street;
  }

  ...getters/setters omitted...
}
```

Setup is complete.

## Using JSON mapper

The annotation processor will generate the JSON mapper for the `Person` class.

Example of serializing `Person` to `YAML`:

```java
Person_JsonMapperImpl mapper = new Person_YamlMapperImpl();

Person test = new Person("AAA", "BBB", 99, new Address("CCC", "DDD", "EEE"));

String yaml = mapper.write(person);

        firstName: AAA
        secondName: BBB
        age: 99
        address:
          country: CCC
          city: DDD
          street: EEE

```

Example of deserializing to POJO:

```java
Person_JsonMapperImpl mapper = new Person_YamlMapperImpl();

        String YAML =
        "firstName: AAA"
        + System.lineSeparator()
        + "secondName: BBB"
        + System.lineSeparator()
        + "age: 99"
        + System.lineSeparator()
        + "address:"
        + System.lineSeparator()
        + "  country: CCC"
        + System.lineSeparator()
        + "  city: DDD"
        + System.lineSeparator()
        + "  street: EEE";


Person person = mapper.read(YAML);
```

## Supported annotations and data types:

Supported annotations:

* [@JsonbProperty](###@YamlProperty)
* [@JsonbTypeSerializer](###@JsonbTypeSerializer)
* [@JsonbTypeDeserializer](###@JsonbTypeDeserializer)
* [@JsonbTransient](###@JsonbTransient)
* [@YamlTypeInfo](###@YamlTypeInfo)
* [@YamlPropertyOrder](###@YamlPropertyOrder)

### @YamlProperty
In this example, the @YamlProperty annotation is applied to the name field. This tells the library to use "_name" as the name of the property when serializing and deserializing the object to and from YAML. When the object is serialized to YAML, the "name" field will be written as "_name". When the object is deserialized from YAML, the YAML property "_name" will be mapped to the "name" field.

* field

```java
@YAMLMapper
public static class Tested {
    @YamlProperty("_name")
    private String name;
}

//"_name: zzz"
```

### @YamlTypeSerializer
### @YamlTypeDeserializer
Annotation provides way how to set custom JsonbSerializer/JsonbDeserializer to field or JavaBean property.

In this example, the @YamlTypeSerializer annotation is applied to the value field. It specifies that the HolderSerializer class should be used to serialize the data field. The HolderSerializer class implements the YAMLSerializer interface and provides a serialize method that is used to convert the data field to a Node in YAML. Pretty much the same is about @YamlTypeDeserializer.

* field
* type

```java
    @YamlTypeSerializer(HolderSerializer.class)
    @YamlTypeDeserializer(HolderSerializer.class)
    private Object value;
```

HolderSerializer/YamlTypeDeserializer:
```java
  public class HolderSerializer implements YAMLSerializer, YAMLDeserializer {

    @Override
    public void serialize(
            YAMLWriter writer, String propertyName, Object value, YAMLSerializationContext ctx) {
        Holder holder = (Holder) value;
        writer.value(propertyName, holder.value.toUpperCase(Locale.ROOT));
    }

    @Override
    public void serialize(YAMLSequenceWriter writer, Object value, YAMLSerializationContext ctx) {
        Holder holder = (Holder) value;
        writer.value(holder.value.toUpperCase(Locale.ROOT));
    }

    @Override
    public Object deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx)
            throws YAMLDeserializationException {
        return deserialize(yaml.value(key), ctx);
    }

    @Override
    public Object deserialize(YamlNode value, YAMLDeserializationContext ctx) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        Holder holder = new Holder();
        holder.value = value.asScalar().value().toLowerCase(Locale.ROOT);
        return holder;
    }
}
```

### @YamlTransient
In this example, the @YamlTransient annotation is applied to the password field. This tells the library to exclude the password field when serializing and deserializing the object to and from YAML. When the object is serialized to YAML, the password field will not be written. When the object is deserialized from YAML, the field password will not be mapped from the YAML.
* field
```java
@JSONMapper
public class Example {

    @YamlTransient
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

```

### @YamlTypeInfo
Here is an example of using the @YamlTypeInfo annotation in a Java class to specify how the type of a property should be handled when it is converted to and from Yaml:

In this example, the @YamlTypeInfo annotation is applied to Animal class. It specifies that the type of the data field should be handled using the YamlTypeInfo.DEFAULT_KEY_NAME value which means that the type of the data field is represented by a YamlSubtype.alias that specifies the alias of the class.

```java
@YAMLMapper
public class PetShop {

    private Animal animal;  // or List<Animal>

}

@YamlTypeInfo({
@YamlSubtype(alias = "dog", type = Dog.class),
@YamlSubtype(alias = "cat", type = Cat.class),
@YamlSubtype(alias = "rat", type = Rat.class)
})
public interface Animal {}

```

### @YamlPropertyOrder
The @YamlPropertyOrder annotation is used to specify the order in which the properties of a Java object should be serialized to YAML. When this annotation is applied to a class, the properties listed within it will be serialized in the order they are listed. For example, if you have a class Person with properties name, age, and address, and you want the YAML representation to have the properties listed in the order name, address, age, you would use the annotation like this:
```java
@YamlPropertyOrder({"name", "address", "age"})
public class Person {
    private String name;
    private int age;
    private String address;
    // getters and setters
}
```
