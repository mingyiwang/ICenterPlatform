package com.icenter.core.client.rest;

import com.google.gwt.user.client.Window;

public final class ServiceInit {

    public final static <T extends RemoteRESTService> T init(T service, String endpoint){
        service.setServiceEndPoint(getServiceRootURL() + endpoint);
        return service;
    }

    public final static boolean isLocal() {
        return Window.Location.getPort().equals("8888");
    }

    public final static String getServiceRootURL() {
        return isLocal() ? "/Proxy" : "";
    }

}
