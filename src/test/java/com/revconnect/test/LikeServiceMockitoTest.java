package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revconnect.dao.LikeDAO;
import com.revconnect.service.LikeService;


@ExtendWith(MockitoExtension.class)
public class LikeServiceMockitoTest {

    @Mock
    private LikeDAO likeDAO;

    @InjectMocks
    private LikeService likeService;

    // ✅ POSITIVE TEST
    @Test
    void testLikePostSuccess() {

        when(likeDAO.likePost(10, 1)).thenReturn(true);

        boolean result = likeService.likePost(10, 1);

        assertTrue(result);
        verify(likeDAO).likePost(10, 1);
    }

    // ❌ NEGATIVE TEST – duplicate like
    @Test
    void testLikePostDuplicate() {

        when(likeDAO.likePost(10, 1)).thenReturn(false);

        boolean result = likeService.likePost(10, 1);

        assertFalse(result);
        verify(likeDAO).likePost(10, 1);
    }

    // ❌ Invalid post
    @Test
    void testLikeInvalidPost() {

        when(likeDAO.likePost(9999, 1)).thenReturn(false);

        boolean result = likeService.likePost(9999, 1);

        assertFalse(result);
    }

    // ✅ Like count
    @Test
    void testLikeCount() {

        when(likeDAO.getLikeCount(10)).thenReturn(5);

        int count = likeService.getLikeCount(10);

        assertEquals(5, count);
    }

    // ❌ Unlike without like
    @Test
    void testUnlikeFailure() {

        when(likeDAO.unlikePost(10, 1)).thenReturn(false);

        boolean result = likeService.unlikePost(10, 1);

        assertFalse(result);
    }
}
