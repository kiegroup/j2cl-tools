/*
 * Copyright 2013 Nicolas Morel
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

package org.kie.j2cl.tools.xml.mapper.api.utils;

import elemental2.core.JsDate;
import java.util.Date;
import org.kie.j2cl.tools.xml.mapper.api.MapperContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;

/**
 * JsDateFormat class.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class JsDateFormat implements MapperContext.DateFormat {

  @Override
  public String format(Date date) {
    return format(null, date);
  }

  @Override
  public String format(XMLSerializerParameters params, Date date) {
    JsDate jsDate = new JsDate();
    double milliseconds = date.getTime();
    jsDate.setTime(milliseconds);
    return jsDate.toISOString();
  }

  @Override
  public Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date) {
    double value = JsDate.parse(date);
    return new Date((long) value);
  }
}
