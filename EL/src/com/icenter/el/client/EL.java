package com.icenter.el.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.icenter.core.client.json.Converters;
import com.icenter.core.client.json.JsonConverter;
import com.icenter.el.client.transfer.Category;

public class EL implements EntryPoint {

    public void onModuleLoad() {
        AbsolutePanel w = new AbsolutePanel();
        RootPanel.get().add(w);

        TestingService service = GWT.create(TestingService.class);
        service.initConverters();

        service.testService(1, "", new Category());

        JsonConverter<Category> category = Converters.get(Category.class.getCanonicalName());
        category.handle(new Category());

        Button button = new Button("Click me");
        button.addClickHandler(clickEvent -> {
            showMessage(category.getClass().getCanonicalName());
        });
        w.add(button);
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
