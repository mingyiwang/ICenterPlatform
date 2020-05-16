package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.lambda.BiAction;

public final class JsonUtils {

    public static void tryParse(String json, BiAction<Boolean, JSONValue> action){
        try {
            JSONValue value = JSONParser.parseStrict(json);
            action.run(true, value);
        }
        catch (Exception e){
            action.run(false, null);
        }

    }

}
