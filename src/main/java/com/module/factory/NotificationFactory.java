package com.module.factory;

import com.module.models.NotificationMethod;
import com.module.models.User;
import com.module.strategy.EmailNotificationStrategy;
import com.module.strategy.NotificationStrategy;
import com.module.strategy.SMSNotificationStrategy;

public class NotificationFactory {

    public static NotificationStrategy getNotificationStrategy(NotificationMethod notificationMethod, User user) {
        switch (notificationMethod) {
            case SMS:
                return new SMSNotificationStrategy(user.getPhoneNumber());
            case EMAIL:
                return new EmailNotificationStrategy(user.getEmail());
            default:
                throw new IllegalArgumentException("Invalid notification method");
        }
    }
}
