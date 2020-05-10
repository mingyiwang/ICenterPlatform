package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class CharacterJSONConverter extends JSONConverter<Character> {

    @Override
    public Character createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(Character object) {
        return object == null ? JSONNull.getInstance() : new JSONString(String.valueOf(object));
    }

    @Override
    public Character convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        return value.isString().stringValue().charAt(0);
    }

}
