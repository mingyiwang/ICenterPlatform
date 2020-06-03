package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.*;
import com.icenter.core.client.Checks;
import com.icenter.core.client.rest.convert.JSONConverter;

public class JSONBooleanArrayConverter extends JSONConverter<boolean[]> {

    @Override
    public final boolean[] createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(boolean[] object) {
        if (object == null){
            return JSONNull.getInstance();
        }

        JSONArray json = new JSONArray();
        for (int i=0; i<object.length; i++) {
             json.set(i, JSONBoolean.getInstance(object[i]));
        }
        return json;
    }

    @Override
    public boolean[] convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        JSONArray array = Checks.requireArray(value);
        int size = array.size();
        boolean[] a = new boolean[size];
        for (int i = 0; i < size; i++) {
             a[i] = array.get(i).isBoolean().booleanValue();
        }
        return a;
    }

}
