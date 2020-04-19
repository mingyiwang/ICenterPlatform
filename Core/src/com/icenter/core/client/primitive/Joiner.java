package com.icenter.core.client.primitive;

import com.icenter.core.client.lambda.Func;

public class Joiner {

    private char joiner;

    private Joiner(char joiner) {
        this.joiner = joiner;
    }

    public static Joiner on(char c){
        return new Joiner(c);
    }

    public <T> String join(T[] arrays){
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<arrays.length; i++){
            if(i > 0){
                buffer.append(joiner);
            }
            buffer.append(arrays[i].toString());
        }

        return buffer.toString();
    }

    public <T> String join(T[] arrays, Func<T, String> func){
        if(arrays.length == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<arrays.length; i++){
            if(i > 0){
                buffer.append(joiner);
            }
            buffer.append(func.func(arrays[i]));
        }
        return buffer.toString();
    }

}
