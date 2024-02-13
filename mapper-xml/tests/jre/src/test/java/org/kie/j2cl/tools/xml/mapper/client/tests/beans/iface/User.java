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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.iface;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.List;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class User implements IUser {

  private String user;

  @XmlElements({
    @XmlElement(name = "_Address1", type = Address.class),
    @XmlElement(name = "_Address2", type = Address2.class),
    @XmlElement(name = "_Address3", type = Address3.class)
  })
  private IAddress iAddress;

  @XmlElements({
    @XmlElement(name = "_Address1", type = Address.class),
    @XmlElement(name = "_Address2", type = Address2.class),
    @XmlElement(name = "_Address3", type = Address3.class)
  })
  private List<IAddress> iAddressList;

  @XmlElementRefs({
    @XmlElementRef(name = "iAddressListRef_Address1", type = Address.class),
    @XmlElementRef(name = "iAddressListRef_Address2", type = Address2.class),
    @XmlElementRef(name = "iAddressListRef_Address3", type = Address3.class)
  })
  private List<IAddress> iAddressListRef;

  @XmlElementRefs({
    @XmlElementRef(name = "iAddressListRef2_Address1", type = Address.class),
    @XmlElementRef(name = "iAddressListRef2_Address2", type = Address2.class),
    @XmlElementRef(name = "iAddressListRef2_Address3", type = Address3.class)
  })
  private List<IAddress> iAddressListRef2;

  @XmlElementRefs({
    @XmlElementRef(name = "iAddressListRef3_Address1", type = Address.class),
    @XmlElementRef(name = "iAddressListRef3_Address2", type = Address2.class),
    @XmlElementRef(name = "iAddressListRef3_Address3", type = Address3.class)
  })
  @XmlElement(name = "wrapped", namespace = "namespace")
  private List<IAddress> iAddressListRef3;

  @XmlElementRefs({
    @XmlElementRef(name = "iAddressRef_Address1", type = Address.class),
    @XmlElementRef(name = "iAddressRef_Address2", type = Address2.class),
    @XmlElementRef(name = "iAddressRef_Address3", type = Address3.class)
  })
  private IAddress iAddressRef;

  @XmlElementRefs({@XmlElementRef(name = "iAddressOneElm_Address3", type = Address3.class)})
  private List<IAddress> iAddressOneElm;

  @XmlElements({@XmlElement(name = "_Address3", type = Address3.class)})
  private List<IAddress> iAddress2OneElm;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;

    User user1 = (User) o;

    if (getUser() != null ? !getUser().equals(user1.getUser()) : user1.getUser() != null)
      return false;
    if (iAddress != null ? !iAddress.equals(user1.iAddress) : user1.iAddress != null) return false;
    if (iAddressList != null
        ? !iAddressList.equals(user1.iAddressList)
        : user1.iAddressList != null) return false;
    if (iAddressListRef != null
        ? !iAddressListRef.equals(user1.iAddressListRef)
        : user1.iAddressListRef != null) return false;
    if (iAddressListRef2 != null
        ? !iAddressListRef2.equals(user1.iAddressListRef2)
        : user1.iAddressListRef2 != null) return false;
    if (getIAddressListRef3() != null
        ? !getIAddressListRef3().equals(user1.getIAddressListRef3())
        : user1.getIAddressListRef3() != null) return false;
    if (iAddressRef != null ? !iAddressRef.equals(user1.iAddressRef) : user1.iAddressRef != null)
      return false;
    if (iAddressOneElm != null
        ? !iAddressOneElm.equals(user1.iAddressOneElm)
        : user1.iAddressOneElm != null) return false;
    return iAddress2OneElm != null
        ? iAddress2OneElm.equals(user1.iAddress2OneElm)
        : user1.iAddress2OneElm == null;
  }

  @Override
  public int hashCode() {
    int result = getUser() != null ? getUser().hashCode() : 0;
    result = 31 * result + (iAddress != null ? iAddress.hashCode() : 0);
    result = 31 * result + (iAddressList != null ? iAddressList.hashCode() : 0);
    result = 31 * result + (iAddressListRef != null ? iAddressListRef.hashCode() : 0);
    result = 31 * result + (iAddressListRef2 != null ? iAddressListRef2.hashCode() : 0);
    result = 31 * result + (iAddressListRef3 != null ? iAddressListRef3.hashCode() : 0);
    result = 31 * result + (iAddressRef != null ? iAddressRef.hashCode() : 0);
    result = 31 * result + (iAddressOneElm != null ? iAddressOneElm.hashCode() : 0);
    result = 31 * result + (iAddress2OneElm != null ? iAddress2OneElm.hashCode() : 0);
    return result;
  }

  public IAddress getIAddressRef() {
    return iAddressRef;
  }

  public void setIAddressRef(IAddress iAddressRef) {
    this.iAddressRef = iAddressRef;
  }

  public List<IAddress> getIAddressList() {
    return iAddressList;
  }

  public void setIAddressList(List<IAddress> iAddressList) {
    this.iAddressList = iAddressList;
  }

  public List<IAddress> getIAddressListRef() {
    return iAddressListRef;
  }

  public void setIAddressListRef(List<IAddress> iAddressListRef) {
    this.iAddressListRef = iAddressListRef;
  }

  public List<IAddress> getIAddressOneElm() {
    return iAddressOneElm;
  }

  public void setIAddressOneElm(List<IAddress> iAddressOneElm) {
    this.iAddressOneElm = iAddressOneElm;
  }

  public List<IAddress> getIAddress2OneElm() {
    return iAddress2OneElm;
  }

  public void setIAddress2OneElm(List<IAddress> iAddress2OneElm) {
    this.iAddress2OneElm = iAddress2OneElm;
  }

  @Override
  public String getUser() {
    return user;
  }

  @Override
  public void setUser(String name) {
    this.user = name;
  }

  @Override
  public IAddress getIAddress() {
    return iAddress;
  }

  @Override
  public void setIAddress(IAddress address) {
    this.iAddress = address;
  }

  public List<IAddress> getIAddressListRef2() {
    return iAddressListRef2;
  }

  public void setIAddressListRef2(List<IAddress> iAddressListRef2) {
    this.iAddressListRef2 = iAddressListRef2;
  }

  public List<IAddress> getIAddressListRef3() {
    return iAddressListRef3;
  }

  public void setIAddressListRef3(List<IAddress> iAddressListRef3) {
    this.iAddressListRef3 = iAddressListRef3;
  }
}
