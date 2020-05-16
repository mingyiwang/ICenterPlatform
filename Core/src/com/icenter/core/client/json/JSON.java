package com.icenter.core.client.json;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public final class JSON {

    private JSONSerializer serializer;
    private Class<?> classLiteral;

    private JSON(Class<?> classLiteral, JSONSerializer serializer) {
        this.serializer = serializer;
        this.classLiteral = classLiteral;
    }

    public final static JSON on(Class<?> classLiteral){
        return new JSON(classLiteral, GWT.create(classLiteral));
    }

    public final String serializeObject(Object object) throws JSONException {
        if(object.getClass() != classLiteral){
           throw new JSONException(object.getClass().getCanonicalName() + " is not supported.");
        }
        return serializer.serializeObject(object).toString();
    }

    public final <T> T deserializeJSON(String json) throws JSONException{
        JSONValue value = null;
        try {
            return (T) serializer.deserializeJSON(JSONParser.parseStrict(json));
        }
        catch(Exception err) {
            return null;
        }
    }

}
