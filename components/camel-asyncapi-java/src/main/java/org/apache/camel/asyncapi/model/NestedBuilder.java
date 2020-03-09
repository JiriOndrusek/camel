package org.apache.camel.asyncapi.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class NestedBuilder<T, V> {

    public abstract V build();
    protected T parent;

    public T done() {

        try {
            V build = this.build();
            setToParent(parent, build);
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return parent;
    }

    protected void setToParent(T parent, V build) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> parentClass = parent.getClass();
        String methodname = "with" + build.getClass().getSimpleName();
        Method method = parentClass.getDeclaredMethod(methodname, build.getClass());
        method.invoke(parent, build);
    }

    public <P extends NestedBuilder<T, V>> P withParentBuilder(T parent) {
        this.parent = parent;
        return (P) this;
    }
}