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
package org.kie.j2cl.tools.xml.mapper.api.stream.impl;

import elemental2.dom.Attr;
import elemental2.dom.CDATASection;
import elemental2.dom.Document;
import elemental2.dom.Node;
import elemental2.dom.Text;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

public class JsNativeXMLReader implements XMLReader {

  private Document doc;

  private Iterator<NodeWrapper> iterator;
  private NodeWrapper current;

  protected JsNativeXMLReader() {}

  public JsNativeXMLReader(String input) {
    doc = new DOMParser().parseFromString(input, "text/xml");
    removeWhitespace(doc, null);
    List<NodeWrapper> nodes = new LinkedList<>();
    visit(doc, nodes);
    iterator = nodes.iterator();
    current = iterator.next();
  }

  @Override
  public boolean hasNext() throws XMLStreamException {
    return iterator.hasNext();
  }

  @Override
  public int peek() throws XMLStreamException {
    return current.type;
  }

  @Override
  public QName peekNodeName() throws XMLStreamException {
    if (current.node.prefix != null && !current.node.prefix.isEmpty()) {
      String nodeName = current.node.nodeName.replace(current.node.prefix + ":", "");
      return new QName(current.node.namespaceURI, nodeName, current.node.prefix);
    }
    return new QName(current.node.namespaceURI, current.node.nodeName);
  }

  @Override
  public String rowValue() throws XMLStreamException {
    return ((Text) current.node).data;
  }

  @Override
  public String nextString() throws XMLStreamException {
    if (current.type == XMLStreamConstants.START_ELEMENT) {
      next();
    }
    if (current.type == XMLStreamConstants.END_ELEMENT) {
      return null;
    }

    return ((Text) current.node).data;
  }

  @Override
  public boolean nextBoolean() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return false;
    }
    return Boolean.valueOf(value);
  }

  @Override
  public double nextDouble() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return 0;
    }
    return Double.valueOf(value);
  }

  @Override
  public long nextLong() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return 0;
    }
    return Long.valueOf(value);
  }

  @Override
  public int nextInt() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return 0;
    }
    return Integer.valueOf(value);
  }

  @Override
  public void close() throws XMLStreamException {}

  @Override
  public void skipValue() throws XMLStreamException {}

  @Override
  public String nextValue() throws XMLStreamException {
    if (current.type == XMLStreamConstants.END_ELEMENT) {
      return ((CDATASection) current.node).data;
    }
    return nextString();
  }

  @Override
  public Number nextNumber() throws XMLStreamException {
    return null;
  }

  @Override
  public void next() throws XMLStreamException {
    if (iterator.hasNext()) {
      current = iterator.next();
    }
  }

  @Override
  public String getInput() throws XMLStreamException {
    return doc.documentElement.toString();
  }

  @Override
  public int getAttributeCount() {
    return current.node.attributes.getLength();
  }

  @Override
  public QName getAttributeName(int index) {
    Attr attr = (Attr) current.node.attributes.item(index);
    return new QName(attr.namespaceURI, attr.name.replaceAll("xsi:", ""));
  }

  @Override
  public String getAttributeValue(int index) {
    return ((Attr) current.node.attributes.item(index)).value;
  }

  @Override
  public String getAttributeType(int index) {
    return null;
  }

  public void visit(Node node, List<NodeWrapper> nodes) {
    nodes.add(new NodeWrapper(node, toNodeType(node.nodeType)));
    if (node.hasChildNodes()) {
      for (int i = 0; i < node.childNodes.getLength(); i++) {
        visit(node.childNodes.item(i), nodes);
      }
    }
    if (node.nodeType == 1) {
      nodes.add(new NodeWrapper(node, XMLStreamConstants.END_ELEMENT));
    }
  }

  public int toNodeType(int nativeType) {
    if (nativeType == 1) {
      return XMLStreamConstants.START_ELEMENT;
    }

    if (nativeType == 2) {
      return XMLStreamConstants.ATTRIBUTE;
    }

    if (nativeType == 3) {
      return XMLStreamConstants.CHARACTERS;
    }

    if (nativeType == 4) {
      return XMLStreamConstants.CDATA;
    }

    if (nativeType == 9) {
      return XMLStreamConstants.START_DOCUMENT;
    }
    throw new UnsupportedOperationException("type" + nativeType);
  }

  public void removeWhitespace(Node n, Node parent) {
    // This n is removed from the parent if n is a whitespace node
    if (parent != null && n instanceof Text && (!(n instanceof CDATASection))) {
      Text t = (Text) n;
      if (t.data.matches("[ \t\n]*")) {
        parent.removeChild(t);
      }
    }
    if (n.hasChildNodes()) {
      int length = n.childNodes.getLength();
      List<Node> toBeProcessed = new ArrayList<>();
      // We collect all the nodes to iterate as the child nodes will change
      // upon removal
      for (int i = 0; i < length; i++) {
        toBeProcessed.add(n.childNodes.item(i));
      }
      // This changes the child nodes, but the iterator of nodes never changes
      // meaning that this is safe
      for (Node childNode : toBeProcessed) {
        removeWhitespace(childNode, n);
      }
    }
  }

  private static class NodeWrapper {

    int type;
    Node node;

    NodeWrapper(Node node, int type) {
      this.node = node;
      this.type = type;
    }

    @Override
    public String toString() {
      return "NodeWrapper{" + "type=" + type + ", node=" + node + '}';
    }
  }

  @JsType(isNative = true, name = "DOMParser", namespace = JsPackage.GLOBAL)
  private static class DOMParser {

    public native Document parseFromString(String contents, String mimeType);
  }
}
