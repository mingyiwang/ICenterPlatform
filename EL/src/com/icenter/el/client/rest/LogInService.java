package com.icenter.el.client.rest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.annotation.RemoteRestMethod;
import com.icenter.core.client.rest.annotation.RestMethod;
import com.icenter.el.client.rest.transfer.Session;

public interface LogInService extends RemoteRESTService {

    @RemoteRestMethod(Method = RestMethod.GET, action = "LogIn")
    void logIn(String userName, String password, AsyncCallback<Session> callback);

}
