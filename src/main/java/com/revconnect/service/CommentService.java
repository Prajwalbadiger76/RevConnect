package com.revconnect.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revconnect.dao.CommentDAO;

public class CommentService {

    private static final Logger logger = LogManager.getLogger(CommentService.class);

    private CommentDAO commentDAO = new CommentDAO();

    // ================= ADD COMMENT =================
    public boolean addComment(int postId, int userId, String comment) {

        logger.info("User {} attempting to comment on post {}", userId, postId);

        boolean result = commentDAO.addComment(postId, userId, comment);

        if (result) {
            logger.info("Comment added successfully on post {}", postId);
        } else {
            logger.error("Failed to add comment on post {}", postId);
        }

        return result;
    }

    // ================= VIEW COMMENTS =================
    public List<String> getComments(int postId) {

        logger.info("Fetching comments for post {}", postId);

        List<String> comments = commentDAO.getCommentsByPostId(postId);

        logger.info("Total comments found for post {}: {}", postId, comments.size());

        return comments;
    }
    
    public boolean deleteComment(int commentId, int userId) {
        return commentDAO.deleteComment(commentId, userId);
    }

}
