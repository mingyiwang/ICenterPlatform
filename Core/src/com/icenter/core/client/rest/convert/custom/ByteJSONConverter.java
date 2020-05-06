package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class ByteJSONConverter extends JSONConverter<Byte> {

    @Override
    public Byte createInstance() {
        return Numbers.getDefaultByte();
    }

    @Override
    public JSONValue convertObjectToJSON(Byte object) {
        return new JSONNumber(object);
    }

    @Override
    public Byte convertJSONToObject(JSONValue value) {
        if (value.isNull() != null){
            return null;
        }

        if (value.isNumber() != null){
            return (byte) value.isNumber().doubleValue();
        }

        return null;
    }

}
