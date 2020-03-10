package org.apache.camel.asyncapi.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public abstract class NestedBuilder<T, V> {

    public NestedBuilder(T parent, Consumer<V> consumer) {
        this.parent = parent;
        this.consumer = consumer;
    }

    public abstract V build();

    private final T parent;
    private final Consumer<V> consumer;


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

}