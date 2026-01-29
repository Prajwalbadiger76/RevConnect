//package com.revconnect.main;


package com.revconnect.main;

import java.util.List;
import java.util.Scanner;

import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.service.CommentService;
import com.revconnect.service.LikeService;
import com.revconnect.service.NotificationService;
import com.revconnect.service.PostService;
import com.revconnect.service.UserService;
import com.revconnect.service.ConnectionService;

public class MainApp {
	
	public static void main(String[] args) {

	    Scanner sc = new Scanner(System.in);
	    UserService service = new UserService();

	    while (true) {
	        
	        System.out.println("\n=============================");
	        System.out.println("   Welcome to RevConnect   ");
	        System.out.println("=============================\n");

	        System.out.println("1. Register");
	        System.out.println("2. Login");
	        System.out.println("3. Exit");
	        System.out.print("\nChoose option: ");

	        int choice = getValidChoice(sc, 1, 3); 

	        switch (choice) {

	            case 1:
	            	System.out.println("\n==== REGISTER ====\n");

	                System.out.print("Enter Name: ");
	                String name = sc.nextLine();
	                
	                String email;
	                while (true) {
	                    System.out.print("Enter Email: ");
	                    email = sc.nextLine();

	                    if (isValidEmail(email)) {
	                        break;
	                    } else {
	                        System.out.println("\n Invalid email format! Example: user@gmail.com");
	                    }
	                }


	                System.out.print("Enter Password: ");
	                String password = sc.nextLine();

	                System.out.print("Enter Bio: ");
	                String bio = sc.nextLine();

	                System.out.print("Enter User Type (PERSONAL/CREATOR/SOCIAL): ");
	                String type = sc.nextLine();

	                System.out.print("Enter Profile Picture Path: ");
	                String pic = sc.nextLine();

	                System.out.print("Enter Location: ");
	                String location = sc.nextLine();

	                System.out.print("Enter Website: ");
	                String website = sc.nextLine();

	                User user = new User();
	                user.setName(name);
	                user.setEmail(email);
	                user.setPassword(password);
	                user.setBio(bio);
	                user.setUserType(type);
	                user.setProfilePic(pic);
	                user.setLocation(location);
	                user.setWebsite(website);

	                if (service.register(user)) {
	                    System.out.println("Registration Successful!");
	                    System.out.println("Please login to continue...\n");
	                    loginFlow(service, sc);
	                } else {
	                    System.out.println("Registration Failed!");
	                }
	                break;

	            case 2:
	                loginFlow(service, sc);
	                break;

	            case 3:
	                System.out.println("Exiting application!!");
	                System.exit(0);

	            default:
	                System.out.println("Invalid choice!!");
	        }
	    }
	}



    // ================= LOGIN FLOW =================

	private static void loginFlow(UserService service, Scanner sc) {
		
		System.out.println("\n==== LOGIN ====\n");

	    boolean loggedIn = false;

	    while (!loggedIn) {

	        System.out.print("Enter Email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter Password: ");
	        String password = sc.nextLine();

	        User loggedUser = service.login(email, password);

	        if (loggedUser != null) {
	            System.out.println("\n Login Successful!");
	            showProfileMenu(loggedUser, service, sc);
	            loggedIn = true;
	        } else {
	            System.out.println("Invalid email or password!");

	            System.out.println("1. Try Again");
	            System.out.println("2. Back to Main Menu");
	            System.out.print("Choose option: ");

	            String retry = sc.nextLine();

	            if (retry.equals("2")) {
	            	System.out.println("\n Returning to main menu...\n");
	                return; // go back to main menu
	            }
	        }
	    }
	}
	
	private static int getValidChoice(Scanner sc, int min, int max) {

	    while (true) {
	        String input = sc.nextLine();

	        if (!input.matches("\\d+")) {
	            System.out.print("Invalid input! Enter numbers only: ");
	            continue;
	        }

	        int choice = Integer.parseInt(input);

	        if (choice < min || choice > max) {
	            System.out.print("Please enter a number between " + min + " and " + max + ": ");
	            continue;
	        }

	        return choice;
	    }
	}
	
	private static boolean isValidEmail(String email) {
	    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	}



    // ================= DASHBOARD =================

