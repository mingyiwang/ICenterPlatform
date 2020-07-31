package com.icenter.core.client.rest.error;

import com.google.gwt.json.client.JSONException;

/**
 * An exception that can be thrown when an interaction with Non JSON data.
 */
public class MalformedJSONException extends JSONException {

    public MalformedJSONException(String message) {
        super(message);
    }

    public MalformedJSONException(Throwable cause) {
        super(cause);
    }

    public MalformedJSONException(String message, Throwable cause) {
        super(message, cause);
    }

}
