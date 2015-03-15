package com.samenea.commons.component.log.hibernateappender.model;

import java.util.Date;

/**
 * Interface for logging events that are to be persisted using the
 * {@link com.samenea.commons.component.utils.hibernateappender.HibernateAppender}.
 *
 * @author David Howe
 * @version 1.0
 */
public interface HibernateAppenderLoggingEvent {
    /**
     * Returns the log message.
     *
     * @return The log message
     */
    public String getMessage();

    /**
     * Returns the name of the class.
     *
     * @return The class name
     */
    public String getClassName();

    /**
     * Returns the source file name of the class.
     *
     * @return The source file name
     */
    public String getFileName();

    /**
     * Returns the line number in the class that initiated the log event.
     *
     * @return The line number
     */
    public String getLineNumber();

    /**
     * Returns the date and time of the log event.
     *
     * @return The date and time of the log event
     */
    public Date getLogDate();

    /**
     * Returns the name of the logger.
     *
     * @return The name of the logger
     */
    public String getLoggerName();

    /**
     * Returns the name of the method in the class that initiated the log event.
     *
     * @return The name of the method
     */
    public String getMethodName();

    /**
     * Returns the date and time that the application started.
     *
     * @return The date and time that the application started
     */
    public Date getStartDate();

    /**
     * Returns the name of the thread.
     *
     * @return The name of the thread
     */
    public String getThreadName();

    /**
     * Returns the logging level of this log event.
     *
     * @return The log level of this logging event
     */
    public String getLevel();
    /**
     * Returns the logging level of this log event.
     *
     * @return The log remoteAddress of this logging event
     */
    public String getRemoteAddress();
    /**
     * Returns the logging level of this log event.
     *
     * @return The log username of this logging event
     */
    public String getUsername();



    /**
     * Sets the log message.
     *
     * @param message The log message
     */
    public void setMessage(String message);
    /**
     * Sets the log message.
     *
     * @param remoteAddress The log remoteAddress
     */
    public void setRemoteAddress(String remoteAddress);
    /**
     * Sets the log message.
     *
     * @param username The log username
     */
    public void setUsername(String username);

    /**
     * Sets the name of the class.
     *
     * @param className
     */
    public void setClassName(String className);

    /**
     * Sets source file name for the class.
     *
     * @param fileName file name
     */
    public void setFileName(String fileName);

    /**
     * Sets the line number in the class that initiated the log event.
     *
     * @param lineNumber line number
     */
    public void setLineNumber(String lineNumber);

    /**
     * Sets the date and time of the log event.
     *
     * @param logDate date and time of the log event
     */
    public void setLogDate(Date logDate);

    /**
     * Sets the logger name.
     *
     * @param loggerName logger name
     */
    public void setLoggerName(String loggerName);

    /**
     * Sets the name of the method that initiated the log event.
     *
     * @param methodName method name
     */
    public void setMethodName(String methodName);

    /**
     * Sets the date and time that the application started.
     *
     * @param startDate date and time that the application started
     */
    public void setStartDate(Date startDate);

    /**
     * Sets the name of the thread.
     *
     * @param threadName thread name
     */
    public void setThreadName(String threadName);

    /**
     * Sets the log level.
     *
     * @param level log level of this logging event
     */
    public void setLevel(String level);

    /**
     * Adds a throwable exception message to the log event.
     *
     * @param position position in the call stack
     * @param throwableMessage throwable message
     */
    public void addThrowableMessage(int position, String throwableMessage);
}
