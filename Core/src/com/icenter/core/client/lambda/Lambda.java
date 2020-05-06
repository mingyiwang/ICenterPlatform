package com.icenter.core.client.lambda;

public final class Lambda {
    
    public static <T> Lambda of(T... elements){
        return new Lambda();
    }


    public <T> T last(){
        return null;
    }

}
