package com.icenter.el.client.rest;

import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.ServiceCallback;
import com.icenter.el.client.rest.transfer.Test;

public interface TestingService extends RemoteRESTService {

    public void postService(String passport, Test test, ServiceCallback<Test> callback);

}
