package com.module.strategy;

import lombok.Getter;

@Getter
public class EmailNotificationStrategy implements NotificationStrategy {

    private static final String EMAIL_MESSAGE = "This is an email notification";

    private static final String EMAIL_SUBJECT = "Email notification subject";

    private String receipient;

    public EmailNotificationStrategy(String receipient) {
        this.receipient = receipient;
    }

    @Override
    public void sendNotification() {
        System.out.println("Sending email to " + this.receipient);
    }
}
