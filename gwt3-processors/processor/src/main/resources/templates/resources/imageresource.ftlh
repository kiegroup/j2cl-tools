        ${encoded?no_esc}
        ${name} =  new org.kie.j2cl.tools.processors.common.resources.impl.ImageResourcePrototype(
                            "${name}",
                            encoded,
                            ${width?no_esc}, ${height?no_esc});
