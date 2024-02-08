package org.kie.j2cl.tools.di.apt.info;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import jakarta.enterprise.inject.Instance;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;

import org.kie.j2cl.tools.di.core.BeanManager;
import org.kie.j2cl.tools.di.core.BeanManagerImpl;

public class Info {

    protected final BeanManager beanManager = org.kie.j2cl.tools.di.core.BeanManagerImpl.get();

    protected void onInvoke(JoinPoint joinPoint, Field field, Object instance) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        if (field.get(joinPoint.getTarget()) == null) {
            field.set(joinPoint.getTarget(), instance);
        }
    }

    protected Field getField(Class clazz, String name) {
        for (java.lang.reflect.Field field : FieldUtils.getAllFields(clazz)) {
            if (field.getName().equals(name))
                return field;
            }
        throw new Error("Error: no field named '" + name + "' at  " + clazz + " at ");
    }
}
