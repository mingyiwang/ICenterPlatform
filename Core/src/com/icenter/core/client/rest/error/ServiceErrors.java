package com.icenter.core.client.rest.error;

import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;

public final class ServiceErrors {

    private static void handServiceError(ServiceException error){
        JSONParseResult result = JSON.parse(error.getMessage());
        if(result.isSucceed()){
           JSONValue value = result.getResult();
           if (value.isString() != null){
               String errorMessage = value.isString().stringValue();
           } else if(value.isObject() != null){
               JSONObject object = value.isObject();
               String errorMessage = object.get("message").isString().stringValue();
           }
        }
        else {
           // not a service error?
        }
    }

    public static void handleError(Throwable error){
        if(error instanceof RequestException){

        }
        else if(error instanceof ServiceException){

        }
        else if(error instanceof MalformedJSONException){

        }
        else if(error instanceof UnexpectedJSONException){

        }
        else {
            // Should this ever happens?
        }
    }

}
