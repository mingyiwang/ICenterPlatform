package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.core.client.io.Base64String;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get().add(new AbsolutePanel());
        Base64String base64String = new Base64String();

    }

}
