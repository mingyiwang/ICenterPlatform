package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.rest.convert.JSONConverter;

public class StringJSONConverter extends JSONConverter<String> {

    @Override
    public String createInstance() {
        return Strings.Empty;
    }

    @Override
    public JSONValue convertObjectToJSON(String object) {
        if(object == null){
           return JSONNull.getInstance();
        }

        return new JSONString(object);
    }

    @Override
    public String convertJSONToObject(JSONValue value) {
        if (value.isNull() != null){
            return null;
        }
        return value.isString().stringValue();
    }

}
