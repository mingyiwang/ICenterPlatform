package com.icenter.core.client.primitive;

public final class ArrayStream {

    private Object[] elements;

    private ArrayStream(Object[] elements){
        this.elements = elements;
    }

    public static ArrayStream of(Object... elements){
        return new ArrayStream(elements);
    }

    public Object last(){
        return null;
    }

}
