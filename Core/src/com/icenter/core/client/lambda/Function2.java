package com.icenter.core.client.lambda;


@FunctionalInterface
public interface Function2<T1,T2,R> {

    R execute(T1 t1, T2 t2);

}
