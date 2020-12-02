package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.el.client.rest.Services;
import com.icenter.el.client.rest.transfer.Session;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);

        Button button = new Button("Click me to log in");
        button.addClickHandler(clickEvent -> {
            Services.LogIn.logIn("mingyi", "wang", new AsyncCallback<Session>() {
                @Override
                public void onFailure(Throwable caught) {

                }

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
