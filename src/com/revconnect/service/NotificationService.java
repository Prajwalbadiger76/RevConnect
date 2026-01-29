package com.revconnect.service;

import java.util.List;
import com.revconnect.dao.NotificationDAO;

public class NotificationService {

    private NotificationDAO dao = new NotificationDAO();

    public void notifyUser(int userId, String message, String category) {
        if (dao.isNotificationEnabled(userId, category)) {
            dao.addNotification(userId, message, category);
        }
    }

    public List<String> getNotifications(int userId) {
        return dao.getNotifications(userId);
    }

    public void markAsRead(int userId) {
        dao.markAsRead(userId);
    }
    
    public int getUnreadCount(int userId) {
        return dao.getUnreadCount(userId);
    }
    



}
