package com.icenter.core.client.rest.convert;

import com.google.gwt.json.client.JSONValue;

public class IntegerJSONConverter extends JSONConverter<Integer>{

    @Override
    public Integer createInstance() {
        return 0;
    }

    @Override
    public JSONValue convertObjectToJSON(Integer object) {
        return convertIntegerToJSONObject(object);
    }

    @Override
    public Integer convertJSONToObject(JSONValue value) {
        if(value.isNumber() != null){
            return (int)value.isNumber().doubleValue();
        }
        return null;
    }

}
