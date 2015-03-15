package com.samenea.common.security.web.handler;

import com.samenea.common.security.service.CaptchaService;
import com.samenea.common.security.service.UserService;
import com.samenea.common.security.web.exception.NotValidCaptchaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/10/12
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationFailureHandlerImpl extends ExceptionMappingAuthenticationFailureHandler {
    @Autowired
    private CaptchaService captchaService;
    private String usernameParameter;

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter(usernameParameter);
        if (exception instanceof NotValidCaptchaException || exception instanceof LockedException || username == null) {
            super.onAuthenticationFailure(request, response, exception);
            return;
        }
        captchaService.failedAttemptLogin(username,request.getRequestedSessionId());
        super.onAuthenticationFailure(request, response, exception);
    }
}
