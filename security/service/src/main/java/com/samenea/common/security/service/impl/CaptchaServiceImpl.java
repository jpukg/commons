package com.samenea.common.security.service.impl;

import com.samenea.common.security.model.User;
import com.samenea.common.security.service.CaptchaService;
import com.samenea.common.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/21/12
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Value("${captcha.maxWithoutCaptchaTry}")
    private Integer maxAllowedTry;
    @Value("${captcha.enable}")
    private boolean enable;
    private Map<String, Integer> sessionTryMap = new HashMap<String, Integer>();
    @Autowired
    private UserService userService;

    @Override
    public void failedAttemptLogin(String username, String sessionId) {
        userService.failedAttemptUserLogin(username);
        failedAttemptSession(sessionId);
    }

    private void failedAttemptSession(String sessionId) {
        Integer tryCount = sessionTryMap.remove(sessionId);
        if (tryCount == null) {
            tryCount = 1;
        } else {
            tryCount++;
        }
        sessionTryMap.put(sessionId, tryCount);
    }

    @Override
    public boolean hasCaptchaRendered(String sessionId) {
        Integer tryCount = sessionTryMap.get(sessionId);
        return enable && tryCount != null && tryCount >= maxAllowedTry;
    }

    @Override
    public void successAttemptUserLogin(User user, String sessionId) {
        userService.successAttemptUserLogin(user);
        sessionTryMap.remove(sessionId);
    }
}
