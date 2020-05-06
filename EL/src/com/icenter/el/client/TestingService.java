package com.icenter.el.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;

public interface TestingService extends RemoteRESTService {

    public void postService(int number, String text, String text2, int number2, AsyncCallback<String> callback);

}
