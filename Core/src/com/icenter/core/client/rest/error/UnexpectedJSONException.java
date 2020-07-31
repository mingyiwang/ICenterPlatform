package com.icenter.core.client.rest.error;

import com.google.gwt.json.client.JSONException;

/**
 * An exception that can be thrown when an interaction with Not Expected JSON data.
 *
 */
public class UnexpectedJSONException extends JSONException {

    public UnexpectedJSONException(String message) {
        super(message);
    }

    public UnexpectedJSONException(Throwable cause) {
        super(cause);
    }

    public UnexpectedJSONException(String message, Throwable cause) {
        super(message, cause);
    }


}
