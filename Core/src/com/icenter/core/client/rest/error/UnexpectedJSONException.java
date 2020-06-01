package com.icenter.core.client.rest.error;

import com.google.gwt.json.client.JSONException;

public class UnexpectedJSONException extends JSONException {

    public UnexpectedJSONException(Throwable cause) {
        super("Unexpected JSON Message.", cause);
    }

}
