package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.Queue;

public class AbstractJSONQueueConverter<T> extends JSONConverter<Queue<T>> {

    @Override
    public Queue<T> createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(Queue<T> object) {
        while(object.peek() != null){

        }
        return null;
    }

    @Override
    public Queue<T> convertJSONToObject(JSONValue value) {
        return null;
    }
}
