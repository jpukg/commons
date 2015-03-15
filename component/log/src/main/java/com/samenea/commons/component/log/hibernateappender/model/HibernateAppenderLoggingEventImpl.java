package com.samenea.commons.component.log.hibernateappender.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 */
@Table(name = "logger")
@Entity
public class HibernateAppenderLoggingEventImpl implements HibernateAppenderLoggingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 1000)
    private String message;
    @Column
    private String className;
    @Column
    private String fileName;
    @Column
    private String lineNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    @Column
    private String loggerName;
    @Column
    private String methodName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column
    private String threadName;
    @Column
    private String remoteAddress;
    @Column
    private String username;
    @Column(name="logLevel")
    private String level;
    @ElementCollection
    @CollectionTable(name="logger_throwable", joinColumns=@JoinColumn(name="logging_id"))
    private List<LogEventThrowable> loggingEventThrowableWrapper;

    public List<LogEventThrowable> getLoggingEventThrowableWrapper() {
        return loggingEventThrowableWrapper;
    }

    public void setLoggingEventThrowableWrapper(List<LogEventThrowable> loggingEventThrowableWrapper) {
        this.loggingEventThrowableWrapper = loggingEventThrowableWrapper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void addThrowableMessage(int position, String throwableMessage) {
        if (loggingEventThrowableWrapper == null){
            loggingEventThrowableWrapper = new ArrayList<LogEventThrowable>();
        }
        loggingEventThrowableWrapper.add(new LogEventThrowable(position, throwableMessage));
    }
}