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

package org.kie.j2cl.tools.json.mapper.collections.set;

import java.util.Objects;
import java.util.SortedSet;
import org.kie.j2cl.tools.json.mapper.annotation.JSONMapper;
import org.kie.j2cl.tools.json.mapper.collections.User;

@JSONMapper
public class UsersSortedSet {

  private SortedSet<User> users;

  public SortedSet<User> getUsers() {
    return users;
  }

  public void setUsers(SortedSet<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UsersSortedSet that = (UsersSortedSet) o;
    return Objects.equals(users, that.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(users);
  }
}
