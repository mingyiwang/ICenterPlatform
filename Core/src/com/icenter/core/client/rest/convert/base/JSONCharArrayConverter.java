package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.*;
import com.icenter.core.client.Checks;
import com.icenter.core.client.rest.convert.JSONConverter;

public class JSONCharArrayConverter extends JSONConverter<char[]> {


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
        if (value == null || value.isNull() != null){
            return null;
        }

        JSONArray array = Checks.requireArray(value);
        int size = array.size();
        char[] a = new char[size];
        for (int i = 0; i < size; i++) {
             a[i] = array.get(i).isString().stringValue().charAt(0);
        }
        return a;
    }

}
