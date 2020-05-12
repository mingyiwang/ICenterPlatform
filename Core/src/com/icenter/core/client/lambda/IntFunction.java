package com.icenter.core.client.lambda;


@FunctionalInterface
public interface IntFunction<T,R> {

    R execute(int i, T t);

    
}
