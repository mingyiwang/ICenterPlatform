package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.CollectionStream;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class AbstractMapJSONConverter<T1, T2> extends JSONConverter<Map<T1,T2>> {

    private static String KEY   = "KEY";
    private static String VALUE = "VALUE";

    private JSONConverter<T1> keyConverter;
    private JSONConverter<T2> valueConverter;

    protected abstract JSONConverter<T1> getKeyConverter();
    protected abstract JSONConverter<T2> getValueConverter();

    @Override
    public final Map<T1, T2> createInstance() {
        return new HashMap<T1,T2>();
    }

    @Override
    public final JSONValue convertObjectToJSON(Map<T1, T2> object) {
        if (object == null){
            return JSONNull.getInstance();
        }

        JSONArray array = new JSONArray();
        CollectionStream.of(object.entrySet()).forEach((i, e) -> {
            JSONValue key   = getKeyConverter().convertObjectToJSON(e.getKey());
            JSONValue value = getValueConverter().convertObjectToJSON(e.getValue());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(KEY,   key);
            jsonObject.put(VALUE, value);
            array.set(i, jsonObject);
        });

        return array;
    }

    @Override
    public final Map<T1, T2> convertJSONToObject(JSONValue value) {
        if(value == null || value.isNull() != null || value.isArray() == null){
            return null;
        }

        Map<T1,T2> map  = createInstance();
        JSONArray array = value.isArray();
        int size = array.size();
        IntStream.of(0, size).forEach(i -> {
            JSONObject j = array.get(i).isObject();
            map.put(
                getKeyConverter().convertJSONToObject(j.get(KEY)),
                getValueConverter().convertJSONToObject(j.get(VALUE))
            );
        });
        return map;
    }


}
