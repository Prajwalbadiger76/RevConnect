
REVCONNECT – SETUP INSTRUCTIONS
===============================

GitHub Repository:
https://github.com/Prajwalbadiger76/RevConnect

--------------------------------
1. SYSTEM REQUIREMENTS
--------------------------------
• Java JDK 8 or above
• Oracle XE Database
• Eclipse / STS IDE
• ojdbc6.jar
• Git (optional)

--------------------------------
2. CLONE THE PROJECT
--------------------------------
git clone https://github.com/Prajwalbadiger76/RevConnect.git

--------------------------------
3. IMPORT PROJECT IN ECLIPSE
--------------------------------
1. Open Eclipse
2. File → Import
3. Existing Projects into Workspace
4. Select project folder
5. Finish

--------------------------------
4. ADD JDBC DRIVER
--------------------------------
1. Right click project
2. Build Path → Configure Build Path
3. Libraries → Add External JAR
4. Select ojdbc6.jar
5. Apply and Close

(Note: Add to Classpath, NOT Modulepath)

--------------------------------
5. DATABASE SETUP
--------------------------------

Create User:
---------------
CREATE USER revconnect IDENTIFIED BY revconnect;
GRANT CONNECT, RESOURCE TO revconnect;

--------------------------------
Create Tables:
--------------------------------

USERS
-----
CREATE TABLE users (
    user_id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    email VARCHAR2(100) UNIQUE,
    password VARCHAR2(255),
    bio VARCHAR2(300),
    user_type VARCHAR2(20),
    profile_pic VARCHAR2(200),
    location VARCHAR2(100),
    website VARCHAR2(100),
    created_at DATE DEFAULT SYSDATE
);

POSTS
-----
CREATE TABLE posts (
    post_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    content VARCHAR2(1000),
    hashtags VARCHAR2(200),
    original_post_id NUMBER,
    created_at DATE DEFAULT SYSDATE
);

COMMENTS
--------
CREATE TABLE comments (
    comment_id NUMBER PRIMARY KEY,
    post_id NUMBER,
    user_id NUMBER,
    comment_text VARCHAR2(500),
    commented_at DATE DEFAULT SYSDATE
);

LIKES
-----
CREATE TABLE post_likes (
    like_id NUMBER PRIMARY KEY,
    post_id NUMBER,
    user_id NUMBER,
    liked_at DATE DEFAULT SYSDATE
);

CONNECTIONS
-----------
CREATE TABLE connections (
    connection_id NUMBER PRIMARY KEY,
    follower_id NUMBER,
    following_id NUMBER,
    status VARCHAR2(20),
    created_at DATE DEFAULT SYSDATE
);

NOTIFICATIONS
-------------
CREATE TABLE notifications (
    notification_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    message VARCHAR2(255),
    category VARCHAR2(50),
    is_read CHAR(1),
    created_at DATE DEFAULT SYSDATE
);

NOTIFICATION PREFERENCES
------------------------
CREATE TABLE notification_preferences (
    user_id NUMBER PRIMARY KEY,
    follow_enabled CHAR(1),
    like_enabled CHAR(1),
    comment_enabled CHAR(1)
);

--------------------------------
6. DATABASE CONNECTION
--------------------------------
File: DBConnection.java

Update credentials:

jdbc:oracle:thin:@localhost:1521:XE
Username: revconnect
Password: revconnect

--------------------------------
7. RUN APPLICATION
--------------------------------
1. Open MainApp.java
2. Right click → Run As → Java Application
3. Choose:
   1 → Register
   2 → Login

--------------------------------
8. RUN JUNIT TESTS
--------------------------------
Path:
src/test/java/com/revconnect/test

Right click test file → Run As → JUnit Test

--------------------------------
9. FEATURES IMPLEMENTED
--------------------------------
✔ User Registration & Login  
✔ Profile Management  
✔ Post Creation  
✔ Like & Comment  
✔ Follow / Unfollow  
✔ Notifications  
✔ Search Users  
✔ JUnit Testing  

--------------------------------
10. NOTES
--------------------------------
• Use valid email while registering
• Database must be running before app execution
• Do not delete triggers or sequences
• Notifications appear after follow/like/comment actions

--------------------------------
END OF SETUP
--------------------------------
