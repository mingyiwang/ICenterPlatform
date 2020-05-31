package com.icenter.el.client.rest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.http.HttpMethod;
import com.icenter.core.client.rest.annotation.RemoteRestAction;
import com.icenter.el.client.rest.transfer.Test;

public interface TestingService extends RemoteRESTService {

    @RemoteRestAction(Method = HttpMethod.GET)
    void postService(String passport, Test test, AsyncCallback<Test> callback);

}
