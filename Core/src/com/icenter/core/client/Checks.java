package com.icenter.core.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.error.UnexpectedJSONException;

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

    public static JSONArray requireArray(JSONValue value){
        if(value.isArray() == null) throw new UnexpectedJSONException("Expected is json array but was not.");
        return value.isArray();
    }

}
