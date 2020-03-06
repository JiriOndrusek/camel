package org.apache.camel.asyncApi;

public interface Aa20OrReferenceType<T> {

    default T asObject(Class<T> clazz) {
        if(clazz.isAssignableFrom(this.getClass())) {
            return (T)this;
        }
        return  null;
    }

    default Aa20Reference asReference() {
        if(this instanceof Aa20Reference) {
            return (Aa20Reference) this;
        }
        return  null;
    }
}
