package org.kie.j2cl.tools.processors.tests.entrypoint;

import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import org.kie.j2cl.tools.processors.annotations.GWT3EntryPoint;

@JsType
public class App {

  @GWT3EntryPoint
  public void init() {
    Js.asPropertyMap(DomGlobal.window).set("started", true);
  }
}
