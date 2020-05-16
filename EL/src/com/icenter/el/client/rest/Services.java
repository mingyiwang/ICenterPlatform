package com.icenter.el.client.rest;

import com.google.gwt.core.client.GWT;
import com.icenter.core.client.rest.RemoteRESTServiceInitializer;

public final class Services {

    public static TestingService Testing =  RemoteRESTServiceInitializer.init((TestingService)GWT.create(TestingService.class),"");


}
