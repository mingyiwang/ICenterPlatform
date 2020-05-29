package com.icenter.core.client.rest.annotation;

public @interface RestEndpoint {

    HttpMethod Method() default HttpMethod.POST;
    String action()     default "";
    String id()         default "";

}
