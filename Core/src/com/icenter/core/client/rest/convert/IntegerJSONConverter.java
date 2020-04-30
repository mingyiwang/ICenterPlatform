package com.icenter.core.client.rest.convert;

import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;

public class IntegerJSONConverter extends JSONConverter<Integer>{

    @Override
    public Integer createInstance() {
        return Numbers.getDefault();
    }

    @Override
    public JSONValue convertObjectToJSON(Integer object) {
        return convertIntegerToJSONObject(object);
    }

    @Override
    public Integer convertJSONToObject(JSONValue value) {
        if (value.isNumber() != null){
            return (int)value.isNumber().doubleValue();
        }
        return null;
    }

}
