package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revconnect.service.NotificationService;

public class NotificationServiceTest {

    private NotificationService notificationService = new NotificationService();

    // Use an existing user ID from your DB
    private final int TEST_USER_ID = 1;

    @Test
    void testAddNotification() {

        notificationService.notifyUser(
                TEST_USER_ID,
                "JUnit Test Notification",
                "FOLLOW"
        );

        int unreadCount = notificationService.getUnreadCount(TEST_USER_ID);

        assertTrue(unreadCount >= 1);
    }

    @Test
    void testGetNotifications() {

        List<String> notifications =
                notificationService.getNotifications(TEST_USER_ID);

        assertNotNull(notifications);
    }

    @Test
    void testUnreadCount() {

        int count = notificationService.getUnreadCount(TEST_USER_ID);

        assertTrue(count >= 0);
    }

    @Test
    void testMarkAsRead() {

        notificationService.markAsRead(TEST_USER_ID);

        int unread = notificationService.getUnreadCount(TEST_USER_ID);

        assertEquals(0, unread);
    }
}
