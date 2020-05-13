package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.*;
import com.icenter.core.client.primitive.CollectionStream;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.HashMap;
import java.util.Map;

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

        JSONObject jsonObject = new JSONObject();
        CollectionStream.of(object.entrySet()).forEach((i, e) -> {
            JSONValue value = getValueConverter().convertObjectToJSON(e.getValue());
            JSONValue key = getKeyConverter().convertObjectToJSON(e.getKey());
            jsonObject.put(key.toString(), value);
        });

        return jsonObject;
    }

    @Override
    public final Map<T1, T2> convertJSONToObject(JSONValue value) {
        if(value == null || value.isNull() != null || value.isArray() == null){
            return null;
        }

        Map<T1,T2> map  = createInstance();
        JSONObject object = value.isObject();
        CollectionStream.of(object.keySet()).forEach(s ->
            map.put(
                getKeyConverter().convertJSONToObject(new JSONString(s)),
                getValueConverter().convertJSONToObject(object.get(s))
            )
        );
        return map;
    }


}
