package com.revconnect.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revconnect.dao.PostDAO;
import com.revconnect.model.Post;

public class PostService {

    private static final Logger logger = LogManager.getLogger(PostService.class);

    private PostDAO postDAO = new PostDAO();

    // ================= CREATE POST =================
    public boolean createPost(Post post) {

        logger.info("Creating post for user ID: {}", post.getUserId());

        boolean result = postDAO.createPost(post);

        if (result) {
            logger.info("Post created successfully for user ID: {}", post.getUserId());
        } else {
            logger.error("Failed to create post for user ID: {}", post.getUserId());
        }

        return result;
    }

    // ================= GET USER POSTS =================
    public List<Post> getUserPosts(int userId) {

        logger.info("Fetching posts for user ID: {}", userId);

        List<Post> posts = postDAO.getPostsByUserId(userId);

        logger.info("Total posts found for user {}: {}", userId, posts.size());

        return posts;
    }

    // ================= DELETE POST =================
    public boolean deletePost(int postId, int userId) {

        logger.info("User {} attempting to delete post {}", userId, postId);

        boolean result = postDAO.deletePost(postId, userId);

        if (result) {
            logger.info("Post {} deleted successfully by user {}", postId, userId);
        } else {
            logger.warn("Failed to delete post {} by user {}", postId, userId);
        }

        return result;
    }

    // ================= GET LAST INSERTED POST ID =================
    public int getLastInsertedPostId(int userId) {

        logger.info("Fetching last inserted post ID for user {}", userId);

        int postId = postDAO.getLastInsertedPostId(userId);

        logger.info("Last post ID for user {} is {}", userId, postId);

        return postId;
    }
    
    public int getPostOwnerId(int postId) {
        return postDAO.getPostOwnerId(postId);
    }
    
    public boolean sharePost(int userId, int postId) {
        return postDAO.sharePost(userId, postId);
    }
    
    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }



}
