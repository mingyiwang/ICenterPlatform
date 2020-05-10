package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapJSONConverter<T1, T2> extends JSONConverter<Map<T1,T2>> {

    private JSONConverter<T1> keyConverter;
    private JSONConverter<T2> valueConverter;

    protected abstract JSONConverter<T1> getKeyConverter();
    protected abstract JSONConverter<T2> getValueConverter();

    @Override
    public Map<T1, T2> createInstance() {
        return new HashMap<T1,T2>();
    }

    @Override
    public JSONValue convertObjectToJSON(Map<T1, T2> object) {
        JSONArray array = new JSONArray();
        Set<T1> keys = object.keySet();
        int index = 0;

        keys.forEach(key -> {
            JSONObject entry     = new JSONObject();
            JSONValue keyEntry   = getKeyConverter().convertObjectToJSON(key);
            JSONValue valueEntry = getValueConverter().convertObjectToJSON(object.get(key));
            entry.put("Key",   keyEntry);
            entry.put("Value", valueEntry);
            array.set(index, entry);
        });
        return array;
    }

    @Override
    public Map<T1, T2> convertJSONToObject(JSONValue value) {
        Map<T1,T2> map = createInstance();
        if(value == null || value.isNull() != null){
           return map;
        }

        JSONArray array = value.isArray();

        return map;
    }


}
