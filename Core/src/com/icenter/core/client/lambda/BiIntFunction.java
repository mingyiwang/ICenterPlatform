package com.icenter.core.client.lambda;


@FunctionalInterface
public interface BiIntFunction<T1,T2,R> {

    R execute(int i, T1 t1, T2 t2);

    
}
