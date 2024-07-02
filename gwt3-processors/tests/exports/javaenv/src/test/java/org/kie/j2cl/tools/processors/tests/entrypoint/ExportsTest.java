package org.kie.j2cl.tools.processors.tests.entrypoint;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import elemental2.core.Global;
import org.junit.Test;
import org.kie.j2cl.tools.processors.tests.exports.SimpleBean;
import org.kie.j2cl.tools.processors.tests.exports.SimpleBeanJsType;

@J2clTestInput(ExportsTest.class)
public class ExportsTest {

  @Test
  public void testSimpleBean() {
    SimpleBean object =
        (SimpleBean) Global.eval("new org.kie.j2cl.tools.processors.tests.exports.SimpleBean()");

    assertEquals("qwerty", object.getId());
    assertEquals("ExportTestClass", object.test());
    assertEquals("qwerty", SimpleBean.staticTest());

    object.setId("new_value");
    assertEquals("new_value", object.getId());
    assertEquals("SimpleBean", object.getClass().getSimpleName());
    assertEquals(
        "org.kie.j2cl.tools.processors.tests.exports.SimpleBean", object.getClass().getName());
    assertEquals(
        "org.kie.j2cl.tools.processors.tests.exports.SimpleBean",
        object.getClass().getCanonicalName());
  }

  @Test
  public void testSimpleBeanJsType() {
    SimpleBeanJsType object =
        (SimpleBeanJsType)
            Global.eval("new org.kie.j2cl.tools.processors.tests.exports.SimpleBeanJsType()");

    assertEquals("qwerty", object.getId());
    assertEquals("ExportTestClass", object.test());
    assertEquals("qwerty", SimpleBean.staticTest());

    object.setId("new_value");
    assertEquals("new_value", object.getId());
    assertEquals("SimpleBeanJsType", object.getClass().getSimpleName());
    assertEquals(
        "org.kie.j2cl.tools.processors.tests.exports.SimpleBeanJsType",
        object.getClass().getName());
    assertEquals(
        "org.kie.j2cl.tools.processors.tests.exports.SimpleBeanJsType",
        object.getClass().getCanonicalName());
  }
}
