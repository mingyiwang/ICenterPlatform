package com.icenter.core.client;

public final class Checks {


    public static void is(Object object, Class<?> expected){

        if(object.getClass() == expected){

        }
    }

    private static <T extends Exception> void failAndThrow(String message){

    }

}
