package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.core.client.io.Base64String;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get().add(new AbsolutePanel());
        //Base64String base64String = new Base64String();

        TestingService service = GWT.create(TestingService.class);
        service.get(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(String s) {

            }
        });
    }

}
