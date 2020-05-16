package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public final class JSON {

   /**
    * Traditional way to parse a json to object.
    * **/
   public final static JSONParseResult parseStrict(String couldBeJson){
        try {
            JSONValue value = JSONParser.parseStrict(couldBeJson);
            return new JSONParseResult().setError(null)
                                        .setResult(value)
                                        .setSucceed(true);
        } catch(JSONException error){
            return new JSONParseResult().setError(error)
                                        .setResult(null)
                                        .setSucceed(false);
        }
   }



}
