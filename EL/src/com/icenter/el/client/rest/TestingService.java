package com.icenter.el.client.rest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.annotation.RemoteRestMethod;
import com.icenter.el.client.rest.transfer.Test;

public interface TestingService extends RemoteRESTService {

    @RemoteRestMethod(Method = "GET")
    public void postService(String passport, Test test, AsyncCallback<Test> callback);

}
