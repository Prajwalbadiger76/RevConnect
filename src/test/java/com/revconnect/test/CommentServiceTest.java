package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revconnect.service.CommentService;

public class CommentServiceTest {

    CommentService service = new CommentService();

    @Test
    void testAddComment() {

        boolean result = service.addComment(1, 1, "JUnit Comment");

        assertTrue(result);
    }
    
    @Test
    void testGetComments() {
    	CommentService commentService = new CommentService();
        List<String> comments = commentService.getComments(1);
        assertNotNull(comments);
    }

}
