package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);

        final TestingService ts = GWT.create(TestingService.class);
        Button button = new Button("Click me");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                //ts.tryAlert();
                ts.testService(1, "test", new AsyncCallback<EL>() {
                    @Override
                    public void onFailure(Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(EL integer) {

                    }
                });
            }
        });

        w.add(button);
    }

}
