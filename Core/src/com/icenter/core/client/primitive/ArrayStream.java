package com.icenter.core.client.primitive;


import com.icenter.core.client.lambda.Action;
import com.icenter.core.client.lambda.IntAction;
import java.util.Objects;
import java.util.stream.IntStream;

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
        final int len = elements.length;
        IntStream.of(0, len-1).forEach(i -> action.run(elements[i]));
        return this;
    }

    public ArrayStream<T> forEach(IntAction<T> action){
        Objects.requireNonNull(action);
        final int len = elements.length;
        IntStream.of(0, len-1).forEach(i -> action.run(i, elements[i]));
        return this;
    }

    public ArrayStream<T> last(Action<T> action){
        Objects.requireNonNull(action);
        final int len = elements.length;
        action.run(elements[len - 1]);
        return this;
    }

}
