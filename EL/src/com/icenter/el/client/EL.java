package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);

        Button button = new Button("Click me");
        button.addClickHandler(clickEvent -> {
            TestingService service = GWT.create(TestingService.class);
            service.postService(0, "I am text1", "I am text2", 12, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable throwable) {
                    showMessage(throwable.getMessage());
                }

                @Override
                public void onSuccess(String integer) {
                    showMessage("Value : " + integer);
                }
            });
        });

        w.add(button);
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
