package com.icenter.core.client.primitive;

import com.icenter.core.client.lambda.Function;
import java.util.List;
import java.util.stream.IntStream;

public final class Joiner {

    private String joiner;

    private Joiner(String joiner){
        this.joiner = joiner;
    }

    public static Joiner on(char c){
        return new Joiner(String.valueOf(c));
    }

    public static Joiner on(String c){
        return new Joiner(Strings.of(c));
    }

    public static Joiner on(int value){
        return new Joiner(Strings.of(value));
    }

    public <T> String join(T... arrays){
        return join(arrays, t -> t.toString());
    }

    public <T> String join(T[] arrays, Function<T, String> lambda){
        if(arrays == null || arrays.length == 0) {
           return Strings.Empty;
        }

        if(arrays.length == 1){
            return lambda.execute(arrays[0]);
        }

        final StringBuilder buffer = new StringBuilder();
        IntStream.range(0, arrays.length).forEach(i -> {
            if (i > 0) {
                buffer.append(joiner);
            }
            buffer.append(lambda.execute(arrays[i]));
        });
        return buffer.toString();
    }

    public <T> String join(List<T> arrays, Function<T, String> lambda){
        if(arrays == null || arrays.size() == 0) {
            return Strings.Empty;
        }

        if(arrays.size() == 1){
           return lambda.execute(arrays.get(0));
        }

        final StringBuilder buffer = new StringBuilder();
        IntStream.range(0, arrays.size()).forEach(i -> {
            if (i > 0) {
                buffer.append(joiner);
            }
            buffer.append(lambda.execute(arrays.get(i)));
        });
        return buffer.toString();
    }
}
