package com.icenter.core.client.primitive;


import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public final class ArrayStream<T> {

    private T[] elements;

    private ArrayStream(T[] elements){
        this.elements = elements;
    }

    public static <T> ArrayStream of(T... elements){
        return new ArrayStream(elements);
    }

    public ArrayStream<T> forEach(Consumer<T> consumer){
        final int len = elements.length;
        IntStream.of(0, len).forEach(i -> {
            consumer.accept(elements[i]);
        });
        return this;
    }

    public ArrayStream<T> forEach(BiConsumer<Integer, T> consumer){
        final int len = elements.length;
        IntStream.of(0, len).forEach(i -> {
            consumer.accept(i, elements[i]);
        });
        return this;
    }


    public ArrayStream<T> last(Consumer<T> consumer){
        final int len = elements.length;
        consumer.accept(elements[len-1]);
        return this;
    }

}
