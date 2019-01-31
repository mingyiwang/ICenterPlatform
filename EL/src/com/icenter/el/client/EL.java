package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.icenter.core.client.TestClient;

public class EL implements EntryPoint {

    public void onModuleLoad() {

        TestClient client = new TestClient();
        client.TryTest();
    }

}
