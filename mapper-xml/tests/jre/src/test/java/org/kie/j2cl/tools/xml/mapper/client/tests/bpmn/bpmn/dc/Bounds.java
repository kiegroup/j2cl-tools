/*
 * Copyright © 2020 Treblereel
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
package org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn.dc;

import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

/** @author Dmitrii Tikhomirov Created by treblereel 4/6/20 */
public class Bounds {

  @XmlAttribute private double height;

  @XmlAttribute private double width;

  @XmlAttribute private double x;

  @XmlAttribute private double y;

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Bounds)) {
      return false;
    }
    Bounds bounds = (Bounds) o;
    return Double.compare(bounds.getHeight(), getHeight()) == 0
        && Double.compare(bounds.getWidth(), getWidth()) == 0
        && Double.compare(bounds.getX(), getX()) == 0
        && Double.compare(bounds.getY(), getY()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getHeight(), getWidth(), getX(), getY());
  }

  @Override
  public String toString() {
    return "Bounds{" + "height=" + height + ", width=" + width + ", x=" + x + ", y=" + y + '}';
  }
}
