package com.revconnect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.model.User;
import com.revconnect.util.DBConnection;
import com.revconnect.util.PasswordUtil;

public class UserDAO {

    public boolean registerUser(User user) {
        boolean isRegistered = false;

        String sql = "INSERT INTO users (name, email, password, bio, user_type) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(4, user.getBio());
            ps.setString(5, user.getUserType());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                isRegistered = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isRegistered;
    }
    
    public User login(String email, String password) {

        User user = null;

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, PasswordUtil.hashPassword(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setBio(rs.getString("bio"));
                user.setUserType(rs.getString("user_type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public User getUserById(int userId) {

        User user = null;

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setBio(rs.getString("bio"));
                user.setUserType(rs.getString("user_type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


}
