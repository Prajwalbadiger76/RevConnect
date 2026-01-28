package com.revconnect.service;

import com.revconnect.dao.UserDAO;
import com.revconnect.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public boolean register(User user) {
        return userDAO.registerUser(user);
    }
    
    public User login(String email, String password) {
        return userDAO.login(email, password);
    }
    
    public User getUserProfile(int userId) {
        return userDAO.getUserById(userId);
    }


}
