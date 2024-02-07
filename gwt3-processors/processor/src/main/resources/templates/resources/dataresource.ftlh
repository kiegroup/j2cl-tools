        ${name} = new org.kie.j2cl.tools.processors.common.resources.DataResource() {
                            public String asString() {
                                ${impl?no_esc}
                            }
                            public String getName() {
                                return "${name}";
                            }
        };
