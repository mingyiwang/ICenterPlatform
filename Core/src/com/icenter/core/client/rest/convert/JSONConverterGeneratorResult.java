package com.icenter.core.client.rest.convert;

import com.icenter.core.client.primitive.Strings;

public final class JSONConverterGeneratorResult {

    private boolean succeed = false;
    private String result = Strings.Empty;
    private Exception error = null;

    private JSONConverterGeneratorResult(){} // hide constructor

    public static JSONConverterGeneratorResult error(String errorMessage){
        JSONConverterGeneratorResult result = new JSONConverterGeneratorResult();
        result.setSucceed(false);
        result.setError(new Exception(errorMessage));
        return result;
    }

    public static JSONConverterGeneratorResult succeed(String result){
        JSONConverterGeneratorResult resultObject = new JSONConverterGeneratorResult();
        resultObject.setResult(result);
        resultObject.setSucceed(true);
        resultObject.setError(null);
        return resultObject;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

}
