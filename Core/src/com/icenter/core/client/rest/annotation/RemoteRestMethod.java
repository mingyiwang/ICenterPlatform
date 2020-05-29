package com.icenter.core.client.rest.annotation;

public @interface RemoteRestMethod {

    RestMethod Method() default RestMethod.POST;
    String action()   default "";

}
