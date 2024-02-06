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
package org.kie.j2cl.tools.yaml.mapper.client;

import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.CSSProperties;
import elemental2.dom.DomGlobal;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLLabelElement;
import elemental2.dom.HTMLTextAreaElement;

import java.io.IOException;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class App implements EntryPoint {

    public static final String HELLO_WORLD = "Hello J2CL world!";

    Company_YamlMapperImpl mapper = Company_YamlMapperImpl.INSTANCE;

    private HTMLTextAreaElement generatedYAML = (HTMLTextAreaElement) DomGlobal.document.createElement("textarea");

    private HTMLDivElement generatedPOJO = (HTMLDivElement) DomGlobal.document.createElement("div");

    @Override
    public void onModuleLoad() {
        HTMLLabelElement label1 = (HTMLLabelElement) DomGlobal.document.createElement("label");
        label1.textContent = "Pojo to XML";
        DomGlobal.document.body.appendChild(label1);
        DomGlobal.document.body.appendChild(DomGlobal.document.createElement("br"));

        DomGlobal.document.body.appendChild(generatedYAML);
        generatedYAML.classList.add("prettyprint", "lang-html");
        generatedYAML.style.height = CSSProperties.HeightUnionType.of("20pc");
        generatedYAML.style.width = CSSProperties.WidthUnionType.of("700px");
        generatedYAML.style.overflow = "scroll";

        DomGlobal.document.body.appendChild(DomGlobal.document.createElement("br"));

        HTMLButtonElement doDemarshalling = (HTMLButtonElement) DomGlobal.document.createElement("button");
        doDemarshalling.addEventListener("click", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                for (int i = 0; i < generatedPOJO.childNodes.getLength(); i++) {
                    generatedPOJO.removeChild(generatedPOJO.childNodes.item(i));
                }
                try {
                    Company result = mapper.read(generatedYAML.value);
                    generatedPOJO.innerHTML = result.toString();
                } catch (Exception e) {
                    DomGlobal.window.alert("Exception " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        doDemarshalling.textContent = "do demarshalling";
        DomGlobal.document.body.appendChild(doDemarshalling);
        DomGlobal.document.body.appendChild(DomGlobal.document.createElement("br"));

        DomGlobal.document.body.appendChild(DomGlobal.document.createElement("br"));

        HTMLLabelElement label2 = (HTMLLabelElement) DomGlobal.document.createElement("label");
        label2.textContent = "... and back to Pojo";
        DomGlobal.document.body.appendChild(label2);

        DomGlobal.document.body.appendChild(DomGlobal.document.createElement("br"));
        DomGlobal.document.body.appendChild(DomGlobal.document.createElement("br"));

        DomGlobal.document.body.appendChild(generatedPOJO);
        generatedPOJO.classList.add("prettyprint", "lang-html");
        generatedPOJO.style.height = CSSProperties.HeightUnionType.of("20pc");
        generatedPOJO.style.width = CSSProperties.WidthUnionType.of("1200px");
        generatedPOJO.style.overflow = "scroll";

        try {
            ser();
        } catch (Exception ex) {
            DomGlobal.console.log(ex);
        }
    }

    private void ser() throws IOException {
        Company tested = new Company();
        tested.setCompanyName("Red Hat");
        tested.setCeo("Paul Cormier");
        tested.setEmployees(13400);
        tested.setFounded(1993);
        tested.setIndustry("IT");


        double startTime = DomGlobal.window.performance.now();
        String xml = mapper.write(tested);
        double stopTime = DomGlobal.window.performance.now();
        DomGlobal.console.log("marhsalling " + (stopTime - startTime));

        generatedYAML.value = xml;

        startTime = DomGlobal.window.performance.now();
        generatedPOJO.innerHTML = mapper.read(xml).toString();
        stopTime = DomGlobal.window.performance.now();
        DomGlobal.console.log("demarhsalling " + (stopTime - startTime));
    }
}
