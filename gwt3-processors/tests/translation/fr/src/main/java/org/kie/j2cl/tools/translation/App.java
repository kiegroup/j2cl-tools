package org.kie.j2cl.tools.translation;

import org.kie.j2cl.tools.processors.annotations.GWT3EntryPoint;

public class App {

  static MyTranslationBundle bundle = new MyTranslationBundleImpl();

  @GWT3EntryPoint
  public void init() {}
}
