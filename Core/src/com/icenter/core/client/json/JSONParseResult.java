package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONValue;

public class JSONParseResult {

    private JSONValue result;
    private boolean succeed;
    private JSONException error;

    public JSONValue getResult() {
        return result;
    }
    public boolean isSucceed() {
        return succeed;
    }

    public JSONException getError() {
        return error;
    }

    public JSONParseResult setResult(JSONValue result) {
        this.result = result;
        return this;
    }

    public JSONParseResult setSucceed(boolean succeed) {
        this.succeed = succeed;
        return this;
    }

    public JSONParseResult setError(JSONException error) {
        this.error = error;
        return this;
    }

}
