package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListJSONConverter<T> extends JSONConverter<List<T>> {

    protected abstract JSONConverter<T> getConverter();

    @Override
    public final List<T> createInstance() {
        return new ArrayList<T>();
    }

    @Override
    public final JSONValue convertObjectToJSON(List<T> object) {
        if (object == null){
            return JSONNull.getInstance();
        }

        JSONArray json = new JSONArray();
        int index = 0;
        for (T o : object) {
             json.set(index, getConverter().convertObjectToJSON(o));
             index ++;
        }
        return json;
    }

    @Override
    public final List<T> convertJSONToObject(JSONValue value) {
        List<T> list = createInstance();

        if (value == null || value.isArray() != null){
            return list;
        }

        JSONArray array = (JSONArray) value;
        int size = array.size();
        for (int i = 0; i < size; i++) {
             list.add(getConverter().convertJSONToObject(array.get(i)));
        };
        return list;
    }

}
