package com.revconnect.dao;

import java.sql.*;
import java.util.*;
import com.revconnect.model.Message;
import com.revconnect.util.DBConnection;

public class MessageDAO {

    // Send message
    public boolean sendMessage(int senderId, int receiverId, String message) {

        String sql = "INSERT INTO messages (sender_id, receiver_id, message) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, message);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // View chat history
    public List<Message> getChat(int user1, int user2) {
        List<Message> list = new ArrayList<>();

        String sql =
            "SELECT * FROM messages " +
            "WHERE (sender_id=? AND receiver_id=?) OR " +
            "(sender_id=? AND receiver_id=?) " +
            "ORDER BY created_at";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.setInt(3, user2);
            ps.setInt(4, user1);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message m = new Message();
                m.setMessageId(rs.getInt("message_id"));
                m.setSenderId(rs.getInt("sender_id"));
                m.setReceiverId(rs.getInt("receiver_id"));
                m.setMessage(rs.getString("message"));
                m.setRead(rs.getString("is_read").equals("Y"));
                m.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Mark messages read
    public void markAsRead(int senderId, int receiverId) {
        String sql =
            "UPDATE messages SET is_read='Y' WHERE sender_id=? AND receiver_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete chat
    public boolean deleteConversation(int user1, int user2) {
        String sql =
            "DELETE FROM messages WHERE " +
            "(sender_id=? AND receiver_id=?) OR (sender_id=? AND receiver_id=?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.setInt(3, user2);
            ps.setInt(4, user1);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Block user
    public boolean blockUser(int blockerId, int blockedId) {

        String checkSql = "SELECT COUNT(*) FROM blocked_users WHERE blocker_id=? AND blocked_id=?";
        String insertSql = "INSERT INTO blocked_users (blocker_id, blocked_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            // Check if already blocked
            try (PreparedStatement check = con.prepareStatement(checkSql)) {
                check.setInt(1, blockerId);
                check.setInt(2, blockedId);

                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("⚠ User already blocked.");
                    return false;
                }
            }

            // Insert block record
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setInt(1, blockerId);
                ps.setInt(2, blockedId);
                ps.executeUpdate();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