    private static void showProfileMenu(User user, UserService service, Scanner sc) {

        PostService postService = new PostService();
        ConnectionService connectionService = new ConnectionService();
        NotificationService notificationService = new NotificationService();
        UserService userService = new UserService();

        
        

        while (true) {
        	int unread = notificationService.getUnreadCount(user.getUserId());
        	

        	System.out.println("\n==== USER DASHBOARD ====");
        	System.out.println("\n Notifications (" + unread + " unread) \n");


//            System.out.println("\n==== USER DASHBOARD ====");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Create Post");
            System.out.println("4. View My Posts");
            System.out.println("5. Delete Post");
            System.out.println("6. Like Post");
            System.out.println("7. Comment on Post");
            System.out.println("8. View Comments");
            System.out.println("9. View Like Count");
            System.out.println("10. Search User");
            System.out.println("11. Follow User");
            System.out.println("12. Accept Follow Request");
            System.out.println("13. View Followers");
            System.out.println("14. View Following");
            System.out.println("15. Unfollow User");
            System.out.println("16. View Notifications");
            System.out.println("17. Unlike Post");
            System.out.println("18. Delete Comment");
            System.out.println("19. Share Post");
            
            System.out.println("20. Logout");


            
            System.out.print("Choose option: ");

            int option = getValidChoice(sc, 1, 20);

            switch (option) {

                case 1:
                    User profile = service.getUserProfile(user.getUserId());
                    displayProfile(profile);
                    break;

                case 2:
                    editProfile(user, service, sc);
                    break;

                case 3:
                    createPost(user, postService, sc);
                    break;

                case 4:
                    viewMyPosts(user, postService);
                    break;
                    
                case 5:
                    deletePost(user, postService, sc);
                    break;
                    
                case 6:
                    likePost(user, sc);
                    break;

                case 7:
                    commentPost(user, sc);
                    break;
                    
                case 8:
                    viewComments(sc);
                    break;
                    
                case 9:
                    viewLikeCount(sc);
                    break;
                case 10:
                    searchUser(service, sc);
                    break;
                    
                case 11:
                	followUser(user, sc, connectionService, notificationService, userService);
                    break;

                case 12:
                    acceptFollow(user, sc, connectionService, notificationService,userService);
                    break;
                    
                case 13:
                    viewFollowers(user, connectionService);
                    break;

                case 14:
                    viewFollowing(user, connectionService);
                    break;

                case 15:
                    unfollowUser(user, sc, connectionService);
                    break;
                    
                case 16:
                    viewNotifications(user, notificationService);
                    break;
                    
                case 17:
                    unlikePost(user, sc);
                    break;
                    
                case 18:
                    deleteComment(user, sc);
                    break;
                    
                case 19:
                    sharePost(user, sc);
                    break;

                case 20:
                    System.out.println("\n Logged out successfully.");
                    return;        

                default:
                    System.out.println("\n Invalid option!");
            }
        }
    }

    // ================= CREATE POST =================

    private static void createPost(User user, PostService postService, Scanner sc) {

        System.out.println("\n==== CREATE POST ====");
        System.out.print("Enter post content: ");
        String content = sc.nextLine();
        
        System.out.print("Enter hashtags (comma separated or blank): ");
        String hashtags = sc.nextLine(); 

        Post post = new Post();
        post.setUserId(user.getUserId());
        post.setContent(content);
        post.setHashtags(hashtags);  

        boolean result = postService.createPost(post);

        if (result) {
            System.out.println("\n Post created successfully!");
        } else {
            System.out.println("\n Failed to create post!");
        }
    }

    // ================= VIEW POSTS =================

    private static void viewMyPosts(User user, PostService postService) {

        System.out.println("\n==== MY POSTS ====");

        var posts = postService.getUserPosts(user.getUserId());

        if (posts.isEmpty()) {
            System.out.println("No posts found.");
            return;
        }

        for (Post post : posts) {
            System.out.println("-------------------------");
            System.out.println("Post ID : " + post.getPostId());
            System.out.println("Content : " + post.getContent());
        }
    }

    // ================= PROFILE DISPLAY =================

    private static void displayProfile(User user) {
        System.out.println("\n===== USER PROFILE =====");
        System.out.println("User ID   : " + user.getUserId());
        System.out.println("Name      : " + user.getName());
        System.out.println("Email     : " + user.getEmail());
        System.out.println("Bio       : " + user.getBio());
        System.out.println("User Type : " + user.getUserType());
        System.out.println("========================");
    }

    // ================= EDIT PROFILE =================

