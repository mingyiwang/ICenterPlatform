package com.icenter.el.client.rest;

import com.google.gwt.core.client.GWT;
import com.icenter.core.client.rest.ServiceInitializer;

public final class Services {

    public static TestingService Testing =  ServiceInitializer.init(GWT.create(TestingService.class),"");
    public static LogInService LogIn =  ServiceInitializer.init(GWT.create(LogInService.class),"");

}
