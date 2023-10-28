package com.module.conf;

import com.module.factory.NotificationFactory;
import com.module.models.NotificationMethod;
import com.module.models.User;
import com.module.strategy.EmailNotificationStrategy;
import com.module.strategy.NotificationStrategy;
import com.module.strategy.SMSNotificationStrategy;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NotificationFactoryTest {

    @Test
    public void testGetNotificationStrategyForSMS() {
        User user = new User();
        user.setPhoneNumber("+1234567890");
        NotificationStrategy strategy = NotificationFactory.getNotificationStrategy(NotificationMethod.SMS, user);

        assertTrue(strategy instanceof SMSNotificationStrategy);
        assertEquals("+1234567890", ((SMSNotificationStrategy) strategy).getPhoneNumber());
    }

    @Test
    public void testGetNotificationStrategyForEmail() {
        User user = new User();
        user.setEmail("user@example.com");
        NotificationStrategy strategy = NotificationFactory.getNotificationStrategy(NotificationMethod.EMAIL, user);

        assertTrue(strategy instanceof EmailNotificationStrategy);
        assertEquals("user@example.com", ((EmailNotificationStrategy) strategy).getReceipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNotificationStrategyForInvalidMethod() {
        User user = new User();
        NotificationFactory.getNotificationStrategy(NotificationMethod.UNKNOWN, user);
    }
}

