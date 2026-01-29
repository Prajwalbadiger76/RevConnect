package com.revconnect.dao;

import java.sql.*;
import java.util.*;
import com.revconnect.util.DBConnection;

public class NotificationDAO {

    public void addNotification(int userId, String message, String category) {

        String sql = "INSERT INTO notifications (user_id, message, category) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

//            ps.setInt(1, userId);
//            ps.setString(2, message);
//            ps.executeUpdate();
            ps.setInt(1, userId);
            ps.setString(2, message);
            ps.setString(3, category);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getNotifications(int userId) {

        List<String> list = new ArrayList<>();

        String sql = "SELECT message, category, created_at FROM notifications WHERE user_id=? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(
                		"[" + rs.getString("category") + "] "
                                + rs.getString("message") +
                                " (" + rs.getTimestamp("created_at") + ")"
                		);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void markAsRead(int userId) {

        String sql = "UPDATE notifications SET is_read='Y' WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getUnreadCount(int userId) {

        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id=? AND is_read='N'";
        int count = 0;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    
    public boolean isNotificationEnabled(int userId, String type) {

        String sql =
            "SELECT " +
            "CASE " +
            "WHEN ?='LIKE' THEN like_enabled " +
            "WHEN ?='COMMENT' THEN comment_enabled " +
            "WHEN ?='FOLLOW' THEN follow_enabled " +
            "END AS status " +
            "FROM notification_preferences WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setString(2, type);
            ps.setInt(3, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "Y".equals(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true; // default allow
    }


}
