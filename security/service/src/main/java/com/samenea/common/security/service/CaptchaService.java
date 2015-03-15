package com.samenea.common.security.service;

import com.samenea.common.security.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/21/12
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CaptchaService {
    void failedAttemptLogin(String username, String sessionId);

    boolean hasCaptchaRendered(String sessionId);

    void successAttemptUserLogin(User user, String requestedSessionId);
}
