package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.icenter.core.client.rest.convert.JClassProperty;
import java.util.Objects;
import java.util.function.BiConsumer;

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

    public JFieldStream forEach(BiConsumer<JField, JClassProperty> consumer){
        Objects.requireNonNull(consumer);

        int len = fields.length;
        if(len == 0) {
           return this;
        }

        for(int i =0; i<len; i++){
            consumer.accept(fields[i], JClassProperty.of(fields[i], targetType));
        }
        return this;
    }

}
