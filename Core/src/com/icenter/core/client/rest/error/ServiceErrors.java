package com.icenter.core.client.rest.error;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;

public final class ServiceErrors {

    private static void handServiceError(ServiceException error){
        JSONParseResult result = JSON.parse(error.getMessage());
        if(result.isSucceed()){
           JSONValue value = result.getResult();
           JSONObject _error = value.isObject();
           _error.get("statusCode");
           _error.get("message");

        }
        else {
           // not a service error?


        }
    }

}
