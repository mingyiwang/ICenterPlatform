package com.icenter.core.client.http;

public class HttpHeaders {

    public static class ContentType {
        public static String Json = "";

        public String getName(){
            return "Content-Type";
        }

        private ContentType(){}
    }

}
