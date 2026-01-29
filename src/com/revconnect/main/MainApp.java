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

        System.out.println("==== RevConnect ====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            System.out.print("Enter Bio: ");
            String bio = sc.nextLine();

            System.out.print("Enter User Type(PERSONAL/CREATOR/SOCIAL): ");
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
                System.out.println("✅ Registration Successful!");
                System.out.println("\n👉 Please login to continue...\n");
                loginFlow(service, sc);
            } else {
                System.out.println("❌ Registration Failed!");
            }

        } else if (choice == 2) {
            loginFlow(service, sc);
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

                System.out.println("1. Try Again");
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

    // ================= DASHBOARD =================

    private static void showProfileMenu(User user, UserService service, Scanner sc) {

        PostService postService = new PostService();
        ConnectionService connectionService = new ConnectionService();
        NotificationService notificationService = new NotificationService();
        
        boolean running = true;

        while (running) {
        	int unread = notificationService.getUnreadCount(user.getUserId());

        	System.out.println("\n==== USER DASHBOARD ====");
        	System.out.println("\n🔔 Notifications (" + unread + " unread) \n");


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

            int option = sc.nextInt();
            sc.nextLine();

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
                    followUser(user, sc, connectionService, notificationService);
                    break;

                case 12:
                    acceptFollow(user, sc, connectionService);
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
                    System.out.println("👋 Logged out successfully.");
                    running = false;
                    break;
                 

                default:
                    System.out.println("❌ Invalid option!");
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
            System.out.println("✅ Post created successfully!");
        } else {
            System.out.println("❌ Failed to create post!");
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
            System.out.println("✅ Profile updated successfully!");
        } else {
            System.out.println("❌ Failed to update profile!");
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
            System.out.println("✅ Post deleted successfully!");
        } else {
            System.out.println("❌ Failed to delete post (Invalid ID or not your post)");
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

            System.out.println("✅ Post liked successfully!");
        } else {
            System.out.println("⚠️ You already liked this post.");
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

            System.out.println("✅ Comment added!");
        } else {
            System.out.println("❌ Failed to add comment.");
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

        System.out.println("👍 Total Likes: " + count);
    }

    private static void searchUser(UserService service, Scanner sc) {

        System.out.print("Enter name or email to search: ");
        String keyword = sc.nextLine();

        List<User> users = service.searchUsers(keyword);

        if (users.isEmpty()) {
            System.out.println("❌ No users found.");
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

    
    private static void followUser( User user, Scanner sc, ConnectionService service, NotificationService notificationService) {

        System.out.print("Enter User ID to follow: ");
        int targetUserId = sc.nextInt();

        if (service.followUser(user.getUserId(), targetUserId)) {

            System.out.println("✅ Follow request sent!");

            notificationService.notifyUser(
            	    targetUserId,
            	    "You have a new follower",
            	    "FOLLOW"
            	);

        } else {
            System.out.println("❌ Already following or invalid user.");
        }
    }

    

    
    private static void acceptFollow(User user, Scanner sc, ConnectionService service) {

        List<Integer> pending = service.getPendingRequests(user.getUserId());

        if (pending.isEmpty()) {
            System.out.println("❌ No pending requests.");
            return;
        }

        System.out.println("Pending Requests:");
        for (int id : pending) {
            System.out.println("User ID: " + id);
        }

        System.out.print("Enter User ID to accept: ");
        int followerId = sc.nextInt();

        if (service.acceptFollow(followerId, user.getUserId())) {
            System.out.println("✅ Follow request accepted!");
        } else {
            System.out.println("❌ Invalid request.");
        }
    }
    
    private static void viewFollowers(User user, ConnectionService service) {

        List<User> followers = service.getFollowers(user.getUserId());

        if (followers.isEmpty()) {
            System.out.println("❌ No followers yet.");
        } else {
            System.out.println("👥 Your Followers (" + followers.size() + "):");

            for (User u : followers) {
                System.out.println("----------------------");
                System.out.println("ID    : " + u.getUserId());
                System.out.println("Name  : " + u.getName());
                System.out.println("Email : " + u.getEmail());
            }
        }
    }

    
    private static void viewFollowing(User user, ConnectionService service) {

        List<User> following = service.getFollowing(user.getUserId());

        if (following.isEmpty()) {
            System.out.println("❌ You are not following anyone.");
        } else {
            System.out.println("➡ You are following (" + following.size() + "):");

            for (User u : following) {
                System.out.println("----------------------");
                System.out.println("ID    : " + u.getUserId());
                System.out.println("Name  : " + u.getName());
                System.out.println("Email : " + u.getEmail());
            }
        }
    }

    private static void unfollowUser(User user, Scanner sc, ConnectionService service) {

        System.out.print("Enter User ID to unfollow: ");
        int unfollowId = sc.nextInt();

        if (service.unfollowUser(user.getUserId(), unfollowId)) {
            System.out.println("✅ Unfollowed successfully.");
        } else {
            System.out.println("❌ You are not following this user.");
        }
    }
    
    private static void viewNotifications(User user, NotificationService service) {

        List<String> notifications = service.getNotifications(user.getUserId());

        if (notifications.isEmpty()) {
            System.out.println("🔔 No notifications.");
        } else {
            System.out.println("🔔 Notifications:");
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
            System.out.println("✅ Post unliked successfully.");
        } else {
            System.out.println("❌ You haven't liked this post.");
        }
    }

    
    private static void deleteComment(User user, Scanner sc) {

        System.out.print("Enter Comment ID to delete: ");
        int commentId = sc.nextInt();

        CommentService service = new CommentService();

        if (service.deleteComment(commentId, user.getUserId())) {
            System.out.println("✅ Comment deleted.");
        } else {
            System.out.println("❌ You can delete only your comments.");
        }
    }
    
    private static void sharePost(User user, Scanner sc) {

        System.out.print("Enter Post ID to share: ");
        int postId = sc.nextInt();

        PostService service = new PostService();

        if (service.sharePost(user.getUserId(), postId)) {
            System.out.println("🔁 Post shared successfully!");
        } else {
            System.out.println("❌ Failed to share post.");
        }
    }



}
