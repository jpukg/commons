package com.samenea.captcha;

/**
 * Central service for checking captcha status
 * @author: Jalal Ashrafi
 * Date: 4/7/13
 */
public interface CaptchaService {
    boolean captchaShouldBeChecked();

    /**
     * captcha display and check may be different in same http request( for example captcha should be displayed in
     * request r1 but should be checked in request r2)
     * @see #captchaShouldBeChecked()
     * @return
     */
    boolean captchaShouldBeDisplayed();
}
