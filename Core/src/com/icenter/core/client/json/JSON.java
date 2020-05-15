package com.icenter.core.client.json;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public final class JSON {

    private JSONSerializer serializer;

    private JSON(JSONSerializer serializer) {
        this.serializer = serializer;
    }

    public final static JSON on(Class<?> classLiteral){ return new JSON(GWT.create(classLiteral)); }

    public final String serializeObject(Object object) throws JSONException {
        return serializer.serializeObject(object).toString();
    }

    public final <T> T deserializeJSON(String json) throws JSONException{
        JSONValue value = null;
        try {
            value = JSONParser.parseStrict(json);
        }  catch(Exception err) {

        }
        return (T) serializer.deserializeJSON(value);
    }

}
