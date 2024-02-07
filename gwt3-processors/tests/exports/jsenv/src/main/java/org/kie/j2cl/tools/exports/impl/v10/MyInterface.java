package org.kie.j2cl.tools.exports.impl.v10;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import org.kie.j2cl.tools.processors.annotations.GWT3Export;

@JsType
public class MyInterface implements org.kie.j2cl.tools.exports.apis.MyInterface {

  @JsMethod
  @GWT3Export
  public String test1(String s) {
    return s;
  }
  ;
}
