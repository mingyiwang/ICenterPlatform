package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class IntegerJSONConverter extends JSONConverter<Integer> {

    @Override
    public Integer createInstance() {
        return Numbers.getDefaultInteger();
    }

    @Override
    public JSONValue convertObjectToJSON(Integer object) {
        return new JSONNumber(object);
    }

    @Override
    public Integer convertJSONToObject(JSONValue value) {
        if (value.isNull()!= null){
            return null;
        }

        if (value.isNumber() != null){
            return (int)value.isNumber().doubleValue();
        }

        return null;
    }

}
