package com.revconnect.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revconnect.dao.LikeDAO;

public class LikeService {

    private static final Logger logger = LogManager.getLogger(LikeService.class);

    private LikeDAO likeDAO = new LikeDAO();

    // ================= LIKE POST =================
    public boolean likePost(int postId, int userId) {

        logger.info("User {} attempting to like post {}", userId, postId);

        boolean result = likeDAO.likePost(postId, userId);

        if (result) {
            logger.info("Post {} liked successfully by user {}", postId, userId);
        } else {
            logger.warn("Duplicate like attempt by user {} on post {}", userId, postId);
        }

        return result;
    }

    // ================= LIKE COUNT =================
    public int getLikeCount(int postId) {

        logger.info("Fetching like count for post {}", postId);

        int count = likeDAO.getLikeCount(postId);

        logger.info("Post {} has {} likes", postId, count);

        return count;
    }
    
    public boolean unlikePost(int postId, int userId) {
        return likeDAO.unlikePost(postId, userId);
    }

}
