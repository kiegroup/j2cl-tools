/*
 * Copyright © 2024 j2cl-maven-plugin authors
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

package com.vertispan.j2cl.tools;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

public class J2CLModuleParser {

    private static final DocumentBuilder db;

    static {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            dbf.setValidating(false);
            dbf.setFeature("http://xml.org/sax/features/namespaces", false);
            dbf.setFeature("http://xml.org/sax/features/validation", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<Path> getSuperSourcePath(Collection<File> files) {
        for(File file : files) {
            Optional<Path> superSourcePath = getSuperSourcePath(file.toPath());
            if(superSourcePath.isPresent()) {
                return superSourcePath;
            }
        }
        return Optional.empty();
    }


    private static Optional<Path> getSuperSourcePath(Path p) {
        Path modulePath = p.resolve("META-INF").resolve("module.j2cl.xml");
        Document doc;
        try {
            doc = db.parse(modulePath.toFile());
        } catch (SAXException | IOException e) {
            return Optional.empty();
        }
        doc.getDocumentElement().normalize();
        NodeList modules = doc.getElementsByTagName("module");
        for (int i = 0; i < modules.getLength(); i++) {
            Node module = modules.item(i);
            for (int j = 0; j < module.getChildNodes().getLength(); j++) {
                Node node = module.getChildNodes().item(j);
                if(node.getNodeName().equals("super-source")) {
                    String path = node.getAttributes().getNamedItem("path").getNodeValue();
                    return Optional.of(Paths.get(path));
                }
            }
        }
        return Optional.empty();
    }
}
