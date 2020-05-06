package com.icenter.core.client.lambda;


@FunctionalInterface
public interface Function<T,R> {

    R execute(T t);

    
}
