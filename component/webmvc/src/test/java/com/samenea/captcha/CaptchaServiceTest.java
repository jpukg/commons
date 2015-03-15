package com.samenea.captcha;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.utils.attempt.RequestAttemptCounter;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static junit.framework.Assert.assertTrue;

/**
 * @author: Jalal Ashrafi
 * Date: 4/7/13
 */
@ContextConfiguration(locations = {"classpath:context.xml","classpath*:contexts/mock.xml"})
public class CaptchaServiceTest extends AbstractJUnit4SpringContextTests{
    private static final Logger logger = LoggerFactory.getLogger(CaptchaServiceTest.class);
    @Autowired
    private CaptchaService captchaService;
//    @Autowired
//    private RequestAttemptCounter requestAttemptCounter;
    @Autowired
    @Value("${captcha.enabled}")
    private boolean captchaIsEnabled = false;

    @Test
    public void captchaShouldBeChecked_should_return_config_value() throws Exception {
        simulateAttempts(10);
        assertTrue(captchaIsEnabled == captchaService.captchaShouldBeChecked());
    }

    @Test
    public void captchaShouldBeDisplayed_should_return_config_value() throws Exception {
        simulateAttempts(10);
        assertTrue(captchaIsEnabled == captchaService.captchaShouldBeDisplayed());
    }

    private void simulateAttempts(int numberOfAttempts) {
        RequestAttemptCounter requestAttemptCounter = new RequestAttemptCounter();
        for (int i = 0; i < numberOfAttempts; i++) {
            requestAttemptCounter.recordAttempt("test");
        }
    }
}