    private static void editProfile(User user, UserService service, Scanner sc) {

        System.out.println("\n==== EDIT PROFILE ====");

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Bio: ");
        String bio = sc.nextLine();

        System.out.print("Enter User Type: ");
        String type = sc.nextLine();

        user.setName(name);
        user.setBio(bio);
        user.setUserType(type);

        boolean updated = service.updateProfile(user);

        if (updated) {
            System.out.println("\n Profile updated successfully!");
        } else {
            System.out.println("\n Failed to update profile!");
        }
    }
    
    // ================= DELETE PROFILE =================
    
    private static void deletePost(User user, PostService postService, Scanner sc) {

        System.out.println("\n==== DELETE POST ====");

        // Show user's posts first
        viewMyPosts(user, postService);

        System.out.print("\nEnter Post ID to delete: ");
        int postId = sc.nextInt();
        sc.nextLine();

        boolean deleted = postService.deletePost(postId, user.getUserId());

        if (deleted) {
            System.out.println("\n Post deleted successfully!");
        } else {
            System.out.println("\n Failed to delete post (Invalid ID or not your post)");
        }
    }
    
    private static void likePost(User user, Scanner sc) {

        System.out.print("Enter Post ID to like: ");
        int postId = sc.nextInt();

        LikeService likeService = new LikeService();
        PostService postService = new PostService();
        NotificationService notificationService = new NotificationService();

        if (likeService.likePost(postId, user.getUserId())) {

            int postOwnerId = postService.getPostOwnerId(postId);

            if (postOwnerId != user.getUserId()) {
            	notificationService.notifyUser(
            		    postOwnerId,
            		    "Your post got a new like",
            		    "LIKE"
            		);
            }

            System.out.println("\n Post liked successfully!");
        } else {
            System.out.println("\n You already liked this post.");
        }
    }

    
    private static void commentPost(User user, Scanner sc) {

        System.out.print("Enter Post ID: ");
        int postId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter comment: ");
        String comment = sc.nextLine();

        CommentService commentService = new CommentService();
        PostService postService = new PostService();
        NotificationService notificationService = new NotificationService();

        if (commentService.addComment(postId, user.getUserId(), comment)) {

            int postOwnerId = postService.getPostOwnerId(postId);

            if (postOwnerId != user.getUserId()) {
            	notificationService.notifyUser(
            		    postOwnerId,
            		    "Someone commented on your post",
            		    "COMMENT"
            		);
            }

            System.out.println("\n Comment added!");
        } else {
            System.out.println("\n Failed to add comment.");
        }
    }


    
    
    private static void viewComments(Scanner sc) {

        System.out.print("Enter Post ID: ");
        int postId = sc.nextInt();
        sc.nextLine();

        CommentService service = new CommentService();
        List<String> comments = service.getComments(postId);

        System.out.println("\n==== COMMENTS ====");

        if (comments.isEmpty()) {
            System.out.println("No comments yet.");
            return;
        }

        for (String comment : comments) {
            System.out.println("- " + comment);
        }
    }
    
    private static void viewLikeCount(Scanner sc) {

        System.out.print("Enter Post ID: ");
        int postId = sc.nextInt();
        sc.nextLine();

        LikeService service = new LikeService();
        int count = service.getLikeCount(postId);

        System.out.println("\n Total Likes: " + count);
    }

