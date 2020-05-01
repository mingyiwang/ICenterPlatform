package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.core.client.rest.convert.Converters;
import com.icenter.core.client.rest.convert.JSONConverter;
import com.icenter.el.client.transfer.Category;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);
        TestingService service = GWT.create(TestingService.class);

        JSONConverter<Category> categoryConverter = Converters.get(Category.class.getCanonicalName());
        Button button = new Button("Click me");
        button.addClickHandler(clickEvent -> {
            showMessage(categoryConverter.convertObjectToJSONString(new Category()));
        });
        w.add(button);
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
