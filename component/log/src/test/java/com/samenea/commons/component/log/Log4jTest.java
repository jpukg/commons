package com.samenea.commons.component.log;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Log4jTest {
    private static final Logger logger = LoggerFactory.getLogger(Log4jTest.class);
    @Ignore
    @Test
    public void test() {
        MDC.put("remoteAddress", "127.0.0.1");
        MDC.put("username", "maziar");
        logger.error("Hello {}", "user");
        logger.error("Hello {}", "user");
    }
}