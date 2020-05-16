package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.*;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapJSONConverter<T1,T2> extends JSONConverter<Map<T1,T2>> {

    private JSONConverter<T2> valueConverter;
    private JSONConverter<T1> keyConverter;

    protected abstract JSONConverter<T1> createKeyConverter();
    protected abstract JSONConverter<T2> createValueConverter();

    public final JSONConverter<T1> getKeyConverter(){
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
        for(Map.Entry<T1, T2> entry : object.entrySet()){
            JSONValue key = getKeyConverter().convertObjectToJSON(entry.getKey());
            JSONValue value = getValueConverter().convertObjectToJSON(entry.getValue());
            jsonObject.put(key.toString(), value);
        }

        return jsonObject;
    }

    @Override
    public final Map<T1, T2> convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null || value.isObject() == null){
            return null;
        }

        JSONObject object = value.isObject();
        Map<T1, T2> map = createInstance();
        Set<String> keys = object.keySet();
        for(String key : keys){
            JSONParseResult result = JSON.parseStrict(key);
            if(result.isSucceed()){
               T1 t1 = getKeyConverter().convertJSONToObject(result.getResult());
               T2 t2 = getValueConverter().convertJSONToObject(object.get(key));
               map.put(t1, t2);
            }
            else {
                // log?
            }
        }
        return map;
    }

}
