package com.icenter.el.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.el.client.transfer.Category;

public interface TestingService extends RemoteRESTService {

    public void putService();
    public void postService(int number, String text, AsyncCallback<Category> callback);

}
