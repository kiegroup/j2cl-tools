package org.kie.j2cl.tools.processors.tests.shim;

import jsinterop.annotations.JsType;
import org.kie.j2cl.tools.processors.annotations.ES6Module;

@ES6Module
@JsType(isNative = true, namespace = "org.kie.j2cl.tools.processors.tests.shim")
public class ES6TransitiveTest {

  private ES6Test es6Test;
  private ES6Test2 es6Test2;

  public native boolean getEs6Testid();

  public native boolean getEs6Test2id();
}
