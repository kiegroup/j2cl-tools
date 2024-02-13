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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler;

import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlTypeAdapter;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler.marshaller.IdDemarshaller;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler.marshaller.IdMarshaller;

@XMLMapper
public class Bean {

  private MyBean myBean;

  private Id id;

  public MyBean getMyBean() {
    return myBean;
  }

  public void setMyBean(MyBean myBean) {
    this.myBean = myBean;
  }

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Bean)) {
      return false;
    }
    Bean bean = (Bean) o;
    return Objects.equals(getMyBean(), bean.getMyBean()) && Objects.equals(getId(), bean.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMyBean(), getId());
  }

  @XmlTypeAdapter(
      serializer = IdMarshaller.class,
      deserializer = IdDemarshaller.class,
      isAttribute = true)
  public static class Id {

    private String id;

    public Id() {}

    public Id(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler.Bean.Id)) {
        return false;
      }
      org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler.Bean.Id id1 =
          (org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler.Bean.Id) o;
      return Objects.equals(getId(), id1.getId());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getId());
    }
  }
}
