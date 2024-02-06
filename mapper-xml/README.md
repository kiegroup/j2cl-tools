
# mapper-xml
**mapper-xml** is an annotation-processor-based XML mapper that works both on the client side - GWT and J2CL - and on the JVM side with "Code-first" approach.

mapper-xml is a relaxed implementation of the Jakarta JAXB specification, which means that it is not 100% compatible with the JAXB specification;

## Maven

Artifacts are published to sonatype repo

```xml
    https://mvnrepository.com/artifact/org.kie.j2cl.tools.xml.mapper
```


## Installing xml-mapper:

1. Add relevant dependencies to the `pom` file:

    1.1. Add the following dependencies:
    
    - For the JRE environment:

    ```xml
    <dependency>
      <groupId>com.fasterxml.woodstox</groupId>
      <artifactId>woodstox-core</artifactId>
      <scope>test</scope>
      <version>6.2.1</version>
    </dependency>
    ```

    1.2. For both JRE and GWT2/J2CL environments, add the API and annotation processor dependencies:
    
    ```xml
    <dependency>
      <groupId>org.kie.j2cl.tools.xml.mapper</groupId>
      <artifactId>api</artifactId>
      <version>0.7</version>
    </dependency>
    <dependency>
      <groupId>org.kie.j2cl.tools.xml.mapper</groupId>
      <artifactId>processor</artifactId>
      <version>0.7</version>
      <scope>provided</scope>
    </dependency>
    ```
    
    > In case you use GWT2, add the `inherits` directive to your `gwt.xml` file:

    ```xml
      <inherits name="org.kie.j2cl.tools.xml.Mapper"/>
    ```

2. Annotate POJOs with the @XMLMapper annotation:

    ```xml
    import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
    
    @XMLMapper
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

## Using XML mapper

The annotation processor will generate the XML mapper for the `Person` class.

Example of serializing `Person` to `XML`:

```xml
  Person_XMLMapperImpl mapper = Person_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    Person person = new Person();
    person.setFirstName("FirstName");
    person.setLastName("LastName");

    String result = mapper.write(person);
    //<?xml version='1.0' encoding='UTF-8'?><Person><firstName>FirstName</firstName><lastName>LastName</lastName></Person>
    
  }
```

Example of deserializing to POJO:

```xml
    Person person1 = mapper.read(result);
```

## Supported annotations and data types:

Supported `JAXB` annotations:

* [@XmlAttribute](###xmlattribute)
* [@XmlElement](#xmlelement)
* [@XmlCData](#xmlcdata)
* [@XmlTransient](#xmltransient)
* [@XmlRootElement](#xmlrootelement)
* [@XmlType](#xmltype)
* [@XmlElementWrapper](#xmlelementwrapper)
* [@XmlSchema](#xmlschema)
* [@XmlNs](#xmlns)
* [@XmlSeeAlso](#xmlseealso)
* [@XmlElements](#xmlelements)
* [@XmlElementRefs](#xmlelementrefs)
* [@XmlElementRef](#xmlelementrefs)
* [@XmlJavaTypeAdapter](#xmljavatypeadapter)
* [@XmlValue](#xmlvalue)
* [@XmlEnumValue](#xmlenumvalue)
* XmlNsForm
* XmlAccessorType
* XmlAccessType

Supported data types:

```java
    //primitives and boxed
    public String stringField;
    public byte byteField;
    public Byte boxedByteField;
    public short shortField;
    public Short boxedShortField;
    public int intField;
    public Integer boxedIntField;
    public long longField;
    public Long boxedLongField;
    public double doubleField;
    public Double boxedDoubleField;
    public float floatField;
    public Float boxedFloatField;
    public boolean booleanField;
    public Boolean boxedBooleanField;
    public char charField;
    public Character boxedCharField;
    
    //special types
    public BigInteger bigIntegerField;
    public BigDecimal bigDecimalField;
    public Date dateField;
    public java.sql.Date sqlDateField;
    public Time timeField;
    public Timestamp timestampField;
    public UUID uui;

    //Enums
    public Enum enumField;

    //1 dimensional primitives arrays
    public byte[] byteFieldArray;
    public short[] shortFieldArray;
    public int[] intFieldArray;
    public long[] longFieldArray;
    public double[] doubleFieldArray;
    public float[] floatFieldArray;
    public boolean[] booleanFieldArray;
    public char[] charFieldArray;

    //2 dimensional primitives arrays
    public byte[][] byteFieldArray2d;
    public short[][] shortFieldArray2d;
    public int[][] intFieldArray2d;
    public long[][] longFieldArray2d;
    public double[][] doubleFieldArray2d;
    public float[][] floatFieldArray2d;
    public boolean[][] booleanFieldArray2d;
    public char[][] charFieldArray2d;

    //1 dimensional Boxed arrays
    public String[] stringFieldArray;
    public Byte[] boxedByteFieldArray;
    public Short[] boxedShortFieldArray;
    public Integer[] boxedIntFieldArray;
    public Long[] boxedLongFieldArray;
    public Double[] boxedDoubleFieldArray;
    public Float[] boxedFloatFieldArray;
    public Boolean[] boxedBooleanFieldArray;
    public Character[] boxedCharFieldArray;
    
    //1 dimensional special types arrays
    public BigInteger[] bigIntegerFieldArray;
    public BigDecimal[] bigDecimalFieldArray;
    public Date[] dateFieldArray;
    public java.sql.Date[] sqlDateFieldArray;
    public Time[] timeFieldArray;
    public Timestamp[] timestampFieldArray;

    //2 dimensional boxed arrays
    public String[][] stringFieldArray2d;
    public Byte[][] boxedByteFieldArray2d;
    public Short[][] boxedShortFieldArray2d;
    public Integer[][] boxedIntFieldArray2d;
    public Long[][] boxedLongFieldArray2d;
    public Double[][] boxedDoubleFieldArray2d;
    public Float[][] boxedFloatFieldArray2d;
    public Boolean[][] boxedBooleanFieldArray2d;
    public Character[][] boxedCharFieldArray2d;
    
    //collections
    public AbstractCollection<String> abstractCollection;
    public AbstractList<String> abstractList;
    public AbstractQueue<String> abstractQueue;
    public AbstractSequentialList<String> abstractSequentialList;
    public AbstractSet<String> abstractSet;
    public ArrayList<String> arrayList;
    public Collection<String> collection;
    public EnumSet<AnEnum> enumSet;
    public HashSet<String> hashSet;
    public Iterable<String> iterable;
    public LinkedHashSet<String> linkedHashSet;
    public LinkedList<String> linkedList;
    public List<String> list;
    public PriorityQueue<String> priorityQueue;
    public Queue<String> queue;
    public Set<String> set;
    public SortedSet<String> sortedSet;
    public Stack<String> stack;
    public TreeSet<String> treeSet;
    public Vector<String> vector;
    
    //Maps
    public AbstractMap<String, String> abstractMap;
    public EnumMap<AnEnum, Integer> enumMap;
    public HashMap<Integer, Double> hashMap;
    public IdentityHashMap<Long, Date> identityHashMap;
    public LinkedHashMap<Double, AnEnum> linkedHashMap;
    public Map<Short, Time> map;
    public SortedMap<String, Short> sortedMap;
    public TreeMap<String, BigInteger> treeMap;
    
    public AnotherBean anotherBean;
