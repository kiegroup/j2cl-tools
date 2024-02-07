        ${name} = new org.kie.j2cl.tools.processors.common.resources.impl.TextResourcePrototype() {
                            public String getText() {
                                ${impl?no_esc}
                            }
                            public String getName() {
                                return "${name}";
                            }
                    };
