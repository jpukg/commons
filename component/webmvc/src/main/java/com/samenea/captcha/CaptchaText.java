package com.samenea.captcha;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * A tag which can be used to annotate a model form property to be checked as captcha text
 *
 * @see  CaptchaValidator
 * @Author:jalal
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =CaptchaValidator.class )
@Documented
public @interface
        CaptchaText {
    String message() default "{isValid.constraints.captcha}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
