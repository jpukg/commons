package com.samenea.commons.component.log.event.model;

import org.apache.log4j.spi.LoggingEvent;
import org.springframework.context.ApplicationEvent;

public class LogEvent extends ApplicationEvent {
    private final LoggingEvent event;


    public LogEvent(LoggingEvent event) {
        super(event);
        this.event = event;
    }

    public LoggingEvent getEvent() {
        return event;
    }
}