package com.revconnect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.model.Post;
import com.revconnect.util.DBConnection;

public class PostDAO {

    // ================= CREATE POST =================
    public boolean createPost(Post post) {

        boolean created = false;

//        String sql = "INSERT INTO posts (user_id, content) VALUES (?, ?)";
        String sql = "INSERT INTO posts (user_id, content, hashtags) VALUES (?, ?, ?)";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getHashtags()); 

            int rows = ps.executeUpdate();

            if (rows > 0) {
                created = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return created;
    }

    // ================= GET POSTS BY USER =================
    public List<Post> getPostsByUserId(int userId) {

        List<Post> posts = new ArrayList<>();

        String sql = "SELECT post_id, content FROM posts WHERE user_id = ? ORDER BY post_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setContent(rs.getString("content"));
                posts.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }
    
 // ================= DELETE POST =================
    public boolean deletePost(int postId, int userId) {

        boolean deleted = false;

        String sql = "DELETE FROM posts WHERE post_id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                deleted = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return deleted;
    }
    
    public int getLastInsertedPostId(int userId) {

        String sql = "SELECT MAX(post_id) FROM posts WHERE user_id = ?";
        int postId = 0;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                postId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return postId;
    }
    
    public int getPostOwnerId(int postId) {

        String sql = "SELECT user_id FROM posts WHERE post_id = ?";
        int userId = -1;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userId;
    }
    
    public boolean sharePost(int userId, int postId) {

        String sql = "INSERT INTO posts (user_id, content, original_post_id) " +
                     "SELECT ?, content, post_id FROM posts WHERE post_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, postId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
