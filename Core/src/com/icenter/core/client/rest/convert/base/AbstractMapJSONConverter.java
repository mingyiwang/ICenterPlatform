package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

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

        return array;
    }

    @Override
    public Map<T1, T2> convertJSONToObject(JSONValue value) {
        Map<T1,T2> map = createInstance();
        if(value == null || value.isNull() != null){
           return map;
        }

        JSONArray array = value.isArray();
        int size = array.size();
        IntStream.of(0, size).forEach(i -> {
            JSONValue entry = array.get(i);
            entry.isObject().get("");//
        });
        return map;
    }


}
