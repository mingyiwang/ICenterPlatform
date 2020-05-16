package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.core.client.rest.ServiceCallback;
import com.icenter.el.client.rest.Services;
import com.icenter.el.client.rest.transfer.Test;
import com.icenter.el.client.rest.transfer.Test2;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);

        final Test test = new Test();
        test.getTestListValue().add(new Test2());
        test.getTestListValue().add(new Test2());

        test.getMapValue().put(new Test2(), 1);
        test.getMapValue().put(new Test2(), 2);

        Button button = new Button("Click me");
        button.addClickHandler(clickEvent -> {
            Services.LogIn.logIn("mingyi", "wang", new ServiceCallback<String>() {
                @Override
                public void onSuccess(String s) {

                }
            });

            Services.Testing.postService("", test,  new ServiceCallback<Test>() {
                @Override
                public void onSuccess(Test s) {
                    Test2 t = s.getMapValue().keySet().iterator().next();


                }
            });
        });

        w.add(button);
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
