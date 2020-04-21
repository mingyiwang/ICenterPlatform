package com.icenter.core.client.primitive;

import com.icenter.core.client.lambda.Func;
import java.util.stream.IntStream;

public final class Joiner {

    private char joiner;

    private Joiner(char joiner) {
        this.joiner = joiner;
    }

    public static Joiner on(char c){
        return new Joiner(c);
    }

    public <T> String join(T[] arrays){
        return join(arrays, t -> t.toString());
    }

    public <T> String join(T[] arrays, Func<T,String> lambda){
        if(arrays.length == 0) {
           return "";
        }

        final StringBuilder buffer = new StringBuilder();
        IntStream.range(0, arrays.length).forEach(i -> {
            if (i > 0) {
                buffer.append(joiner);
            }
            buffer.append(lambda.func(arrays[i]));
        });
        return buffer.toString();
    }

}
