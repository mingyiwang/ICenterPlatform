package com.icenter.el.client.rest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;

public interface LogInService extends RemoteRESTService {

    void logIn(String userName, String password, AsyncCallback<String> callback);

}
