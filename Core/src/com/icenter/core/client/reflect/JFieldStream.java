package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.icenter.core.client.rest.convert.JSONProperty;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

public final class JFieldStream {

    private JField[] fields;
    private JClassType targetType;

    private JFieldStream(JClassType targetType){
        Objects.requireNonNull(targetType);
        Objects.requireNonNull(targetType.getFields());
        Objects.requireNonNull(targetType.getMethods());

        this.targetType = targetType;
        this.fields = targetType.getFields();
    }

    public static JFieldStream of(JClassType targetType){
        return new JFieldStream(targetType);
    }

    public JFieldStream forEach(BiConsumer<JField, JSONProperty> consumer){
        Objects.requireNonNull(consumer);

        int len = fields.length;
        if(len == 0) {

        }

        IntStream.of(0, len-1).forEach(i -> consumer.accept(fields[i], JSONProperty.of(fields[i], targetType)));
        return this;
    }

}