    private static void searchUser(UserService service, Scanner sc) {

        System.out.print("Enter name or email to search: ");
        String keyword = sc.nextLine();

        List<User> users = service.searchUsers(keyword);

        if (users.isEmpty()) {
            System.out.println("\n No users found.");
            return;
        } else {
            System.out.println("\n===== SEARCH RESULTS =====");
            for (User u : users) {
                System.out.println("ID       : " + u.getUserId());
                System.out.println("Name     : " + u.getName());
                System.out.println("Email    : " + u.getEmail());
                System.out.println("Bio      : " + u.getBio());
                System.out.println("Location : " + u.getLocation());
                System.out.println("Website  : " + u.getWebsite());
                System.out.println("----------------------------");
            }
        }
    }

    
    private static void followUser( User user, Scanner sc, ConnectionService service, NotificationService notificationService, UserService userService) {
    	
    	ConnectionService connectionService = new ConnectionService();
        System.out.print("Enter username to follow: ");
        String name = sc.nextLine();
        int targetUserId = userService.getUserIdByName(name);

        if (targetUserId == -1) {
            System.out.println("\n User not found.");
            return;
        }

        if (connectionService.followUser(user.getUserId(), targetUserId)) {

            System.out.println("\n Follow request sent!");

            notificationService.notifyUser(
                targetUserId,
                "You have a new follower!",
                "FOLLOW"
            );

        } else {
            System.out.println("\n Already following or request exists.");
        }
    }
    
    

    

    
    private static void acceptFollow(User user, Scanner sc, 
    		ConnectionService service,NotificationService notificationService,  UserService userService) {
    	
    	ConnectionService connectionService = new ConnectionService();
//    	System.out.println("\nPending Requests:");

    	List<Integer> pending = connectionService.getPendingRequests(user.getUserId());
    	
    	if (pending.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        System.out.println("\n Pending Requests:");
        for (int id : pending) {
            User u = userService.getUserProfile(id);
            System.out.println("User ID: " + id + " | Name: " + u.getName());
        }
        
        System.out.print("Enter User ID to accept: ");
        int followerId = sc.nextInt();

        // ✅ VALIDATE INPUT
        if (!pending.contains(followerId)) {
            System.out.println("\n Invalid user selection.");
            return;
        }
        
        boolean accepted = connectionService.acceptFollow(followerId, user.getUserId());

        if (accepted) {
            System.out.println("\n Follow request accepted!");

            notificationService.notifyUser(
                followerId,
                "Your follow request was accepted",
                "FOLLOW"
            );
        } else {
            System.out.println("\n Something went wrong.");
        }
        
    }
    
    private static void viewFollowers(User user, ConnectionService service) {

        List<User> followers = service.getFollowers(user.getUserId());

        if (followers.isEmpty()) {
            System.out.println("\n No followers yet.");
            return;
        }

        System.out.println("\n Your Followers (" + followers.size() + "):");

        for (User u : followers) {
            System.out.println("----------------------");
            System.out.println("ID    : " + u.getUserId());
            System.out.println("Name  : " + u.getName());
            System.out.println("Email : " + u.getEmail());
        }
    }


    
    private static void viewFollowing(User user, ConnectionService service) {

        List<User> following = service.getFollowing(user.getUserId());

        if (following.isEmpty()) {
            System.out.println("\n You are not following anyone.");
            return;
        }

        System.out.println("\n You are following (" + following.size() + "):");

        for (User u : following) {
            System.out.println("----------------------");
            System.out.println("ID    : " + u.getUserId());
            System.out.println("Name  : " + u.getName());
            System.out.println("Email : " + u.getEmail());
        }
    }


    private static void unfollowUser(User user, Scanner sc, ConnectionService service) {

        System.out.print("Enter User ID to unfollow: ");
        int unfollowId = sc.nextInt();

        if (service.unfollowUser(user.getUserId(), unfollowId)) {
            System.out.println("\n Unfollowed successfully.");
        } else {
            System.out.println("\n You are not following this user.");
        }
    }
    
    private static void viewNotifications(User user, NotificationService service) {

        List<String> notifications = service.getNotifications(user.getUserId());

        if (notifications.isEmpty()) {
            System.out.println("\n No notifications.");
        } else {
            System.out.println("\n Notifications:");
            for (String n : notifications) {
                System.out.println("- " + n);
            }
            service.markAsRead(user.getUserId());
        }
    }
    
    private static void unlikePost(User user, Scanner sc) {

        System.out.print("Enter Post ID to unlike: ");
        int postId = sc.nextInt();

        LikeService service = new LikeService();

        if (service.unlikePost(postId, user.getUserId())) {
            System.out.println("\n Post unliked successfully.");
        } else {
            System.out.println("\n You haven't liked this post.");
        }
    }

    
    private static void deleteComment(User user, Scanner sc) {

        System.out.print("Enter Comment ID to delete: ");
        int commentId = sc.nextInt();

        CommentService service = new CommentService();

        if (service.deleteComment(commentId, user.getUserId())) {
            System.out.println("\n Comment deleted.");
        } else {
            System.out.println("\n You can delete only your comments.");
        }
    }
    
    private static void sharePost(User user, Scanner sc) {

        System.out.print("Enter Post ID to share: ");
        int postId = sc.nextInt();

        PostService service = new PostService();

        if (service.sharePost(user.getUserId(), postId)) {
            System.out.println("\n Post shared successfully!");
        } else {
            System.out.println("\n Failed to share post.");
        }
    }

}
