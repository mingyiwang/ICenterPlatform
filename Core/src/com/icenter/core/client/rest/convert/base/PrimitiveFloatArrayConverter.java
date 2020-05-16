package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;

public class PrimitiveFloatArrayConverter extends JSONConverter<float[]> {

    @Override
    public final float[] createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(float[] object) {
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
    public float[] convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null || value.isArray() == null){
            return null;
        }
        JSONArray array = value.isArray();
        int size = array.size();
        float[] a = new float[size];
        for (int i = 0; i < size; i++) {
             a[i] = (float) array.get(i).isNumber().doubleValue();
        }
        return a;
    }

}
