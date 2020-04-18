package com.icenter.el.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;

public interface TestingService extends RemoteRESTService {

    public void get(AsyncCallback<String> response);
    public void post(int param, AsyncCallback<String> response);

}
