package com.icenter.core.client.http;

import com.google.gwt.http.client.URL;

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

    public final static URL fromString(String url){
        URL.encode(url);
        return null;
    }
}
