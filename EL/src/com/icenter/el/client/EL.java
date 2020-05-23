package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.core.client.rest.ServiceCallback;
import com.icenter.el.client.rest.Services;
import com.icenter.el.client.rest.transfer.Session;
import com.icenter.el.client.rest.transfer.Test;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);

        Services.Test.postService("", new Test(), new AsyncCallback<Test>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Test result) {

            }
        });

        Button button = new Button("Click me to log in");
        button.addClickHandler(clickEvent -> {
            Services.LogIn.logIn("mingyi", "wang", new ServiceCallback<Session>() {
                @Override
                public void onSuccess(Session session) {
                    String passport = session.getPassport();
                }
            });
        });

        w.add(button);
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
