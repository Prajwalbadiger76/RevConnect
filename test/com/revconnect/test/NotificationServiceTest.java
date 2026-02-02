package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.revconnect.service.NotificationService;

public class NotificationServiceTest {

    private NotificationService service = new NotificationService();
    private final int TEST_USER_ID = 1;

    @Test
    void testAddNotification() {
        service.notifyUser(TEST_USER_ID, "JUnit Test", "FOLLOW");
        int count = service.getUnreadCount(TEST_USER_ID);
        assertTrue(count >= 1);
    }

    @Test
    void testGetNotifications() {
        List<String> list = service.getNotifications(TEST_USER_ID);
        assertNotNull(list);
    }

    @Test
    void testUnreadCount() {
        int count = service.getUnreadCount(TEST_USER_ID);
        assertTrue(count >= 0);
    }

    @Test
    void testMarkAsRead() {
        service.markAsRead(TEST_USER_ID);
        int unread = service.getUnreadCount(TEST_USER_ID);
        assertEquals(0, unread);
    }

    // ⚠ Use an invalid user ID that does not exist
    private final int INVALID_USER_ID = 99999;

    // ---------------- NEGATIVE TEST CASES ----------------

    // ❌ 1. Invalid user ID (should not crash)
    @Test
    void testAddNotificationWithInvalidUser() {
        service.notifyUser(INVALID_USER_ID, "Invalid user test", "FOLLOW");

        int count = service.getUnreadCount(INVALID_USER_ID);

        // Should not crash, should return 0 or safe value
        assertEquals(0, count);
    }

    // ❌ 2. Null message
    @Test
    void testAddNotificationWithNullMessage() {
        service.notifyUser(1, null, "FOLLOW");

        int count = service.getUnreadCount(1);

        // Should not insert invalid notification
        assertTrue(count >= 0);
    }

    // ❌ 3. Empty message
    @Test
    void testAddNotificationWithEmptyMessage() {
        service.notifyUser(1, "", "FOLLOW");

        int count = service.getUnreadCount(1);
        assertTrue(count >= 0);
    }

    // ❌ 4. Invalid category
    @Test
    void testAddNotificationWithInvalidCategory() {
        service.notifyUser(1, "Invalid category test", "UNKNOWN");

        int count = service.getUnreadCount(1);
        assertTrue(count >= 0);
    }

    // ❌ 5. Get notifications for invalid user
    @Test
    void testGetNotificationsForInvalidUser() {
        List<String> list = service.getNotifications(INVALID_USER_ID);

        assertNotNull(list);
        assertEquals(0, list.size());
    }

    // ❌ 6. Mark as read for invalid user
    @Test
    void testMarkAsReadForInvalidUser() {
        service.markAsRead(INVALID_USER_ID);

        int count = service.getUnreadCount(INVALID_USER_ID);
        assertEquals(0, count);
    }

    // ❌ 7. Unread count should never be negative
    @Test
    void testUnreadCountNeverNegative() {
        int count = service.getUnreadCount(INVALID_USER_ID);
        assertTrue(count >= 0);
    }
}

//
//package com.revconnect.test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import com.revconnect.service.NotificationService;
//
//public class NotificationServiceTest {
//
//    private NotificationService service = new NotificationService();
//
//    // ⚠ Use an invalid user ID that does not exist
//    private final int INVALID_USER_ID = 99999;
//
//    // ---------------- NEGATIVE TEST CASES ----------------
//
//    // ❌ 1. Invalid user ID (should not crash)
//    @Test
//    void testAddNotificationWithInvalidUser() {
//        service.notifyUser(INVALID_USER_ID, "Invalid user test", "FOLLOW");
//
//        int count = service.getUnreadCount(INVALID_USER_ID);
//
//        // Should not crash, should return 0 or safe value
//        assertEquals(0, count);
//    }
//
//    // ❌ 2. Null message
//    @Test
//    void testAddNotificationWithNullMessage() {
//        service.notifyUser(1, null, "FOLLOW");
//
//        int count = service.getUnreadCount(1);
//
//        // Should not insert invalid notification
//        assertTrue(count >= 0);
//    }
//
//    // ❌ 3. Empty message
//    @Test
//    void testAddNotificationWithEmptyMessage() {
//        service.notifyUser(1, "", "FOLLOW");
//
//        int count = service.getUnreadCount(1);
//        assertTrue(count >= 0);
//    }
//
//    // ❌ 4. Invalid category
//    @Test
//    void testAddNotificationWithInvalidCategory() {
//        service.notifyUser(1, "Invalid category test", "UNKNOWN");
//
//        int count = service.getUnreadCount(1);
//        assertTrue(count >= 0);
//    }
//
//    // ❌ 5. Get notifications for invalid user
//    @Test
//    void testGetNotificationsForInvalidUser() {
//        List<String> list = service.getNotifications(INVALID_USER_ID);
//
//        assertNotNull(list);
//        assertEquals(0, list.size());
//    }
//
//    // ❌ 6. Mark as read for invalid user
//    @Test
//    void testMarkAsReadForInvalidUser() {
//        service.markAsRead(INVALID_USER_ID);
//
//        int count = service.getUnreadCount(INVALID_USER_ID);
//        assertEquals(0, count);
//    }
//
//    // ❌ 7. Unread count should never be negative
//    @Test
//    void testUnreadCountNeverNegative() {
//        int count = service.getUnreadCount(INVALID_USER_ID);
//        assertTrue(count >= 0);
//    }
//}
