package com.revconnect.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.revconnect.model.User;
import com.revconnect.service.UserService;

public class UserServiceTest {

    UserService service = new UserService();

    @Test
    void testUserRegistration() {

        User user = new User();
        user.setName("JUnit User");
        user.setEmail("junit" + System.currentTimeMillis() + "@test.com");
        user.setPassword("1234");
        user.setBio("JUnit Test");
        user.setUserType("PERSONAL");

        boolean result = service.register(user);

        assertTrue(result);
    }


    @Test
    void testUserLogin() {

        User user = service.login("junit@test.com", "1234");

        assertNotNull(user);
        assertEquals("JUnit User", user.getName());
    }
}
