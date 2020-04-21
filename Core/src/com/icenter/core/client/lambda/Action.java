package com.icenter.core.client.lambda;

public interface Action<T> {

    void run(T t);

}
