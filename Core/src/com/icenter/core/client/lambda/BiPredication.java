package com.icenter.core.client.lambda;

@FunctionalInterface
public interface BiPredication<T1, T2> {

    boolean test(T1 t1, T2 t2);

}
