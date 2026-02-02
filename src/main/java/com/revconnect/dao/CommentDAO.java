package com.revconnect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.util.DBConnection;
import java.util.*;

public class CommentDAO {
	

    public boolean addComment(int postId, int userId, String comment) {

        String sql = "INSERT INTO comments (post_id, user_id, comment_text) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ps.setInt(2, userId);
            ps.setString(3, comment);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<String> getCommentsByPostId(int postId) {

        List<String> comments = new ArrayList<>();

        String sql = "SELECT comment_text FROM comments WHERE post_id = ? ORDER BY commented_at";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comments.add(rs.getString("comment_text"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }
    
    
    public boolean deleteComment(int commentId, int userId) {

        String sql = "DELETE FROM comments WHERE comment_id=? AND user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, commentId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;

    }
    

}
