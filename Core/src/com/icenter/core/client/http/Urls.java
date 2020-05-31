package com.icenter.core.client.http;

public final class Urls {

    public final static String addSlashIfNeeded(String url){
        if(!url.startsWith("/")){
            url = "/" + url;
        }
        if(!url.endsWith("/")){
            url = url + "/";
        }
        return url;
    }

}
