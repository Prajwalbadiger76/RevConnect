package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.revconnect.model.Post;
import com.revconnect.service.LikeService;
import com.revconnect.service.PostService;

public class LikeServiceTest {

    @Test
    void testLikePost() {

        // Step 1: Create a post first
        PostService postService = new PostService();
        Post post = new Post();

        post.setUserId(1); // existing user
        post.setContent("JUnit test post for like");

        boolean postCreated = postService.createPost(post);
        assertTrue(postCreated);

        // Step 2: Like the post
        LikeService likeService = new LikeService();

        // Get latest post ID manually
        int postId = postService.getLastInsertedPostId(1);

        boolean result = likeService.likePost(postId, 1);

        assertTrue(result);
    }
}
