package com.samenea.commons.component.log.event;

import com.samenea.commons.component.log.event.model.LogEvent;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/7/12
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PublishEventAppender extends AppenderSkeleton implements Appender, ApplicationEventPublisherAware {
    private static ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
         this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected void append(LoggingEvent event) {

        if (applicationEventPublisher != null){
            applicationEventPublisher.publishEvent(new LogEvent(event));
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
