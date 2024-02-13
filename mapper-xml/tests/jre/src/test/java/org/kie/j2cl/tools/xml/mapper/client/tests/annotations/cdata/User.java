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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.cdata;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlCData;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.UUID;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
@XmlRootElement(namespace = "http://www.omg.org/bpmn20")
public class User {

  @XmlCData private String username;
  @XmlAttribute private String id;

  @XmlAttribute(name = "_uuid")
  private UUID uuid;

  @XmlAttribute private long time;

  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getId(), getUuid(), getTime());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return getTime() == user.getTime()
        && Objects.equals(getUsername(), user.getUsername())
        && Objects.equals(getId(), user.getId())
        && Objects.equals(getUuid(), user.getUuid());
  }

  public long getTime() {
    return time;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "User{"
        + "username='"
        + username
        + '\''
        + ", id='"
        + id
        + '\''
        + ", uuid="
        + uuid
        + ", time="
        + time
        + '}';
  }
}
