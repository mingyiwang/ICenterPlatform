package com.icenter.core.client.lambda;

@FunctionalInterface
public interface BiAction<T1, T2> {

    void run(T1 t1, T2 t2);

}
