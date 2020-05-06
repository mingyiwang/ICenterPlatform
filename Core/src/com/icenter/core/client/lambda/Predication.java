package com.icenter.core.client.lambda;

import java.util.Objects;

@FunctionalInterface
public interface Predication<T> {

    boolean test(T t);


    default Predication<T> getDefault(Predication<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return (T t) -> {
            return  predicate.test(t);
        };
    }

}
