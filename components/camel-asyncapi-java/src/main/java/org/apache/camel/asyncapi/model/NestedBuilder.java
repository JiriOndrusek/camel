package org.apache.camel.asyncapi.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class NestedBuilder<T, V> {

    public abstract V build();
    protected T parent;

    public T done() {
        Class<?> parentClass = parent.getClass();
        try {
            V build = this.build();
            String methodname = "with" + build.getClass().getSimpleName();
            Method method = parentClass.getDeclaredMethod(methodname, build.getClass());
            method.invoke(parent, build);
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return parent;
    }



    public <P extends NestedBuilder<T, V>> P withParentBuilder(T parent) {
        this.parent = parent;
        return (P) this;
    }
}