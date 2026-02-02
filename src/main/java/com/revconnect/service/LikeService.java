package com.revconnect.service;

import com.revconnect.dao.LikeDAO;
public class LikeService {

    private LikeDAO likeDAO;

    // default constructor
    public LikeService() {
        this.likeDAO = new LikeDAO();
    }

    // constructor for Mockito
    public LikeService(LikeDAO likeDAO) {
        this.likeDAO = likeDAO;
    }

    public boolean likePost(int postId, int userId) {
        return likeDAO.likePost(postId, userId);
    }

    public int getLikeCount(int postId) {
        return likeDAO.getLikeCount(postId);
    }

    public boolean unlikePost(int postId, int userId) {
        return likeDAO.unlikePost(postId, userId);
    }
}
