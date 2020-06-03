package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.Checks;
import com.icenter.core.client.rest.convert.JSONConverter;

public class JSONLongArrayConverter extends JSONConverter<long[]> {

    @Override
    public final long[] createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(long[] object) {
        if (object == null){
            return JSONNull.getInstance();
        }

        JSONArray json = new JSONArray();
        for (int i=0; i<object.length; i++) {
             json.set(i, new JSONNumber(object[i]));
        }
        return json;
    }

    @Override
    public long[] convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        JSONArray array = Checks.requireArray(value);
        int size = array.size();
        long[] a = new long[size];
        for (int i = 0; i < size; i++) {
             a[i] = (long) array.get(i).isNumber().doubleValue();
        }
        return a;
    }

}
