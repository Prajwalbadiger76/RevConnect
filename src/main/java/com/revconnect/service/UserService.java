package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.UserDAO;
import com.revconnect.model.User;

public class UserService {

    private UserDAO dao = new UserDAO();

    // ================= REGISTER =================
    public boolean register(User user) {
        return dao.registerUser(user);
    }

    // ================= LOGIN =================
    public User login(String email, String password) {
        return dao.login(email, password);
    }

    // ================= PROFILE =================
    public User getUserProfile(int userId) {
        return dao.getUserById(userId);
    }

    public boolean updateProfile(User user) {
        return dao.updateProfile(user);
    }

    // ================= SEARCH =================
    public List<User> searchUsers(String keyword) {
        return dao.searchUsers(keyword);
    }

    public int getUserIdByName(String name) {
        return dao.getUserIdByName(name);
    }

    // ================= SECURITY QUESTION =================

    public String getSecurityQuestion(String email) {
        return dao.getSecurityQuestion(email);
    }

    public boolean setSecurityQuestion(String email, String question, String answer) {
        return dao.setSecurityQuestion(email, question, answer);
    }

    // ================= PASSWORD RESET =================

    public boolean resetPassword(String email, String answer, String newPassword) {
        return dao.resetPassword(email, answer, newPassword);
    }
    
 // Check if email exists
    public boolean emailExists(String email) {
        return dao.emailExists(email);
    }

    // Change password (used after login)
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        return dao.changePassword(userId, oldPassword, newPassword);
    }

}
