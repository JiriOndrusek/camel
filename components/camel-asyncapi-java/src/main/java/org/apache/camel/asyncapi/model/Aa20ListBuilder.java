package org.apache.camel.asyncapi.model;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Aa20ListBuilder {

    public static Aa20ListBuilder newBuilder() {
        return new Aa20ListBuilder();
    }

    private Aa20ListBuilder() {
    }

    private List<Object> list = new LinkedList<>();

    public Aa20ListBuilder withObject(Object value) {
        list.add(value);
        return this;
    }

    public Aa20ListBuilder addObject(String name, Consumer<Aa20ListBuilder> object) {
        Aa20ListBuilder o = new Aa20ListBuilder();
        object.accept(o);
        Object ob = o.done();
        list.add(ob);
        return this;
    }

    public Object done() {
        return list;
    }
}
