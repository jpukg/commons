package com.samenea.notification;


/**
 * @author: Jalal Ashrafi
 * Date: 5/1/13
 */
public interface NotificationService {
    public void sendSms(String message, String mobileNumber);
    public void sendSmsToAdmin(String message);
}
