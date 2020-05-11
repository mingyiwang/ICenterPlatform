package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractArrayJSONConverter<T> extends JSONConverter<T[]> {

    protected abstract JSONConverter<T> getConverter();

    @Override
    public T[] createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(T[] object) {
        JSONArray json = new JSONArray();
        int index = 0;
        for (T o : object) {
            json.set(index, getConverter().convertObjectToJSON(o));
            index ++;
        }
        return json;
    }

    @Override
    public T[] convertJSONToObject(JSONValue value) {
        if (value == null || value.isArray() != null){
            return null;
        }
        JSONArray array = (JSONArray) value;
        int size = array.size();
        List<T> wrapper = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
             wrapper.add(getConverter().convertJSONToObject(array.get(i)));
        };
        return (T[]) wrapper.toArray();
    }


}
