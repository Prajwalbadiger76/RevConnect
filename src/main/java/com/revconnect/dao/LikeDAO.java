package com.revconnect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.util.DBConnection;

public class LikeDAO {

	public boolean likePost(int postId, int userId) {

	    String sql = "INSERT INTO post_likes (post_id, user_id) VALUES (?, ?)";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, postId);
	        ps.setInt(2, userId);

	        ps.executeUpdate();
	        return true;

	    } catch (Exception e) {
	        // Duplicate like attempt
	        if (e.getMessage().contains("unique")) {
	            return false;
	        }
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public int getLikeCount(int postId) {

	    String sql = "SELECT COUNT(*) FROM post_likes WHERE post_id = ?";
	    int count = 0;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, postId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public boolean unlikePost(int postId, int userId) {

	    String sql = "DELETE FROM post_likes WHERE post_id=? AND user_id=?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, postId);
	        ps.setInt(2, userId);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}


}
