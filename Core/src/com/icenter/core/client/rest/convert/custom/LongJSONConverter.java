package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class LongJSONConverter extends JSONConverter<Long> {

    @Override
    public Long createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(Long object) {
        return new JSONNumber(object);
    }

    @Override
    public Long convertJSONToObject(JSONValue value) {
        if (value.isNull()!= null){
            return null;
        }

        if (value.isNumber() != null){
            return (long) value.isNumber().doubleValue();
        }

        return null;
    }

}
