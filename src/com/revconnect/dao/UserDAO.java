package com.revconnect.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.revconnect.model.User;
import com.revconnect.util.DBConnection;
import com.revconnect.util.PasswordUtil;

public class UserDAO {

    // ================= REGISTER =================
    public boolean registerUser(User user) {

        boolean isRegistered = false;

        String sql = "INSERT INTO users " +
                     "(name, email, password, bio, user_type, profile_pic, location, website) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(4, user.getBio());
            ps.setString(5, user.getUserType());
            ps.setString(6, user.getProfilePic());
            ps.setString(7, user.getLocation());
            ps.setString(8, user.getWebsite());

            isRegistered = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isRegistered;
    }

    // ================= LOGIN =================
    public User login(String email, String password) {

        User user = null;

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, PasswordUtil.hashPassword(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // ================= GET USER BY ID =================
    public User getUserById(int userId) {

        User user = null;

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // ================= GET USER BY EMAIL (NEW) =================
    public User getUserByEmail(String email) {

        User user = null;

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    
 // ================= GET USER BY EMAIL (NEW) =================
    
    public int getUserIdByName(String name) {

        String sql = "SELECT user_id FROM users WHERE LOWER(name) = LOWER(?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    // ================= UPDATE PROFILE =================
    public boolean updateProfile(User user) {

        boolean updated = false;

        String sql = "UPDATE users SET name=?, bio=?, profile_pic=?, location=?, website=? WHERE user_id=?";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getName());
        	ps.setString(2, user.getBio());
        	ps.setString(3, user.getProfilePic());
        	ps.setString(4, user.getLocation());
        	ps.setString(5, user.getWebsite());
        	ps.setInt(6, user.getUserId());


            updated = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }

    // ================= COMMON USER MAPPER =================
    private User extractUser(ResultSet rs) throws Exception {

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setBio(rs.getString("bio"));
        user.setUserType(rs.getString("user_type"));
        user.setProfilePic(rs.getString("profile_pic"));
        user.setLocation(rs.getString("location"));
        user.setWebsite(rs.getString("website"));

        return user;
    }
    
//    public List<User> searchUsers(String keyword) {
//
//        List<User> users = new ArrayList<>();
//
//        String sql = "SELECT * FROM users WHERE name LIKE ? OR email LIKE ?";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, "%" + keyword + "%");
//            ps.setString(2, "%" + keyword + "%");
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                User user = new User();
//                user.setUserId(rs.getInt("user_id"));
//                user.setName(rs.getString("name"));
//                user.setEmail(rs.getString("email"));
//                user.setBio(rs.getString("bio"));
//                user.setUserType(rs.getString("user_type"));
//                user.setProfilePic(rs.getString("profile_pic"));
//                user.setLocation(rs.getString("location"));
//                user.setWebsite(rs.getString("website"));
//
//                users.add(user);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return users;
//    }
    
    public List<User> searchUsers(String keyword) {

        List<User> list = new ArrayList<>();

        String sql = "SELECT user_id, name, email, bio, location, website FROM users WHERE LOWER(name) LIKE ? OR LOWER(email) LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ps.setString(2, "%" + keyword.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setBio(rs.getString("bio"));
                u.setLocation(rs.getString("location"));
                u.setWebsite(rs.getString("website"));
                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


}
