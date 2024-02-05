/*
 * Copyright 2017 Ahmad Bawaneh
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
package org.kie.j2cl.tools.xml.mapper.apt;

import static java.util.Objects.nonNull;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseDateXMLDeserializer.DateXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseDateXMLDeserializer.SqlDateXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseDateXMLDeserializer.SqlTimeXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseDateXMLDeserializer.SqlTimestampXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.BigDecimalXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.BigIntegerXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.ByteXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.DoubleXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.FloatXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.IntegerXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.LongXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.NumberXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer.ShortXMLDeserializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseDateXMLSerializer.DateXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseDateXMLSerializer.SqlDateXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseDateXMLSerializer.SqlTimeXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseDateXMLSerializer.SqlTimestampXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.BigDecimalXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.BigIntegerXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.ByteXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.DoubleXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.FloatXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.IntegerXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.LongXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.NumberXMLSerializer;
import static org.kie.j2cl.tools.xml.mapper.api.ser.BaseNumberXMLSerializer.ShortXMLSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.kie.j2cl.tools.xml.mapper.api.deser.BooleanXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.CharacterXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.EnumXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.StringArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.StringXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.UUIDXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.VoidXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveBooleanArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveByteArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveCharacterArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveDoubleArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveFloatArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveIntegerArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveLongArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.PrimitiveShortArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.Array2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveBooleanArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveByteArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveCharacterArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveDoubleArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveFloatArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveIntegerArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveLongArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.PrimitiveShortArray2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.AbstractCollectionXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.AbstractListXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.AbstractQueueXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.AbstractSequentialListXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.AbstractSetXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.ArrayListXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.CollectionXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.EnumSetXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.HashSetXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.IterableXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.LinkedHashSetXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.LinkedListXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.ListXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.PriorityQueueXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.QueueXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.SetXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.SortedSetXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.StackXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.TreeSetXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.collection.VectorXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.AbstractMapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.EnumMapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.HashMapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.IdentityHashMapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.LinkedHashMapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.MapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.SortedMapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.TreeMapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.BooleanXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.CharacterXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.CollectionXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.EnumXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.IterableXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.StringXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.UUIDXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.VoidXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.ArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveBooleanArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveByteArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveCharacterArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveDoubleArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveFloatArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveIntegerArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveLongArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.PrimitiveShortArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.Array2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveBooleanArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveByteArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveCharacterArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveDoubleArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveFloatArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveIntegerArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveLongArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.PrimitiveShortArray2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.map.MapXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;

/**
 * TypeRegistry class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public final class TypeRegistry {

  private Map<String, ClassMapper> simpleTypes = new HashMap<>();
  private Map<String, ClassMapper> basicTypes = new HashMap<>();
  private Map<String, Class> collectionsDeserializers = new HashMap<>();
  private Map<String, Class> mapDeserializers = new HashMap<>();
  private Map<String, ClassMapper> customMappers = new HashMap<>();
  private Set<String> inActiveGenSerializers = new HashSet<>();
  private Set<String> inActiveGenDeserializers = new HashSet<>();
  private final ClassMapperFactory MAPPER = new ClassMapperFactory();
  private final Types types;
  private final Elements elements;
  private final GenerationContext context;

  public TypeRegistry(GenerationContext context) {
    this.types = context.getProcessingEnv().getTypeUtils();
    this.elements = context.getProcessingEnv().getElementUtils();
    this.context = context;

    initBasicMappers();
    initCommonMappers();
    initNumberMappers();
    initDataMappers();
    initIterableMappers();
    initMapMappers();
    initPrimitiveArraysMappers();
    initPrimitive2DArraysMappers();
    initCollectionsDeserializersMappers();
    initMapsDeserializersMappers();
  }

  private void initBasicMappers() {
    MAPPER
        .forType(boolean.class)
        .serializer(BooleanXMLSerializer.class)
        .deserializer(BooleanXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(char.class)
        .serializer(CharacterXMLSerializer.class)
        .deserializer(CharacterXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(byte.class)
        .serializer(ByteXMLSerializer.class)
        .deserializer(ByteXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(double.class)
        .serializer(DoubleXMLSerializer.class)
        .deserializer(DoubleXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(float.class)
        .serializer(FloatXMLSerializer.class)
        .deserializer(FloatXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(int.class)
        .serializer(IntegerXMLSerializer.class)
        .deserializer(IntegerXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(long.class)
        .serializer(LongXMLSerializer.class)
        .deserializer(LongXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(short.class)
        .serializer(ShortXMLSerializer.class)
        .deserializer(ShortXMLDeserializer.class)
        .register(basicTypes);
  }

  private void initCommonMappers() {
    // Common mappers
    MAPPER
        .forType(String.class)
        .serializer(StringXMLSerializer.class)
        .deserializer(StringXMLDeserializer.class)
        .register(basicTypes);
    MAPPER
        .forType(Boolean.class)
        .serializer(BooleanXMLSerializer.class)
        .deserializer(BooleanXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Character.class)
        .serializer(CharacterXMLSerializer.class)
        .deserializer(CharacterXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(UUID.class)
        .serializer(UUIDXMLSerializer.class)
        .deserializer(UUIDXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Void.class)
        .serializer(VoidXMLSerializer.class)
        .deserializer(VoidXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(Enum.class)
        .serializer(EnumXMLSerializer.class)
        .deserializer(EnumXMLDeserializer.class)
        .register(basicTypes);
  }

  private void initNumberMappers() {
    MAPPER
        .forType(BigDecimal.class)
        .serializer(BigDecimalXMLSerializer.class)
        .deserializer(BigDecimalXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(BigInteger.class)
        .serializer(BigIntegerXMLSerializer.class)
        .deserializer(BigIntegerXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Byte.class)
        .serializer(ByteXMLSerializer.class)
        .deserializer(ByteXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Double.class)
        .serializer(DoubleXMLSerializer.class)
        .deserializer(DoubleXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Float.class)
        .serializer(FloatXMLSerializer.class)
        .deserializer(FloatXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Integer.class)
        .serializer(IntegerXMLSerializer.class)
        .deserializer(IntegerXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Long.class)
        .serializer(LongXMLSerializer.class)
        .deserializer(LongXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Short.class)
        .serializer(ShortXMLSerializer.class)
        .deserializer(ShortXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Number.class)
        .serializer(NumberXMLSerializer.class)
        .deserializer(NumberXMLDeserializer.class)
        .register(basicTypes);
  }

  private void initDataMappers() {
    MAPPER
        .forType(Date.class)
        .serializer(DateXMLSerializer.class)
        .deserializer(DateXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(java.sql.Date.class)
        .serializer(SqlDateXMLSerializer.class)
        .deserializer(SqlDateXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Time.class)
        .serializer(SqlTimeXMLSerializer.class)
        .deserializer(SqlTimeXMLDeserializer.class)
        .register(basicTypes);

    MAPPER
        .forType(Timestamp.class)
        .serializer(SqlTimestampXMLSerializer.class)
        .deserializer(SqlTimestampXMLDeserializer.class)
        .register(basicTypes);
  }

  private void initIterableMappers() {
    MAPPER
        .forType(Iterable.class)
        .serializer(IterableXMLSerializer.class)
        .deserializer(IterableXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(Collection.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(CollectionXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(AbstractCollection.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(AbstractCollectionXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(AbstractList.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(AbstractListXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(AbstractQueue.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(AbstractQueueXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(AbstractSequentialList.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(AbstractSequentialListXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(AbstractSet.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(AbstractSetXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(ArrayList.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(ArrayListXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(EnumSet.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(EnumSetXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(HashSet.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(HashSetXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(LinkedHashSet.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(LinkedHashSetXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(LinkedList.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(LinkedListXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(List.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(ListXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(PriorityQueue.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(PriorityQueueXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(Queue.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(QueueXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(Set.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(SetXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(SortedSet.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(SortedSetXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(Stack.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(StackXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(TreeSet.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(TreeSetXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(Vector.class)
        .serializer(CollectionXMLSerializer.class)
        .deserializer(VectorXMLDeserializer.class)
        .register(simpleTypes);
  }

  private void initMapMappers() {
    MAPPER
        .forType(Map.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(MapXMLDeserializer.class)
        .register(simpleTypes);
    MAPPER
        .forType(AbstractMap.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(AbstractMapXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(EnumMap.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(EnumMapXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(HashMap.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(HashMapXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(IdentityHashMap.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(IdentityHashMapXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(LinkedHashMap.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(LinkedHashMapXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(SortedMap.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(SortedMapXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(TreeMap.class)
        .serializer(MapXMLSerializer.class)
        .deserializer(TreeMapXMLDeserializer.class)
        .register(simpleTypes);
  }

  private void initPrimitiveArraysMappers() {
    MAPPER
        .forType(boolean[].class)
        .serializer(PrimitiveBooleanArrayXMLSerializer.class)
        .deserializer(PrimitiveBooleanArrayXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(byte[].class)
        .serializer(PrimitiveByteArrayXMLSerializer.class)
        .deserializer(PrimitiveByteArrayXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(char[].class)
        .serializer(PrimitiveCharacterArrayXMLSerializer.class)
        .deserializer(PrimitiveCharacterArrayXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(double[].class)
        .serializer(PrimitiveDoubleArrayXMLSerializer.class)
        .deserializer(PrimitiveDoubleArrayXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(float[].class)
        .serializer(PrimitiveFloatArrayXMLSerializer.class)
        .deserializer(PrimitiveFloatArrayXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(int[].class)
        .serializer(PrimitiveIntegerArrayXMLSerializer.class)
        .deserializer(PrimitiveIntegerArrayXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(long[].class)
        .serializer(PrimitiveLongArrayXMLSerializer.class)
        .deserializer(PrimitiveLongArrayXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(short[].class)
        .serializer(PrimitiveShortArrayXMLSerializer.class)
        .deserializer(PrimitiveShortArrayXMLDeserializer.class)
        .register(simpleTypes);
  }

  private void initPrimitive2DArraysMappers() {
    MAPPER
        .forType(boolean[][].class)
        .serializer(PrimitiveBooleanArray2dXMLSerializer.class)
        .deserializer(PrimitiveBooleanArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(byte[][].class)
        .serializer(PrimitiveByteArray2dXMLSerializer.class)
        .deserializer(PrimitiveByteArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(char[][].class)
        .serializer(PrimitiveCharacterArray2dXMLSerializer.class)
        .deserializer(PrimitiveCharacterArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(double[][].class)
        .serializer(PrimitiveDoubleArray2dXMLSerializer.class)
        .deserializer(PrimitiveDoubleArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(float[][].class)
        .serializer(PrimitiveFloatArray2dXMLSerializer.class)
        .deserializer(PrimitiveFloatArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(int[][].class)
        .serializer(PrimitiveIntegerArray2dXMLSerializer.class)
        .deserializer(PrimitiveIntegerArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(long[][].class)
        .serializer(PrimitiveLongArray2dXMLSerializer.class)
        .deserializer(PrimitiveLongArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(short[][].class)
        .serializer(PrimitiveShortArray2dXMLSerializer.class)
        .deserializer(PrimitiveShortArray2dXMLDeserializer.class)
        .register(simpleTypes);

    MAPPER
        .forType(String[].class)
        .serializer(ArrayXMLSerializer.class)
        .deserializer(StringArrayXMLDeserializer.class)
        .register(simpleTypes);
    MAPPER
        .forType(String[][].class)
        .serializer(Array2dXMLSerializer.class)
        .deserializer(Array2dXMLDeserializer.class)
        .register(simpleTypes);
  }

  private void initCollectionsDeserializersMappers() {
    collectionsDeserializers.put(
        AbstractCollection.class.getCanonicalName(), AbstractCollectionXMLDeserializer.class);
    collectionsDeserializers.put(
        AbstractList.class.getCanonicalName(), AbstractListXMLDeserializer.class);
    collectionsDeserializers.put(
        AbstractQueue.class.getCanonicalName(), AbstractQueueXMLDeserializer.class);
    collectionsDeserializers.put(
        AbstractSequentialList.class.getCanonicalName(),
        AbstractSequentialListXMLDeserializer.class);
    collectionsDeserializers.put(
        AbstractSet.class.getCanonicalName(), AbstractSetXMLDeserializer.class);
    collectionsDeserializers.put(
        ArrayList.class.getCanonicalName(), ArrayListXMLDeserializer.class);
    collectionsDeserializers.put(
        Collection.class.getCanonicalName(), CollectionXMLDeserializer.class);
    collectionsDeserializers.put(EnumSet.class.getCanonicalName(), EnumSetXMLDeserializer.class);
    collectionsDeserializers.put(HashSet.class.getCanonicalName(), HashSetXMLDeserializer.class);
    collectionsDeserializers.put(Iterable.class.getCanonicalName(), IterableXMLDeserializer.class);
    collectionsDeserializers.put(
        LinkedHashSet.class.getCanonicalName(), LinkedHashSetXMLDeserializer.class);
    collectionsDeserializers.put(
        LinkedList.class.getCanonicalName(), LinkedListXMLDeserializer.class);
    collectionsDeserializers.put(List.class.getCanonicalName(), ListXMLDeserializer.class);
    collectionsDeserializers.put(
        PriorityQueue.class.getCanonicalName(), PriorityQueueXMLDeserializer.class);
    collectionsDeserializers.put(Queue.class.getCanonicalName(), QueueXMLDeserializer.class);
    collectionsDeserializers.put(Set.class.getCanonicalName(), SetXMLDeserializer.class);
    collectionsDeserializers.put(
        SortedSet.class.getCanonicalName(), SortedSetXMLDeserializer.class);
    collectionsDeserializers.put(Stack.class.getCanonicalName(), StackXMLDeserializer.class);
    collectionsDeserializers.put(TreeSet.class.getCanonicalName(), TreeSetXMLDeserializer.class);
    collectionsDeserializers.put(Vector.class.getCanonicalName(), VectorXMLDeserializer.class);
  }

  private void initMapsDeserializersMappers() {
    mapDeserializers.put(Map.class.getName(), MapXMLDeserializer.class);
    mapDeserializers.put(AbstractMap.class.getName(), AbstractMapXMLDeserializer.class);
    mapDeserializers.put(EnumMap.class.getName(), EnumMapXMLDeserializer.class);
    mapDeserializers.put(HashMap.class.getName(), HashMapXMLDeserializer.class);
    mapDeserializers.put(IdentityHashMap.class.getName(), IdentityHashMapXMLDeserializer.class);
    mapDeserializers.put(LinkedHashMap.class.getName(), LinkedHashMapXMLDeserializer.class);
    mapDeserializers.put(SortedMap.class.getName(), SortedMapXMLDeserializer.class);
    mapDeserializers.put(TreeMap.class.getName(), TreeMapXMLDeserializer.class);
  }

  public void addInActiveGenSerializer(TypeMirror typeMirror) {
    inActiveGenSerializers.add(context.getTypeUtils().stringifyTypeWithPackage(typeMirror));
  }

  public void addInActiveGenDeserializer(TypeMirror typeMirror) {
    inActiveGenDeserializers.add(context.getTypeUtils().stringifyTypeWithPackage(typeMirror));
  }

  public void removeInActiveGenSerializer(TypeMirror typeMirror) {
    inActiveGenSerializers.remove(context.getTypeUtils().stringifyTypeWithPackage(typeMirror));
  }

  public void removeInActiveGenDeserializer(TypeMirror typeMirror) {
    inActiveGenDeserializers.remove(context.getTypeUtils().stringifyTypeWithPackage(typeMirror));
  }

  public boolean isInActiveGenSerializer(TypeMirror typeMirror) {
    return inActiveGenSerializers.contains(
        context.getTypeUtils().stringifyTypeWithPackage(typeMirror));
  }

  public boolean isInActiveGenDeserializer(TypeMirror typeMirror) {
    return inActiveGenDeserializers.contains(
        context.getTypeUtils().stringifyTypeWithPackage(typeMirror));
  }

  /**
   * resetTypeRegistry
   *
   * <p>Helper method to clean (reset) state of TypeRegistry. This action should be performed on
   * every APT run, since in some environments (such as Eclipse), the processor is instantiated once
   * and used multiple times. Without some cleanup we may end up with some serializer/deserializers
   * not generated due to TypeRegistry internal state saying that they already exists.
   */
  public void resetTypeRegistry() {
    customMappers.clear();
  }

  /**
   * register.
   *
   * @param mapper a {@link TypeRegistry.ClassMapper} object.
   */
  public void register(ClassMapper mapper) {
    mapper.register(simpleTypes);
  }

  /**
   * isBasicType.
   *
   * @param type a {@link String} object.
   * @return a boolean.
   */
  public boolean isBasicType(String type) {
    return basicTypes.containsKey(type);
  }

  /**
   * registerSerializer.
   *
   * @param type a {@link String} object.
   * @param serializer a {@link TypeElement} object.
   */
  public void registerSerializer(String type, TypeElement serializer) {
    if (customMappers.containsKey(type)) {
      customMappers.get(type).serializer = serializer;
    } else {
      ClassMapper classMapper = new ClassMapper(type);
      classMapper.serializer = serializer;
      customMappers.put(type, classMapper);
    }
  }

  /**
   * registerDeserializer.
   *
   * @param type a {@link String} object.
   * @param deserializer a {@link TypeElement} object.
   */
  public void registerDeserializer(String type, TypeElement deserializer) {
    if (customMappers.containsKey(type)) {
      customMappers.get(type).deserializer = deserializer;
    } else {
      ClassMapper classMapper = new ClassMapper(type);
      classMapper.deserializer = deserializer;
      customMappers.put(type, classMapper);
    }
  }

  /**
   * getCustomSerializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link TypeElement} object.
   */
  public TypeElement getCustomSerializer(TypeMirror typeMirror) {
    return getCustomSerializer(typeMirror.toString());
  }

  /**
   * getCustomSerializer.
   *
   * @param type a {@link String} object.
   * @return a {@link TypeElement} object.
   */
  public TypeElement getCustomSerializer(String type) {
    if (containsSerializer(type)) {
      return customMappers.get(type).serializer;
    }
    throw new TypeSerializerNotFoundException(type);
  }

  /**
   * containsSerializer.
   *
   * @param typeName a {@link String} object.
   * @return a boolean.
   */
  public boolean containsSerializer(String typeName) {
    return nonNull(customMappers.get(typeName)) && nonNull(customMappers.get(typeName).serializer);
  }

  /**
   * getCustomDeserializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link TypeElement} object.
   */
  public TypeElement getCustomDeserializer(TypeMirror typeMirror) {
    return getCustomDeserializer(typeMirror.toString());
  }

  /**
   * getCustomDeserializer.
   *
   * @param type a {@link String} object.
   * @return a {@link TypeElement} object.
   */
  public TypeElement getCustomDeserializer(String type) {
    if (containsDeserializer(type)) {
      return customMappers.get(type).deserializer;
    }
    throw new TypeDeserializerNotFoundException(type);
  }

  /**
   * containsDeserializer.
   *
   * @param typeName a {@link String} object.
   * @return a boolean.
   */
  public boolean containsDeserializer(String typeName) {
    return nonNull(customMappers.get(typeName))
        && nonNull(customMappers.get(typeName).deserializer);
  }

  /**
   * getSerializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link TypeElement} object.
   */
  public TypeElement getSerializer(TypeMirror typeMirror) {
    return getSerializer(typeMirror.toString());
  }

  public TypeElement getSerializer(String typeName) {
    if (basicTypes.containsKey(typeName) || simpleTypes.containsKey(typeName)) {
      return get(typeName).serializer;
    }
    throw new TypeSerializerNotFoundException(typeName);
  }

  /**
   * get.
   *
   * @param typeName a {@link String} object.
   * @return a {@link TypeRegistry.ClassMapper} object.
   */
  public ClassMapper get(String typeName) {
    if (isSimpleType(typeName)) {
      return simpleTypes.get(typeName);
    }
    return basicTypes.get(typeName);
  }

  public boolean isSimpleType(String type) {
    return simpleTypes.containsKey(type);
  }

  /**
   * getDeserializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link TypeElement} object.
   */
  public TypeElement getDeserializer(TypeMirror typeMirror) {
    return getDeserializer(typeMirror.toString());
  }

  public TypeElement getDeserializer(String typeName) {
    if (basicTypes.containsKey(typeName) || simpleTypes.containsKey(typeName)) {
      return get(typeName).deserializer;
    }
    throw new TypeDeserializerNotFoundException(typeName);
  }

  /**
   * getCollectionDeserializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link Class} object.
   */
  public Class<?> getCollectionDeserializer(TypeMirror typeMirror) {
    return getCollectionDeserializer(asNoneGeneric(typeMirror));
  }

  private Class<?> getCollectionDeserializer(String collectionType) {
    if (collectionsDeserializers.containsKey(collectionType)) {
      return collectionsDeserializers.get(collectionType);
    }
    throw new TypeDeserializerNotFoundException(collectionType);
  }

  private String asNoneGeneric(TypeMirror type) {
    return types.asElement(type).toString().replace("<E>", "");
  }

  /**
   * getMapDeserializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a {@link Class} object.
   */
  public Class<?> getMapDeserializer(TypeMirror typeMirror) {
    return getMapDeserializer(asNoneGeneric(typeMirror));
  }

  private Class<?> getMapDeserializer(String mapType) {
    if (mapDeserializers.containsKey(mapType)) {
      return mapDeserializers.get(mapType);
    }
    throw new TypeDeserializerNotFoundException(mapType);
  }

  /**
   * containsSerializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public boolean containsSerializer(TypeMirror typeMirror) {
    return containsSerializer(typeMirror.toString());
  }

  /**
   * containsDeserializer.
   *
   * @param typeMirror a {@link TypeMirror} object.
   * @return a boolean.
   */
  public boolean containsDeserializer(TypeMirror typeMirror) {
    return containsDeserializer(typeMirror.toString());
  }

  private static class TypeSerializerNotFoundException extends RuntimeException {

    TypeSerializerNotFoundException(String typeName) {
      super(typeName);
    }
  }

  private static class TypeDeserializerNotFoundException extends RuntimeException {

    TypeDeserializerNotFoundException(String typeName) {
      super(typeName);
    }
  }

  class ClassMapperFactory {

    ClassMapper forType(Class<?> clazz) {
      return new ClassMapper(clazz);
    }
  }

  public class ClassMapper {

    private final String clazz;

    private TypeElement serializer;

    private TypeElement deserializer;

    private ClassMapper(Class clazz) {
      this.clazz = clazz.getCanonicalName();
    }

    private ClassMapper(String type) {
      this.clazz = type;
    }

    private ClassMapper serializer(Class serializer) {
      this.serializer = elements.getTypeElement(serializer.getCanonicalName());
      return this;
    }

    private ClassMapper deserializer(Class deserializer) {
      this.deserializer = elements.getTypeElement(deserializer.getCanonicalName());
      return this;
    }

    private ClassMapper register(Map<String, ClassMapper> registry) {
      registry.put(this.clazz, this);
      return this;
    }

    @Override
    public String toString() {
      return "ClassMapper{" + "clazz='" + clazz + '\'' + ", serializer=" + serializer != null
          ? serializer.toString()
          : "" + ", deserializer=" + deserializer != null ? deserializer.toString() : "" + '}';
    }
  }
}
