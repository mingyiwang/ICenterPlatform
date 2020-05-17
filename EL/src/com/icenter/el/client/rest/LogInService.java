package com.icenter.el.client.rest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.el.client.rest.transfer.Session;

public interface LogInService extends RemoteRESTService {

    void logIn(String userName, String password, AsyncCallback<Session> callback);

}
