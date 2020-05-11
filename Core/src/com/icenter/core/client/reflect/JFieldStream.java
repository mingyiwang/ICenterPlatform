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

    private JFieldStream(JField[] fields, JClassType targetType){
        Objects.requireNonNull(fields);
        Objects.requireNonNull(targetType);

        this.fields = fields;
        this.targetType = targetType;
    }

    public static JFieldStream of(JField[] fields, JClassType targetType){
        return new JFieldStream(fields, targetType);
    }

    public JFieldStream forEach(BiConsumer<JField, JSONProperty> consumer){
        final int len = fields.length;
        IntStream.of(0, len).forEach(i -> {
            consumer.accept(fields[i], JSONProperty.of(fields[i], targetType));
        });
        return this;
    }

}
