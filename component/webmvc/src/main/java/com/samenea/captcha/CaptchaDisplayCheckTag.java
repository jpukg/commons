package com.samenea.captcha;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

/**
 * This tag can be used to check if captcha should be displayed or not. Surrounded in this tag,
 * captcha form can be displayed with arbitrary look and feel.
 * @see com.samenea.captcha.CaptchaService
 * @author: Jalal Ashrafi
 * Date: 4/7/13
 */
@Configurable
public class CaptchaDisplayCheckTag extends ConditionalTagSupport{
    private static final Logger logger = LoggerFactory.getLogger(CaptchaDisplayCheckTag.class);
    @Autowired
    CaptchaService captchaService;

    @Override
    protected boolean condition() throws JspTagException {
        final boolean captchaShouldBeDisplayed = captchaService.captchaShouldBeDisplayed();
        logger.debug("Captcha Should be Displayed? : {}",captchaShouldBeDisplayed);
        return captchaShouldBeDisplayed;
    }
}
