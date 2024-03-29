# mapper-json
mapper-json is an annotation-processor-based JSON-B (JSON Binding) like mapper that works both on the client side - GWT and J2CL - and on the JVM side with "Code-first" approach.

## Get started

1. Add relevant dependencies to your pom.xml

```xml
    <dependency>
        <groupId>org.kie.j2cl.tools.json.mapper</groupId>
        <artifactId>common</artifactId>
        <version>${project.version}</version>
    </dependency>

    <dependency>
        <groupId>org.kie.j2cl.tools.json.mapper</groupId>
        <artifactId>processor</artifactId>
        <version>${project.version}</version>
        <scope>provided</scope>
    </dependency>

```
2. In case you use GWT2, add the `inherits` directive to your `gwt.xml` file:

```xml
<inherits name='org.kie.j2cl.tools.json.Mapper' />
```

3. Annotate POJOs with the @JSONMapper annotation:

```xml
import org.kie.j2cl.tools.json.mapper.annotation.JSONMapper;
    
@JSONMapper
public class Person {
    
   private String firstName;
   private String lastName;
       
   public String getFirstName() {
      return firstName;
   }
       
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }
       
   public String getLastName() {
      return lastName;
   }
       
   public void setLastName(String lastName) {
       this.lastName = lastName;
   }
}
```

Setup is complete.

## Using JSON mapper

The annotation processor will generate the JSON mapper for the `Person` class.

Example of serializing `Person` to `JSON`:

```java
Person_JsonMapperImpl mapper = new Person_JsonMapperImpl();

Person person = new Person();
person.setFirstName("John");
person.setLastName("Doe");

String json = mapper.toJSON(person);
// {"firstName":"John","lastName":"Doe"}
```

Example of deserializing to POJO:

```java
Person_JsonMapperImpl mapper = new Person_JsonMapperImpl();

Person person = mapper.fromJSON("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")
```

## Supported annotations and data types:

Supported `JSON-B` annotations:

* [@JsonbProperty](###@JsonbProperty)
* [@JsonbTypeSerializer](###@JsonbTypeSerializer)
* [@JsonbTypeDeserializer](###@JsonbTypeDeserializer)
* [@JsonbTransient](###@JsonbTransient)
* [@JsonbPropertyOrder](###@JsonbPropertyOrder)

### @JsonbProperty
Allows customization of field (or JavaBean property) name.This name is used either in serialization or in deserialization.
* field

```java
public class Person {
    @JsonbProperty("_firstName")
    private String firstName;

    @JsonbProperty("_lastName")
    private String lastName;
}

//{"_firstName":"John","_lastName":"Doe"}
```

### @JsonbTypeSerializer
### @JsonbTypeDeserializer
Annotation provides way how to set custom JsonbSerializer/JsonbDeserializer to field or JavaBean property.
* field

```java
  @JsonbTypeSerializer(ObjectJsonbTypeSerializer.class)
  @JsonbTypeDeserializer(ObjectJsonbTypeDeserializer.class)
  private Object holder;
```

JsonbSerializer:
```java
public class ObjectJsonbTypeSerializer implements JsonbSerializer<Object> {

    Translation_JsonSerializerImpl translation_JsonSerializerImpl =
            new Translation_JsonSerializerImpl();

    @Override
    public void serialize(Object obj, JsonGenerator generator, SerializationContext ctx) {
        if (obj instanceof Boolean) {
            generator.write("holder", ((Boolean) obj));
        } else if (obj instanceof Translation) {
            translation_JsonSerializerImpl.serialize((Translation) obj, "holder", generator, ctx);
        }
    }
}
```

JsonbDeserializer:
```java
public class ObjectJsonbTypeDeserializer extends JsonbDeserializer<Object> {

    Translation_JsonDeserializerImpl translation_JsonDeserializerImpl =
            new Translation_JsonDeserializerImpl();

    @Override
    public Object deserialize(JsonValue value, DeserializationContext ctx) {

        if (value.getValueType() != JsonValue.ValueType.NULL) {
            if (value.getValueType() == JsonValue.ValueType.TRUE
                    || value.getValueType() == JsonValue.ValueType.FALSE) {
                if (value.getValueType() == JsonValue.ValueType.TRUE) {
                    return true;
                } else {
                    return false;
                }
            } else if (value.getValueType() == JsonValue.ValueType.OBJECT) {
                return translation_JsonDeserializerImpl.deserialize(value, ctx);
            }
        }
        return null;
    }
}
```

### @JsonbTransient
Prevents mapping of a Java Bean property, field or type to JSON representation.

* field
```java
@JSONMapper
public class Person {

    @JsonbTransient
    private String firstName;

    private String lastName;
}
//{"lastName":"Doe"}
```

### @JsonbPropertyOrder
JsonbPropertyOrder allows us to specify the order of properties in the JSON representation of the class, which can be useful when working with systems that have strict ordering requirements.

* type
```java
@JSONMapper
@JsonbPropertyOrder({"property3", "property2", "property1"})
public class MyClass {
    private String property1;
    private String property2;
    private String property3;
    // getters and setters
}
//{"property3":"value3","property2":"value2","property1":"value1"}
```
