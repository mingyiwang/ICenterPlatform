package com.icenter.el.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.rest.RemoteRESTService;

public interface TestingService extends RemoteRESTService {

    public void tryAlert();
    public void testService(int number, String text, AsyncCallback<EL> test);


}
