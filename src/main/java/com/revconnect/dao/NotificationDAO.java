package com.revconnect.dao;

import java.sql.*;
import java.util.*;
import com.revconnect.util.DBConnection;

public class NotificationDAO {

    // ================= ADD NOTIFICATION =================
    public boolean addNotification(int userId, String message, String category) {

        if (userId <= 0 || message == null || message.trim().isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO notifications " +
                     "(user_id, message, category, is_read, created_at) " +
                     "VALUES (?, ?, ?, 'N', SYSDATE)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, message);
            ps.setString(3, category);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    // ================= GET NOTIFICATIONS =================
    public List<String> getNotifications(int userId) {

        List<String> list = new ArrayList<>();

        String sql = "SELECT message, category, created_at " +
                     "FROM notifications WHERE user_id=? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(
                    "[" + rs.getString("category") + "] " +
                    rs.getString("message") +
                    " (" + rs.getTimestamp("created_at") + ")"
                );
            }

        } catch (Exception e) {
            // ignore safely
        }

        return list;
    }

    // ================= MARK AS READ =================
    public void markAsRead(int userId) {

        String sql = "UPDATE notifications SET is_read='Y' WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            // ignore
        }
    }

    // ================= UNREAD COUNT =================
    public int getUnreadCount(int userId) {

        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id=? AND is_read='N'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            return 0;
        }

        return 0;
    }

    // ================= CHECK PREF =================
    public boolean isNotificationEnabled(int userId, String category) {

        String sql = "SELECT COUNT(*) FROM notification_preferences WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            // Auto insert preference row
            if (rs.next() && rs.getInt(1) == 0) {

                String insert = "INSERT INTO notification_preferences " +
                                "(user_id, follow_enabled, like_enabled, comment_enabled) " +
                                "VALUES (?, 'Y', 'Y', 'Y')";

                try (PreparedStatement ps2 = con.prepareStatement(insert)) {
                    ps2.setInt(1, userId);
                    ps2.executeUpdate();
                }
            }

        } catch (Exception e) {
            return true;
        }

        return true;
    }
}
