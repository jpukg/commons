package com.samenea.captcha;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.utils.attempt.RequestAttemptCounter;
import org.slf4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * It should be added in web xml to ensure removing lastAttemptRecord which is stored
 * in current thread for current request to prevent conflict in different http requests.
 * @see CaptchaValidator which stores last attempt info
 * @see com.samenea.commons.utils.attempt.RequestAttemptCounter#recordAttempt(String)
 * @see com.samenea.commons.utils.attempt.RequestAttemptCounter#getLastAttemptInfoInCurrentThread()
 * @author: Jalal Ashrafi
 * Date: 4/10/13
 */
public class AttemptCleanupServletRequestListener implements ServletRequestListener{
    private static final Logger logger = LoggerFactory.getLogger(AttemptCleanupServletRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        RequestAttemptCounter.removeLastAttemptInfoInCurrentThread();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
    }
}