```
    
## Annotations:
    
### @XmlAttribute
@XmlAttribute defines the field that will be mapped as an attribute instead of an element. Name value can be used to override the attribute name.

Java:
```java
    @XmlAttribute(name = "_id")
    private int id;
```
XML:
```xml
    <Person _id="111" />
```
    
### @XmlElement
@XmlElement defines the actual XML element name that will be used.

Java:
```java
    @XmlAttribute(name = "_id")
    private int id;
```
XML:
```xml
    <Person><_id>111</_id></Person>
```

### @XmlCData
@XmlCData defines the field that will be mapped as a CDATA section. Use it with String fields.

Java:
```java
    @XmlCData
    private String id;
```  
XML:
```xml
    <Person><id><![CDATA[111]]></id><firstName>FirstName</firstName><lastName>LastName</lastName></Person>
```

### @XmlTransient
@XmlTransient annotates fields that we don't want to include in the future XML file. It's also possible to use the transient modifier.

Java:
```java
    @XmlTransient
    private String id;
``` 
```java
    private transient String id;
``` 

### @XmlRootElement
The name of the root XML element is derived from the class name. We can also specify the name of the root element of the XML using its name attribute.

Java:
```java
    @XmlRootElement(name = "person")
    public class Person {
```
    
XML:
```xml
    <person><id>1</id><firstName>FirstName</firstName><lastName>LastName</lastName></person>
```
    
Namespace is used to add the `xmlns` declaration to the element.

Java:
```java
    @XmlRootElement(name = "person", namespace = "https://my.namespace")
    public class Person {
```
XML:
```xml
<person xmlns="https://my.namespace"><id>1</id><firstName>FirstName</firstName><lastName>LastName</lastName></person>
```
     
### @XmlType
@XmlType defines the order in which the fields are written in the XML file.

Java:
```java
    @XMLMapper
    @XmlType(propOrder = {"lastName", "firstName","id"})
    public class Person {

    @XmlCData
     private String id;
     private String firstName;
     private String lastName;
```
XML:
```xml
  <Person><lastName>LastName</lastName><firstName>FirstName</firstName><id><![CDATA[111]]></id></Person>
```

### @XmlElementWrapper  
@XmlElementWrapper generates a wrapper element around XML elements.

Java:
```java
    @XmlElementWrapper(name = "XmlElementWrapper")
    private String firstName;
```
XML:
```xml
  <XmlElementWrapper><firstName>FirstName</firstName></XmlElementWrapper>
```

### @XmlSchema

@XmlSchema is used on the package to set a default `namespace` attribute and specify that all elements in the package are qualified with the `namespace`. This information is specified in a special Java source file: `package-info.java`.
 
Java:

```java
    @XmlSchema(namespace = "http://www.omg.org/bpmn20")
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.simple;

import jakarta.xml.bind.annotation.XmlSchema;
```
     
XML:
```xml
  <Person xmlns="http://www.omg.org/bpmn20">
```

Sometimes it's necessary to add `xsi:schemaLocation` to the root element. An example:

Java:

```java
     @XmlSchema(namespace = "http://www.omg.org/bpmn20",
        xmlns = {
                @XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance"),
                @XmlNs(prefix = "drools", namespaceURI = "http://www.jboss.org/drools")
        },
        location =
                "http://www.jboss.org/drools "
)
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.simple;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
```
     
XML:
```xml
  <Person xmlns="http://www.omg.org/bpmn20" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:drools="http://www.jboss.org/drools" xsi:schemaLocation="http://www.jboss.org/drools ">
```

@XmlType can be used to override the `namespace` declaration.
     
```java
    @XmlType(namespace = "http://www.example.org/type")
```

### @XmlNs
@XmlNs is used to add additional `namespace` declarations in @XmlSchema;

Java:

```java
     @XmlSchema(namespace = "http://www.omg.org/bpmn20",
        xmlns = {
                @XmlNs(prefix = "drools", namespaceURI = "http://www.jboss.org/drools")
        }
)
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.simple;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
```
     
XML:
```xml
     <Person xmlns="http://www.example.org/type" xmlns:drools="http://www.jboss.org/drools">
```

### @XmlSeeAlso
@XmlSeeAlso instructs a marshaller to also bind other classes when binding this class. Subclasses must be annotated with @XmlRootElement.

Java:
```java
    @XmlSeeAlso({Dog.class,Cat.class})
    class Animal {}
    class Dog extends Animal {}
    class Cat extends Animal {}
    
    public class SomeClass {
        private Animal animal;
    }
```

XML:
```xml
     <animal xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="Dog">
```

### @XmlElements
Compared to an element property (property with XmlElement annotation), a reference property has a different substitution semantics. When a subclass is assigned to a property, an element property produces the same tag name with @xsi:type, whereas a reference property produces a different tag name (the tag name that's on the subclass.)

Java: 
```java
    @XmlElements({
    @XmlElement(name = "_Address1", type = Address.class),
    @XmlElement(name = "_Address2", type = Address2.class),
    @XmlElement(name = "_Address3", type = Address3.class)
    })
    private List<IAddress> iAddressList;
```
     
XML:
```xml
  <iAddressList xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="_Address1" />
``` 

### @XmlElementRefs
Compared to an element property (property with {@link XmlElement} annotation), a reference property has a different substitution semantics. When a subclass is assigned to a property, an element property produces the same tag name with @xsi:type, whereas a reference property produces a different tag name (the tag name that's on the subclass.)

Java:
```java
    @XmlElementRefs({
    @XmlElementRef(name = "_Address1", type = Address.class),
    @XmlElementRef(name = "_Address2", type = Address2.class),
    @XmlElementRef(name = "_Address3", type = Address3.class)
    })
    private List<IAddress> iAddressListRef;

    ... iAddressListRef.add(new Address("AAAA"))
```
    
XML:
```xml
  <_Address1>
    <address>AAAAA</address>
  </_Address1>
```

### @XmlJavaTypeAdapter
Use an adapter that implements XmlAdapter for custom marshaling.
* field
* type
* package, from within XmlJavaTypeAdapters

Java:
```java
  @XmlJavaTypeAdapter(MyTestBeanValueAdapter.class)
  private MyCustomBean value;
```

```java
public class MyTestBeanValueAdapter extends XmlAdapter<MyCustomBeanType, MyCustomBean> {

  @Override
  public MyCustomBean unmarshal(MyCustomBeanType v) throws Exception {
    return new MyCustomBean(v);
  }

  @Override
  public MyCustomBeanType marshal(MyCustomBean v) throws Exception {
    return new MyCustomBeanType(v);
  }
}
```

### @XmlValue
Enables mapping a class to a XML Schema complex type with a simpleContent or a XML Schema simple type.

Java:
```java
public class User {
         @XmlValue
         public String name;
     }
```
XML:
```xml
<?xml version='1.0' encoding='UTF-8'?><User>testName</User>
```


### @XmlEnumValue
Maps an enum constant in Enum type to XML representation.

Java:
```java
  public enum Enums {
    @XmlEnumValue("1")
    ONE,
    TWO,
    @XmlEnumValue("_three")
    THREE,
    FOUR
  }
```
XML:
```xml
<?xml version='1.0' encoding='UTF-8'?><EnumBean><val>_three</val></EnumBean>
```
