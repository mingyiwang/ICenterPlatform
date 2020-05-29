package com.icenter.core.client.rest.annotation;

public @interface RemoteRestMethod {

    public String Method() default "POST";
    public String Endpoint() default "";

}
