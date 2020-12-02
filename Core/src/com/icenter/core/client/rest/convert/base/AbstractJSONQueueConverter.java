package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.Checks;
import com.icenter.core.client.rest.convert.JSONConverter;

import java.util.*;

public abstract class AbstractJSONQueueConverter<T> extends JSONConverter<Queue<T>> {

    private JSONConverter<T> converter;

    protected abstract JSONConverter<T> createConverter();

    public final JSONConverter<T> getConverter(){
        if(converter == null){
           converter = createConverter();
        }
        return converter;
    }

    @Override
    public Queue<T> createInstance() {
        return new AbstractQueue<T>() {
            @Override
            public Iterator<T> iterator() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean offer(T t) {
                return false;
            }

            @Override
            public T poll() {
                return null;
            }

            @Override
            public T peek() {
                return null;
            }
        };
    }

    @Override
    public JSONValue convertObjectToJSON(Queue<T> object) {
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
    public Queue<T> convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        JSONArray array = Checks.requireArray(value);
        Queue<T> queue = createInstance();
        int size = array.size();
        for (int i = 0; i < size; i++) {
             queue.add(getConverter().convertJSONToObject(array.get(i)));
        }
        return queue;
    }
}
