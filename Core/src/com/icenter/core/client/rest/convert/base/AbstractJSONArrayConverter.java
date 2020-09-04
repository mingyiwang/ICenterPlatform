package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.Checks;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJSONArrayConverter<T> extends JSONConverter<T[]> {

    private JSONConverter<T> converter;
    protected abstract JSONConverter<T> createConverter();

    public final JSONConverter<T> getConverter(){
        if(converter == null){
           converter = createConverter();
        }
        return converter;
    }

    // Array is created during the run time, not allowed to override
    @Override
    public final T[] createInstance() {
        throw new UnsupportedOperationException("Array is created during the run time, not allowed to use.");
    }

    @Override
    public JSONValue convertObjectToJSON(T[] object) {
        if (object == null){
            return JSONNull.getInstance();
        }

        JSONArray json = new JSONArray();
        for (int i=0; i<object.length; i++) {
             json.set(i, getConverter().convertObjectToJSON(object[i]));
        }
        return json;
    }

    @Override
    public T[] convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null ){
            return null;
        }

        JSONArray array = Checks.requireArray(value);
        int size = array.size();
        List<T> wrapper = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
             wrapper.add(getConverter().convertJSONToObject(array.get(i)));
        }

        return (T[]) wrapper.toArray();
    }

}
