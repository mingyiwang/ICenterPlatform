package com.icenter.core.client.lambda;

@FunctionalInterface
public interface IntAction<T> {

    void run(int integer, T t);

}
