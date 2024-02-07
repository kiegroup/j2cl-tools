package org.kie.j2cl.tools.processors.tests.shim;

import jsinterop.annotations.JsType;
import org.kie.j2cl.tools.processors.annotations.ES6Module;

@ES6Module("../../../../../../js/ES6TestPath.js")
@JsType(isNative = true, namespace = "org.kie.j2cl.tools.processors.tests.shim")
public class ES6TestPath {

  public String id;

  public native boolean isES6TestPath();
}
