package com.module.service;

import com.google.inject.Inject;
import com.module.factory.NotificationFactory;
import com.module.models.NotificationMethod;
import com.module.models.User;
import com.module.strategy.NotificationStrategy;

public class NotificationService {

    private NotificationStrategy notificationStrategy;
    private NotificationFactory notificationFactory;

    @Inject
    public NotificationService(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    public void sendNotification(NotificationMethod notificationMethod, User user) {
        notificationStrategy = notificationFactory.getNotificationStrategy(notificationMethod, user);
        notificationStrategy.sendNotification();
    }

}
