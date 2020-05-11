package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.el.client.transfer.Test;
import com.icenter.el.client.transfer.Test2;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);

        final Test test = new Test();
        test.getTestListValue().add(new Test2());
        test.getTestListValue().add(new Test2());

        Button button = new Button("Click me");
        button.addClickHandler(clickEvent -> {
            TestingService service = GWT.create(TestingService.class);
            service.postService(test,test.getTestListValue(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(String s) {
                    showMessage(s);
                }
            });
        });

        w.add(button);
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
