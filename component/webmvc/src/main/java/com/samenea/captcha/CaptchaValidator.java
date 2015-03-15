package com.samenea.captcha;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.utils.attempt.AttemptRecordInfo;
import com.samenea.commons.utils.attempt.RequestAttemptCounter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate captcha fields and counts attempts on captcha which can be used
 * to make captcha mandatory based on number attempts on a url
 *
 */
@Configurable
public class CaptchaValidator implements ConstraintValidator<CaptchaText, String> {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaValidator.class);
    public static final String REQUESTURI_REQUEST_KEY = "REQUESTURI_REQUEST_KEY";
    @Autowired
    CaptchaService captchaService;
    @Autowired
    RequestAttemptCounter requestAttemptCounter;
    private CaptchaText captchaTag;


    @Override
    public void initialize(CaptchaText jCaptchResponse) {

        captchaTag = jCaptchResponse;
    }

    @Override
    public boolean isValid(String jCaptchaResponse, ConstraintValidatorContext constraintValidatorContext) {
        final String currentServletUri = (String) RequestContextHolder.getRequestAttributes().getAttribute(REQUESTURI_REQUEST_KEY, RequestAttributes.SCOPE_REQUEST);
        final AttemptRecordInfo attemptRecordInfo = requestAttemptCounter.recordAttempt(currentServletUri);
        logger.debug("Attempt without captcha  record is : {}",attemptRecordInfo.toString());
        if (!captchaService.captchaShouldBeChecked()){
            logger.debug("Captcha is disabled. Captcha will not be checked.");
            return true;
        }

        logger.debug("Validating captcha. entered text is : {}", jCaptchaResponse);
        //todo This could be moved to service
        final String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

        final Boolean response = jCaptchaResponse != null && SimpleImageCaptchaServlet.service.validateResponseForID(sessionId, jCaptchaResponse);
        if (response){
            logger.debug("Captcha check PASSED for session: {}",sessionId);
            requestAttemptCounter.removeAttemptRecord(RequestAttemptCounter.getLastAttemptInfoInCurrentThread().getAction());
        }else {
            logger.debug("Captcha check FAILED for session: {}",sessionId);
        }
        return response;
    }
}

