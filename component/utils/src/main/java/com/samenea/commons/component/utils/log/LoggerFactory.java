package com.samenea.commons.component.utils.log;

import org.slf4j.Logger;

/**
 * <p>This is just a simple wrapper for slf4j {@link org.slf4j.LoggerFactory}</p>
 * For special purpose logging client should use
 * {@link #getLogger(Class, com.samenea.commons.component.utils.log.LoggerFactory.LoggerType)}
 * or {@link #getLogger(String, com.samenea.commons.component.utils.log.LoggerFactory.LoggerType)}
 * <p>Using this class enables consistent way to get loggers for different purposes</p>
 * (for example exceptions,security,performance)</p>
 * <pre>
 * {@code
 *
 *  <logger name="EXCEPTION_LOG.com.samenea">
 *       <level value="ALL"/>
 *       <appender-ref ref="CONSOLE"/>
 *  </logger>
 * }
 * </pre>
 * @see LoggerType for more info on usage
 * @author Jalal Ashrafi
 */
public class LoggerFactory {
    /**
     * Prefixes will be added for every logger type to the {@link #getLogger(Class, com.samenea.commons.component.utils.log.LoggerFactory.LoggerType)}
     * or {@link #getLogger(String, com.samenea.commons.component.utils.log.LoggerFactory.LoggerType)}
     * thus in log4j.xml.
     * <p>This should be considered for example to log exceptions for com.samenea package
     * a logger as follows should be defined:</p>
     * <pre>
     * {@code
     *
     *  <logger name="EXCEPTION_LOG.com.samenea">
     *       <level value="ALL"/>
     *       <appender-ref ref="CONSOLE"/>
     *  </logger>
     *}
     * </pre>
     */
    public static enum LoggerType{
        /**
         * exception logging
         */
        EXCEPTION("EXCEPTION_LOG."),
        /**
         * security logging
         */
        SECURITY("SECURITY_LOG."),
        /**
         * performance logging
         */
        PERFORMANCE("PERFORMANCE_LOG.");

        private final String prefix;
        LoggerType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

    }
    private LoggerFactory() {
    }



    public static Logger getLogger(String name, LoggerType loggerType) {
        if(name == null){
            throw new IllegalArgumentException("logger name can not be null");
        }
        if(loggerType == null){
            throw new IllegalArgumentException("loggerType can not be null");
        }
        return getLogger(loggerType.getPrefix() + name);
    }
    public static Logger getLogger(Class clazz, LoggerType loggerType) {
        if(clazz == null){
            throw new IllegalArgumentException("logger name can not be null");
        }
        return getLogger(clazz.getName(),loggerType);
    }

    public static Logger getLogger(String name){
        return org.slf4j.LoggerFactory.getLogger(name);
    }
    public static Logger getLogger(Class clazz){
        return org.slf4j.LoggerFactory.getLogger(clazz);
    }
}
