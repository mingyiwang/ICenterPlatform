package com.icenter.el.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.el.client.transfer.Test;

public interface TestingService extends RemoteRESTService {

    public void postService(Test test, AsyncCallback<Test> callback);

}
