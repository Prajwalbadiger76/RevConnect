package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revconnect.model.Post;
import com.revconnect.service.PostService;

public class PostServiceTest {

    PostService service = new PostService();

    // ================= POSITIVE TEST CASES =================

    @Test
    void testCreatePost() {
        Post post = new Post();
        post.setUserId(1); // valid user
        post.setContent("JUnit test post");
        post.setHashtags("#test");

        boolean result = service.createPost(post);

        assertTrue(result);
    }

    @Test
    void testGetUserPosts() {
        List<Post> posts = service.getUserPosts(1);
        assertNotNull(posts);
    }

    // ================= NEGATIVE TEST CASES =================

    // ❌ Invalid user ID
    @Test
    void testCreatePostWithInvalidUser() {
        Post post = new Post();
        post.setUserId(-1); // invalid user
        post.setContent("Invalid user post");

        boolean result = service.createPost(post);

        assertFalse(result);
    }

    // ❌ Empty content
    @Test
    void testCreatePostWithEmptyContent() {
        Post post = new Post();
        post.setUserId(1);
        post.setContent(""); // empty content

        boolean result = service.createPost(post);

        assertFalse(result);
    }

    // ❌ Null content
    @Test
    void testCreatePostWithNullContent() {
        Post post = new Post();
        post.setUserId(1);
        post.setContent(null);

        boolean result = service.createPost(post);

        assertFalse(result);
    }

    // ❌ Get posts for non-existing user
    @Test
    void testGetPostsForInvalidUser() {
        List<Post> posts = service.getUserPosts(9999);
        assertNotNull(posts);
        assertEquals(0, posts.size());
    }

    // ❌ Delete post with wrong user
    @Test
    void testDeletePostByWrongUser() {
        boolean result = service.deletePost(1, 9999);
        assertFalse(result);
    }

    // ❌ Delete non-existing post
    @Test
    void testDeleteInvalidPost() {
        boolean result = service.deletePost(9999, 1);
        assertFalse(result);
    }

    // ❌ Share invalid post
    @Test
    void testShareInvalidPost() {
        boolean result = service.sharePost(1, 9999);
        assertFalse(result);
    }

    // ❌ Search with invalid hashtag
    @Test
    void testSearchInvalidHashtag() {
        List<Post> posts = service.searchPostsByHashtag("###invalid###");
        assertNotNull(posts);
    }

    // ❌ Get owner of invalid post
    @Test
    void testGetPostOwnerInvalid() {
        int owner = service.getPostOwnerId(9999);
        assertEquals(-1, owner);
    }
}
