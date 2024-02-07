package org.kie.j2cl.tools.processors.tests.shim;

import jsinterop.annotations.JsType;
import org.kie.j2cl.tools.processors.annotations.ES6Module;

@ES6Module
@JsType(isNative = true, namespace = "org.kie.j2cl.tools.processors.tests.shim", name = "ES6TestZZ")
public class ES6Test2 {

  public String id;

  public native boolean isTestZZ();
}
