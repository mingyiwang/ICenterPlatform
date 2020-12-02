package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class JSONByteConverter extends JSONConverter<Byte> {

    @Override
    public JSONValue convertObjectToJSON(Byte object) {
        return object == null ? JSONNull.getInstance() : new JSONNumber(object);
    }

    @Override
    public Byte convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        if (value.isString() != null){
            return Byte.parseByte(value.isString().stringValue());
        }

        return (byte) value.isNumber().doubleValue();
    }

}
