package com.icenter.core.client.rest.error;

import com.google.gwt.json.client.JSONException;

public class MalformedJSONException extends JSONException {

    public MalformedJSONException(String json, Throwable cause) {
        super("["+json+"]"+" is not json message.", cause);
    }

}
