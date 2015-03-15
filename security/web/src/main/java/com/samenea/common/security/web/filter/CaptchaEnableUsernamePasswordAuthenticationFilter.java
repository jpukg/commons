package com.samenea.common.security.web.filter;

import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.samenea.common.security.service.CaptchaService;
import com.samenea.common.security.web.exception.NotValidCaptchaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaEnableUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private GenericManageableCaptchaService genericManageableCaptchaService;
    @Autowired
    private CaptchaService captchaService;

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "j_captcha";
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

    public void setCaptchaParameter(String captchaParameter) {
        this.captchaParameter = captchaParameter;
    }



    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (captchaService.hasCaptchaRendered(request.getRequestedSessionId())) {
            checkCaptcha(request);
        }
        return super.attemptAuthentication(request, response);
    }

    private void checkCaptcha(HttpServletRequest request) {
        String captcha = obtainCaptcha(request);
        boolean validate = genericManageableCaptchaService.validateResponseForID(request.getSession().getId(), captcha);
        if (!validate) {
            throw new NotValidCaptchaException();
        }
    }

    private String obtainExpectedCaptchaString(HttpServletRequest request) {
        return genericManageableCaptchaService.getTextChallengeForID(request.getSession().getId());
    }

    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(captchaParameter);
    }

}