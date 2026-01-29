package com.revconnect.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revconnect.dao.UserDAO;
import com.revconnect.model.User;

public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserDAO userDAO = new UserDAO();

    // ================= REGISTER =================
    public boolean register(User user) {

        logger.info("Attempting to register user: " + user.getEmail());

        boolean result = userDAO.registerUser(user);

        if (result) {
            logger.info("User registered successfully: " + user.getEmail());
        } else {
            logger.error("User registration failed for: " + user.getEmail());
        }

        return result;
    }

    // ================= LOGIN =================
    public User login(String email, String password) {

        logger.info("Login attempt for email: " + email);

        User user = userDAO.login(email, password);

        if (user != null) {
            logger.info("Login successful for: " + email);
        } else {
            logger.warn("Login failed for: " + email);
        }

        return user;
    }

    // ================= PROFILE =================
    public User getUserProfile(int userId) {

        logger.info("Fetching profile for user ID: " + userId);

        return userDAO.getUserById(userId);
    }
 // ================= SEARCH PROFILE =================
    public List<User> searchUsers(String keyword) {
        return userDAO.searchUsers(keyword);
    }


    // ================= UPDATE PROFILE =================
    public boolean updateProfile(User user) {

        logger.info("Updating profile for user ID: " + user.getUserId());

        boolean updated = userDAO.updateProfile(user);

        if (updated) {
            logger.info("Profile updated successfully for user ID: " + user.getUserId());
        } else {
            logger.error("Failed to update profile for user ID: " + user.getUserId());
        }

        return updated;
    }

}
