package com.samenea.commons.component.utils.log;

import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import static com.samenea.commons.component.utils.log.LoggerFactory.LoggerType;
import static com.samenea.commons.component.utils.log.LoggerFactory.LoggerType.EXCEPTION;

/**
 * @author Jalal Ashrafi
 */
public class LoggerFactoryTest {
    @Test
    public void should_add_exception_prefix_to_loggerName_for_class_param(){
        for (LoggerType loggerType : LoggerType.values()) {
            Logger logger = LoggerFactory.getLogger(getClass(), loggerType);
            Assert.assertNotNull(logger);
            Assert.assertEquals(loggerType.getPrefix() + getClass().getName(), logger.getName());
        }
    }
    @Test(expected = IllegalArgumentException.class)
    public void should_allow_null_getLogger_for_class(){
        LoggerFactory.getLogger((Class) null, EXCEPTION);
    }
    @Test(expected = IllegalArgumentException.class)
    public void should_not_allow_null_for_string_param(){
        LoggerFactory.getLogger((String) null,EXCEPTION);
    }
    @Test(expected = IllegalArgumentException.class)
    public void should_not_allow_null_for_loggerType(){
        LoggerFactory.getLogger(getClass(),null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void should_not_allow_null_for_loggerType_string_param(){
        LoggerFactory.getLogger("",null);
    }
    @Test
    public void should_add_prefix_to_loggerName_for_string_param(){
        final String loggerName = "log";
        for (LoggerType loggerType : LoggerType.values()) {
            Logger logger = LoggerFactory.getLogger(loggerName, loggerType);
            Assert.assertNotNull(logger);
            Assert.assertEquals(loggerType.getPrefix() + loggerName, logger.getName());
        }
    }
}
