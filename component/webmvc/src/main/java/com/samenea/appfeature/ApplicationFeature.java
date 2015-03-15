package com.samenea.appfeature;


import java.lang.annotation.*;

/**
 * will be used on a controller to specify the application feature this
 * controller performs for example
 * @Author:Jalal Ashrafi
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApplicationFeature {
    String value();
}
