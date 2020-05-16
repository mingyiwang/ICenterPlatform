package com.icenter.core.client.primitive;

import com.icenter.core.client.Checks;
import com.icenter.core.client.lambda.Function;
import java.util.Locale;
import java.util.Objects;

public final class Strings {

    public static String Empty = "";

    public static boolean isNullOrEmpty(String value){
        return value == null || value.length() == 0;
    }

    public static String of(Object object){
        return of(object, Empty);
    }

    public static String of(int value){
        return String.valueOf(value);
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
            return defaultValue.execute(object);
        }
        return converter.execute(object);
    }

    public static boolean equalsIgnoreCase(String s1, String s2){
        if (s1 == null || s2 == null){
            return false;
        }
        return s1.toLowerCase(Locale.ROOT).equals(s2.toLowerCase(Locale.ROOT));
    }
}
