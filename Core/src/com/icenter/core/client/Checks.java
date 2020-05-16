package com.icenter.core.client;

public final class Checks {


    public static boolean is(Object object, Class<?> expected){

        if(object.getClass() == expected){
            return true;
        }

        return false;
    }

    private static <T extends Exception> void failAndThrow(String message){

    }

    public static void requireNotEmpty(String value){
        if(value == null || value.isEmpty()){

        }
    }
}
