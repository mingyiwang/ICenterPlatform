package com.icenter.core.client.rest.error;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class ServiceError {

    private int code;
    private String message;
    private String details;

    public static ServiceError parse(JSONValue value){
        JSONObject jsonObject = value.isObject();
        ServiceError error = new ServiceError();
        error.code = (int)jsonObject.get("code").isNumber().doubleValue();
        error.message = jsonObject.get("message").isString().stringValue();
        error.details = jsonObject.get("details").isString().stringValue();
        return error;
    }

}
