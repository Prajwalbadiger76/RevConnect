package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.revconnect.model.Post;
import com.revconnect.service.PostService;

public class PostServiceTest {

    PostService service = new PostService();

    @Test
    void testCreatePost() {

        Post post = new Post();
        post.setUserId(1); // existing user
        post.setContent("JUnit test post");

        boolean result = service.createPost(post);

        assertTrue(result);
    }
}
