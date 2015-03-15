package com.samenea.common.security.web.handler;

import com.samenea.common.security.model.User;
import com.samenea.common.security.service.CaptchaService;
import com.samenea.common.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

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
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private CaptchaService captchaService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        captchaService.successAttemptUserLogin(user,request.getRequestedSessionId());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
