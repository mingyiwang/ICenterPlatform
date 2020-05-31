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

    public static void requireNotNullOrEmpty(String value) throws IllegalArgumentException {
        requireNotNullOrEmpty(value, "value can not be null or empty");
    }

    public static void requireNotNullOrEmpty(String value, String errorMessage) throws IllegalArgumentException {
        if(value == null || value.isEmpty()){
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
