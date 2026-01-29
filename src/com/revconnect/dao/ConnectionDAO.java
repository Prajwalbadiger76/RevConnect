package com.revconnect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.model.User;
import com.revconnect.util.DBConnection;

//import java.sql.*;
//import java.util.*;
//import com.revconnect.util.DBConnection;

public class ConnectionDAO {

    // Send follow request
    public boolean followUser(int followerId, int followingId) {

        if (followerId == followingId) return false;

        String checkSql = "SELECT * FROM connections WHERE follower_id=? AND following_id=?";
        String insertSql = "INSERT INTO connections (follower_id, following_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement check = con.prepareStatement(checkSql)) {

            check.setInt(1, followerId);
            check.setInt(2, followingId);

            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                return false; // already requested or following
            }

            PreparedStatement ps = con.prepareStatement(insertSql);
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    // Accept follow request
    public boolean acceptFollow(int followerId, int followingId) {

        String sql =
            "UPDATE connections SET status='ACCEPTED' " +
            "WHERE follower_id=? AND following_id=? AND status='PENDING'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    // View followers
    public List<User> getFollowers(int userId) {

        List<User> followers = new ArrayList<>();

        String sql =
            "SELECT u.user_id, u.name, u.email " +
            "FROM connections c " +
            "JOIN users u ON c.follower_id = u.user_id " +
            "WHERE c.following_id = ? AND c.status = 'ACCEPTED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                followers.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return followers;
    }


    // View following
    
    public List<User> getFollowing(int userId) {

        List<User> following = new ArrayList<>();

        String sql =
            "SELECT u.user_id, u.name, u.email " +
            "FROM connections c " +
            "JOIN users u ON c.following_id = u.user_id " +
            "WHERE c.follower_id = ? AND c.status = 'ACCEPTED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                following.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return following;
    }


    // View pending requests
    public List<Integer> getPendingRequests(int userId) {

        List<Integer> list = new ArrayList<>();

        String sql =
            "SELECT follower_id FROM connections " +
            "WHERE following_id=? AND status='PENDING'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("follower_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean unfollowUser(int followerId, int followingId) {

        String sql = "DELETE FROM connections WHERE follower_id=? AND following_id=? AND status='ACCEPTED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

}
