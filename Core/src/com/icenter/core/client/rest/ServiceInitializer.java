package com.icenter.core.client.rest;

import com.google.gwt.user.client.Window;

public final class ServiceInitializer {

    public final static <T extends RemoteRESTService> T init(T service, String endpoint){
        service.setServiceEntryPoint(getServiceRootURL() + endpoint);
        return service;
    }

    public static boolean isLocal() {
        return Window.Location.getPort().equals("9999");
    }

    public static String getServiceRootURL() {
        return isLocal() ? "/Proxy" : "";
    }

}
