package com.icenter.core.client.primitive;

import com.icenter.core.client.lambda.Action;
import com.icenter.core.client.lambda.IntAction;
import java.util.Objects;

public final class ArrayStream<T> {

    private T[] elements;

    private ArrayStream(T[] elements){
        this.elements = elements;
    }

    public static <T> ArrayStream of(T... elements){
        return new ArrayStream(elements);
    }

    public ArrayStream<T> forEach(Action<T> action){
        Objects.requireNonNull(action);
        for(int i=0; i< elements.length; i++){
            action.run(elements[i]);
        }
        return this;
    }

    public ArrayStream<T> forEach(IntAction<T> action){
        Objects.requireNonNull(action);
        for(int i = 0; i< elements.length; i++){
            action.run(i, elements[i]);
        }
        return this;
    }

    public ArrayStream<T> last(Action<T> action){
        Objects.requireNonNull(action);
        int len = elements.length;
        action.run(elements[len - 1]);
        return this;
    }

}
