package com.icenter.core.client.lambda;

@FunctionalInterface
public interface Action<T> {

    void run(T t);

}
