package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public final class JSON {

   /**
    * JavaScript native way to parse a json to object with quiet error handling.
    * **/
   public final static JSONParseResult parse(String couldBeJson){
        try {
            JSONValue json = JSONParser.parseStrict(couldBeJson); // this method not execute javascript code.
            return JSONParseResult.succeed(json);
        } catch(JSONException error){
            return JSONParseResult.failed(error);
        }
   }

}
