package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Strings;

public final class JSONParseResult {

    private boolean succeed;
    private JSONValue result;
    private Exception error;

    public final static JSONParseResult succeed(JSONValue result){
        return new JSONParseResult().setSucceed(true).setResult(result);
    }

    public final static JSONParseResult failed(Exception error){
        return new JSONParseResult().setSucceed(false).setError(error);
    }

    public JSONValue getResult() {
        return this.result;
    }

    public boolean isSucceed() {
        return this.succeed;
    }

    public Exception getError() {
        return this.error;
    }

    public String getErrorMessage() {
        return this.error == null ? Strings.Empty : this.error.getMessage();
    }

    private JSONParseResult setResult(JSONValue result) {
        this.result = result;
        return this;
    }

    private JSONParseResult setSucceed(boolean succeed) {
        this.succeed = succeed;
        return this;
    }

    private JSONParseResult setError(Exception error) {
        this.error = error;
        return this;
    }

    private JSONParseResult(){ }

}
