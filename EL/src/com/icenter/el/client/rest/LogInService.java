package com.icenter.el.client.rest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.annotation.RestEndpoint;
import com.icenter.core.client.rest.annotation.HttpMethod;
import com.icenter.el.client.rest.transfer.Session;

public interface LogInService extends RemoteRESTService {

    @RestEndpoint(Method = HttpMethod.GET, action = "LogIn")
    void logIn(String userName, String password, AsyncCallback<Session> callback);

}
