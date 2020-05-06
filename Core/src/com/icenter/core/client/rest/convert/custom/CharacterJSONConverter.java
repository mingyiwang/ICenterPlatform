package com.icenter.core.client.rest.convert.custom;

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
            if(object == null){
           return JSONNull.getInstance();
        }
        return new JSONString(String.valueOf(object));
    }

    @Override
    public Character convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }
        return Character.valueOf('c');
    }

}
