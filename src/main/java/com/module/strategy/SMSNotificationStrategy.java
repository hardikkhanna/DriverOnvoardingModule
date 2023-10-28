package com.module.strategy;

import lombok.Getter;

@Getter
public class SMSNotificationStrategy implements NotificationStrategy {

    private static final String SMS_MESSAGE = "This is an SMS notification";

    private String phoneNumber;

    public SMSNotificationStrategy(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void sendNotification() {
        System.out.println("Sending SMS to " + this.getPhoneNumber());
    }
}
