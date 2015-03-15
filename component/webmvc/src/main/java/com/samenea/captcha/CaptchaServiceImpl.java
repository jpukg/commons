package com.samenea.captcha;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.utils.attempt.AttemptRecordInfo;
import com.samenea.commons.utils.attempt.RequestAttemptCounter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: Jalal Ashrafi
 * Date: 4/7/13
 */
@Component
public class CaptchaServiceImpl implements CaptchaService {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    private final boolean captchaIsEnabled;
    private final int maxCaptchaLessAttempts;

    @Autowired
    public CaptchaServiceImpl(@Value("${captcha.enabled}")boolean captchaIsEnabled,
                              @Value("${captcha.maxCaptchalessAttempts}") int maxCaptchaLessAttempts) {
        this.captchaIsEnabled = captchaIsEnabled;
        this.maxCaptchaLessAttempts = maxCaptchaLessAttempts;
    }

    @Override
    public boolean captchaShouldBeChecked() {
        AttemptRecordInfo attemptRecordInfo = RequestAttemptCounter.getLastAttemptInfoInCurrentThread();
        if(attemptRecordInfo == null &&  maxCaptchaLessAttempts > 0){
            return false;
        }
        if(maxCaptchaLessAttempts <= 0 || attemptRecordInfo.getAttemptCount()  > maxCaptchaLessAttempts ){
            return captchaIsEnabled;
        }
        return false;
    }

    @Override
    public  boolean captchaShouldBeDisplayed() {
        //todo needs refactor
        AttemptRecordInfo attemptRecordInfo = RequestAttemptCounter.getLastAttemptInfoInCurrentThread();
        if(attemptRecordInfo == null &&  maxCaptchaLessAttempts > 0){
            return false;
        }
        if(maxCaptchaLessAttempts <= 0 || attemptRecordInfo.getAttemptCount()  > maxCaptchaLessAttempts - 1){
            return captchaIsEnabled;
        }
        return false;
    }

}
