package org.apache.camel.asyncapi.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Aa20ObjectBuilder {

    public static Aa20ObjectBuilder newBuilder() {
        return new Aa20ObjectBuilder();
    }

    private Aa20ObjectBuilder() {
    }

    private Map<String, Object> data = new LinkedHashMap<>();

    public Aa20ObjectBuilder withObject(String name, Object value) {
        data.put(name, value);
        return this;
    }

    public Aa20ObjectBuilder addObject(String name, Consumer<Aa20ObjectBuilder> object) {
        Aa20ObjectBuilder o = new Aa20ObjectBuilder();
        object.accept(o);
        Object ob = o.done();
        data.put(name, ob);
        return this;
    }

    public Aa20ObjectBuilder addList(String name, Consumer<Aa20ListBuilder> object) {
        Aa20ListBuilder l = Aa20ListBuilder.newBuilder();
        object.accept(l);
        Object ob = l.done();
        data.put(name, ob);
        return this;
    }

    public Object done() {
        return data;
    }
}
