package com.icenter.core.client.lambda;

@FunctionalInterface
public interface BiIntAction<T> {

    void run(int integer, T t);

}
