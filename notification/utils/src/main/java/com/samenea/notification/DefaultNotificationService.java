package com.samenea.notification;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.sms.wsclient.SmsWebServiceFactory;
import com.samenea.sms.wsclient.smswsmodel.MessageVO;
import com.samenea.sms.wsclient.smswsmodel.Priority;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
/**
 * @author: Jalal Ashrafi
 * Date: 5/1/13
 */
@Component
public class DefaultNotificationService implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultNotificationService.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(DefaultNotificationService.class, LoggerFactory.LoggerType.EXCEPTION);
    private final String adminMobileNumber;
    private final String smsProviderName ;
    private final boolean notificationEnabled;
    @Autowired
    SmsWebServiceFactory smsWebServiceFactory;
//    @Autowired
//    ExecutorService executorService;

    @Autowired
    public DefaultNotificationService(
            @Value("${notification.admin.mobileNumber}")
            String adminMobileNumber,
            @Value("${notification.sms.providerName}")
            String smsProviderName,
            @Value("${notification.admin.enable}")
            Boolean notificationEnabled) {
        Assert.hasText(smsProviderName,"smsProviderName Should not be empty or null");
        Assert.hasText(adminMobileNumber,"adminMobileNumber Should not be empty or null");
        this.smsProviderName = smsProviderName;
        this.adminMobileNumber = adminMobileNumber;
        this.notificationEnabled=notificationEnabled;
        if(!notificationEnabled){
            logger.info("Notification is disabled globally. No notification will be sent....");
        }
    }

    @Async
    @Override
    public void sendSms(String message, String mobileNumber) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Async
    @Override
    public void sendSmsToAdmin(String message) {
        if(notificationEnabled){
            sendMessage(message);
        }else {
            logger.debug("Not sending message: '{}' because notification is disabled",message);
        }
    }
    private void sendMessage(String message) {
        try {
            smsWebServiceFactory.getWebService().createMessages(null, createNotificationMessages(message));
            logger.info("message: ({}) sent to {}", message , adminMobileNumber);
        } catch (Exception e) {
            logger.warn("Can not sent message to admin mobile({}). exception message is: {}", adminMobileNumber, e.getMessage());
            exceptionLogger.warn("Can not sent message to admin mobile({}). exception message is: {}",adminMobileNumber,e.getMessage(),e);
        }
    }

    private List<MessageVO> createNotificationMessages(String message) {
        final MessageVO notificationMessage = new MessageVO();
        notificationMessage.setPriority(Priority.HIGH);
        if(!"Any".equalsIgnoreCase(smsProviderName)){
            notificationMessage.setProviderName(smsProviderName);
        }
        notificationMessage.setRecipientNumber(adminMobileNumber);
        notificationMessage.setText(message);
        List<MessageVO> messages = new ArrayList<MessageVO>(1);
        messages.add(notificationMessage);
        return messages;
    }
}
