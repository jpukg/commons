package com.samenea.notification.utils;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.notification.NotificationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>Notifies by sms about application startup or shutdown
 * <p>Note1 : The shutdown notification will be sent just in the case of gracefull shutdown.
 * </p>Note2 : It is a good idea to this bean be instantiated in the last initializing application
 * context - usually web context - to ensure application started and keep down the probability of context
 * initialization failure in other contexts
 * @author: Jalal Ashrafi
 * Date: 3/26/13
 */
@Component
public class ApplicationStartupOrShutdownNotifier implements ApplicationListener<ApplicationContextEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupOrShutdownNotifier.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(ApplicationStartupOrShutdownNotifier.class, LoggerFactory.LoggerType.EXCEPTION);


    //todo make sure events processed just one time
    private volatile boolean startupNotificationAlreadyFired = false;
    private volatile boolean shutdownNotificationAlreadyFired = false;

    @Autowired
    NotificationService notificationService;

    private final String serviceName;

    @Autowired
    public ApplicationStartupOrShutdownNotifier(@Value("${notification.serviceName}") String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        logger.info("Application context Event raised. context display name is: {}, event is : {}",event.getApplicationContext().getDisplayName(),event.toString());
        if( !startupNotificationAlreadyFired && event instanceof ContextRefreshedEvent){
            //synchronization is needed but duplicate message is not so important to worth it!
            startupNotificationAlreadyFired = true;
            notificationService.sendSmsToAdmin(String.format("'%s' started at: %s.", serviceName, new Date()));
        }
        if( !shutdownNotificationAlreadyFired && event instanceof ContextClosedEvent){
            shutdownNotificationAlreadyFired = true;
            notificationService.sendSmsToAdmin(String.format("'%s' STOPPED at: %s.", serviceName, new Date()));
        }
    }
}
