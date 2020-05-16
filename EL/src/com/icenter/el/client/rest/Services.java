package com.icenter.el.client.rest;

import com.google.gwt.core.client.GWT;
import com.icenter.core.client.rest.ServiceInit;

public final class Services {

    public static TestingService Testing =  ServiceInit.init(GWT.create(TestingService.class),"");
    public static LogInService LogIn =  ServiceInit.init(GWT.create(LogInService.class),"");

}
