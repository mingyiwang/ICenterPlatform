package com.icenter.core.client.lambda;

@FunctionalInterface
public interface BiIntAction<T1, T2> {

    void run(int integer, T1 t1, T2 t2);

}
