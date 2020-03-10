package org.apache.camel.asyncapi.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public abstract class NestedBuilder<T, V> {

    protected NestedBuilder() {
    }

    public abstract V build();

    private T parent;
    private Consumer<V> consumer;


    public T done() {
        V build = this.build();

        if(consumer != null) {
            consumer.accept(build);
        } else {
            try {
                Class<?> parentClass = parent.getClass();
                String methodname = "with" + build.getClass().getSimpleName();
                Method method = parentClass.getDeclaredMethod(methodname, build.getClass());
                method.invoke(parent, build);
            } catch (NoSuchMethodException
                    | IllegalAccessException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return parent;
    }

    public <P extends NestedBuilder<T, V>> P withParentBuilder(T parent) {
        this.parent = parent;
        return (P) this;
    }

    public <P extends NestedBuilder<T, V>> P withConsumerBuilder(Consumer<V> consumer) {
        this.consumer = consumer;
        return (P) this;
    }
}