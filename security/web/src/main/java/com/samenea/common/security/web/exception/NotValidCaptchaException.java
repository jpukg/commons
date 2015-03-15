package com.samenea.common.security.web.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/10/12
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotValidCaptchaException extends BadCredentialsException {
    public NotValidCaptchaException(String msg) {
        super(msg);
    }
    public NotValidCaptchaException() {
        super("Not valid captcha");
    }
}
