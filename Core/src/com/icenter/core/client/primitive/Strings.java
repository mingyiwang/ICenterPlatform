package com.icenter.core.client.primitive;

import com.icenter.core.client.lambda.Function;
import java.util.Objects;

public final class Strings {

    public static String Empty = "";

    public static String of(Object object){
        return of(object, Empty);
    }

    public static String of(Object object, String defaultValue){
        if(Objects.isNull(object)){
           return defaultValue;
        }

        return object.toString();
    }

    public static <T> String of(T object, Function<T, String> converter){
        return of(object, converter, t-> Empty);
    }

    public static <T> String of(T object, Function<T, String> converter, Function<T, String> defaultValue){
        if (Objects.isNull(object)){
            return defaultValue.func(object);
        }
        return converter.func(object);
    }

}
