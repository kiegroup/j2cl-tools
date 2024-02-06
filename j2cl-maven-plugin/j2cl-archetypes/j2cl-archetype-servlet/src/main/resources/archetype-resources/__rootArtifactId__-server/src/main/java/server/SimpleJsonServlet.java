/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package ${package}.server;

import ${package}.shared.SharedType;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

/**
 * A very simple servlet, avoiding any server libraries, with an overly-simple JSON
 * encoding strategy.
 */
@WebServlet(name = "SimpleJsonServlet", urlPatterns = {"/hello.json"})
public class SimpleJsonServlet extends HttpServlet {
    private static final Pattern simpleAsciiChars = Pattern.compile("^[a-zA-Z0-9]*$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        if (!simpleAsciiChars.matcher(name).matches()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String reply = SharedType.sayHello(name);

        response.setContentType("application/json;charset=UTF-8");

        try (ServletOutputStream out = response.getOutputStream()) {
            out.print("{\"response\":\"" + reply + "\"}");
        }
    }

}
