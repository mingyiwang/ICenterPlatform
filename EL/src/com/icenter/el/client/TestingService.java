package com.icenter.el.client;

import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.el.client.transfer.Category;

public interface TestingService extends RemoteRESTService {

    public void tryAlert();
    public void testService(Integer number, String text, Category test);


}
