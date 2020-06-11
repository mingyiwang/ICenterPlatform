package com.icenter.core.client.rest.error;

import com.google.gwt.json.client.JSONException;

public class MalformedJSONException extends JSONException {

    public MalformedJSONException(String message, Throwable cause) {
        super(message, cause);
    }

}
