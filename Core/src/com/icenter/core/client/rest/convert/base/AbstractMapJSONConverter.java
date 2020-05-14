package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.*;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.HashMap;
import java.util.Map;
import static com.icenter.core.client.primitive.CollectionStream.of;


public abstract class AbstractMapJSONConverter<T1, T2> extends JSONConverter<Map<T1,T2>> {

    private JSONConverter<T1> keyConverter;
    private JSONConverter<T2> valueConverter;

    protected abstract JSONConverter<T1> createKeyConverter();
    protected abstract JSONConverter<T2> createValueConverter();

    public JSONConverter<T1> getKeyConverter(){
        if(keyConverter == null){
           keyConverter = createKeyConverter();
        }

        return keyConverter;
    }

    public final JSONConverter<T2> getValueConverter(){
        if(valueConverter == null){
           valueConverter = createValueConverter();
        }
        return valueConverter;
    }

    @Override
    public final Map<T1, T2> createInstance() {
        return new HashMap<>();
    }

    @Override
    public final JSONValue convertObjectToJSON(Map<T1, T2> object) {
        if (object == null){
            return JSONNull.getInstance();
        }

        JSONObject jsonObject = new JSONObject();
        of(object.entrySet()).forEach((i, e) -> {
           JSONValue key = getKeyConverter().convertObjectToJSON(e.getKey());
           JSONValue value = getValueConverter().convertObjectToJSON(e.getValue());
           jsonObject.put(key.toString(), value);
        });

        return jsonObject;
    }

    @Override
    public final Map<T1, T2> convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null || value.isObject() == null){
            return null;
        }

        Map<T1, T2> map = createInstance();
        JSONObject object = value.isObject();
        of(object.keySet())
           .forEach(s -> map.put(
             getKeyConverter().convertJSONToObject(JSONParser.parseStrict(s)),
             getValueConverter().convertJSONToObject(object.get(s)))
        );

        return map;
    }


}
