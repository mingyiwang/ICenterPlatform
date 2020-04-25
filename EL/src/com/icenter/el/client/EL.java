package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.core.client.json.Converters;
import com.icenter.core.client.json.JSONConverter;
import com.icenter.el.client.transfer.Category;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);


        JSONConverter<Category> category = Converters.get(Category.class.getCanonicalName());
        JSONValue value = category.handle(new Category());

        Button button = new Button("Click me");
        button.addClickHandler(clickEvent -> {
            showMessage(value.toString());
        });
        w.add(button);
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
