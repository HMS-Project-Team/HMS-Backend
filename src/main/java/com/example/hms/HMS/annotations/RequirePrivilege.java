package com.example.hms.HMS.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePrivilege {
    String privilege();

    String type() default "READ"; // READ, WRITE, MAINTAIN
}
