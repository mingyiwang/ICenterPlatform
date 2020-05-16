package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.*;
import com.icenter.core.client.rest.convert.JSONConverter;

public class PrimitiveByteArrayConverter extends JSONConverter<byte[]> {

    @Override
    public final byte[] createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(byte[] object) {
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
    public byte[] convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null || value.isArray() == null){
            return null;
        }
        JSONArray array = value.isArray();
        int size = array.size();
        byte[] a = new byte[size];
        for (int i = 0; i < size; i++) {
             a[i] = (byte) array.get(i).isNumber().doubleValue();
        }
        return a;
    }

}
