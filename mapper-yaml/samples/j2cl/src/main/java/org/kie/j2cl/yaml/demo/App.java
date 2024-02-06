package org.kie.j2cl.tools.yaml.demo;

import java.io.IOException;

import elemental2.dom.CSSProperties;
import elemental2.dom.DomGlobal;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLLabelElement;
import elemental2.dom.HTMLTextAreaElement;

public class App {

    Company_YamlMapperImpl mapper = Company_YamlMapperImpl.INSTANCE;
    private HTMLTextAreaElement generatedYAML = (HTMLTextAreaElement) DomGlobal.document.createElement("textarea");
    private HTMLDivElement generatedPOJO = (HTMLDivElement) DomGlobal.document.createElement("div");

    public void onModuleLoad() {
        HTMLLabelElement label1 = (HTMLLabelElement) DomGlobal.document.createElement("label");
        label1.textContent = "Pojo to YAML";
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
                } catch (IOException e) {
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
