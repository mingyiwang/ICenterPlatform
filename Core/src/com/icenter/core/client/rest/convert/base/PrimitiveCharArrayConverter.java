package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.*;
import com.icenter.core.client.rest.convert.JSONConverter;

public class PrimitiveCharArrayConverter extends JSONConverter<char[]> {

    @Override
    public final char[] createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(char[] object) {
        if (object == null){
            return JSONNull.getInstance();
        }

        JSONArray json = new JSONArray();
        for (int i=0; i<object.length; i++) {
             json.set(i, new JSONString(String.valueOf(object[i])));
        }
        return json;
    }

    @Override
    public char[] convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null || value.isArray() == null){
            return null;
        }
        JSONArray array = value.isArray();
        int size = array.size();
        char[] a = new char[size];
        for (int i = 0; i < size; i++) {
             a[i] = array.get(i).isString().stringValue().charAt(0);
        }
        return a;
    }

}
