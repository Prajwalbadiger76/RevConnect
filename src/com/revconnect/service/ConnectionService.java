package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.ConnectionDAO;
import com.revconnect.model.User;

public class ConnectionService {

    private ConnectionDAO dao = new ConnectionDAO();

    public boolean followUser(int followerId, int followingId) {
        return dao.followUser(followerId, followingId);
    }

    public boolean acceptFollow(int followerId, int userId) {
        return dao.acceptRequest(followerId, userId);
    }

    public List<User> getFollowers(int userId) {
        return dao.getFollowers(userId);
    }

    public List<User> getFollowing(int userId) {
        return dao.getFollowing(userId);
    }

    public List<Integer> getPendingRequests(int userId) {
        return dao.getPendingRequests(userId);
    }
    public boolean unfollowUser(int followerId, int followingId) {
        return dao.unfollowUser(followerId, followingId);
    }

}
