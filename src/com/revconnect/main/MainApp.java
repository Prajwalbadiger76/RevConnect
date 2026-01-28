package com.revconnect.main;

import java.util.Scanner;

import com.revconnect.model.User;
import com.revconnect.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;

import com.revconnect.util.DBConnection;
import com.revconnect.util.PasswordUtil;




public class MainApp {
	
	public static void main(String[] args) {

	    Scanner sc = new Scanner(System.in);
	    UserService service = new UserService();

	    System.out.println("==== RevConnect ====");
	    System.out.println("1. Register");
	    System.out.println("2. Login");
	    System.out.print("Choose option: ");

	    int choice = sc.nextInt();
	    sc.nextLine();

	    if (choice == 1) {

	        // -------- REGISTER --------
	        System.out.print("Enter Name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter Email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter Password: ");
	        String password = sc.nextLine();

	        System.out.print("Enter Bio: ");
	        String bio = sc.nextLine();

	        System.out.print("Enter User Type: ");
	        String type = sc.nextLine();

	        User user = new User();
	        user.setName(name);
	        user.setEmail(email);
	        user.setPassword(password);
	        user.setBio(bio);
	        user.setUserType(type);

	        if (service.register(user)) {
	            System.out.println("✅ Registration Successful!");
	            System.out.println("\n👉 Please login to continue...\n");

	            // Call login flow
	            loginFlow(service, sc);
	        } else {
	            System.out.println("❌ Registration Failed!");
	        }

	    } else if (choice == 2) {

	        // -------- LOGIN --------
	        loginFlow(service, sc);

	    } else {
	        System.out.println("❌ Invalid option!");
	    }

	    sc.close();
	}
    
    // ================= LOGIN FLOW =================

    private static void loginFlow(UserService service, Scanner sc) {

        boolean loggedIn = false;

        while (!loggedIn) {

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            User loggedUser = service.login(email, password);

            if (loggedUser != null) {
                System.out.println("✅ Login Successful!");
                showProfileMenu(loggedUser, service, sc);
                loggedIn = true;
            } else {
                System.out.println("❌ Invalid email or password!");

                System.out.println("\n1. Try Again");
                System.out.println("2. Exit");
                System.out.print("Choose option: ");

                int retry = sc.nextInt();
                sc.nextLine();

                if (retry == 2) {
                    System.out.println("👋 Exiting application.");
                    break;
                }
            }
        }
    }

    // ================= PROFILE MENU =================
    
    private static void showProfileMenu(User user, UserService service, Scanner sc) {

        boolean running = true;

        while (running) {
            System.out.println("\n==== USER DASHBOARD ====");
            System.out.println("1. View Profile");
            System.out.println("2. Logout");
            System.out.print("Choose option: ");

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {

                case 1:
                    User profile = service.getUserProfile(user.getUserId());
                    displayProfile(profile);

                    // Ask what to do next
                    System.out.println("\n1. Back to Dashboard");
                    System.out.println("2. Logout");
                    System.out.print("Choose option: ");

                    int choice = sc.nextInt();
                    sc.nextLine();

                    if (choice == 2) {
                        System.out.println("👋 Logged out successfully.");
                        running = false;
                    }
                    break;

                case 2:
                    System.out.println("👋 Logged out successfully.");
                    running = false;
                    break;

                default:
                    System.out.println("❌ Invalid option!");
            }
        }
    }


    private static void displayProfile(User user) {
        System.out.println("\n===== USER PROFILE =====");
        System.out.println("User ID   : " + user.getUserId());
        System.out.println("Name      : " + user.getName());
        System.out.println("Email     : " + user.getEmail());
        System.out.println("Bio       : " + user.getBio());
        System.out.println("User Type : " + user.getUserType());
        System.out.println("========================");
    }
    
    public boolean updateProfile(User user) {

        boolean updated = false;

        String sql = "UPDATE users SET name = ?, bio = ?, user_type = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getBio());
            ps.setString(3, user.getUserType());
            ps.setInt(4, user.getUserId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                updated = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }

}
