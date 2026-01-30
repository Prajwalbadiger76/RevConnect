🛠️ RevConnect – Setup Instructions

GitHub Repository:
🔗 https://github.com/Prajwalbadiger76/RevConnect.git

📌 Project Overview

RevConnect is a console-based social media application built using:

Java (Core + JDBC)

Oracle Database

JUnit Testing

Log4j Logging

It supports:

User registration & login

Post creation

Like / Comment

Follow system

Notifications

Profile management

✅ Prerequisites

Make sure you have the following installed:

Tool	Required
Java	JDK 8 or above
IDE	Eclipse / STS
Database	Oracle XE
JDBC Driver	ojdbc6.jar
Git	Optional
📥 Step 1: Clone the Project
git clone https://github.com/Prajwalbadiger76/RevConnect.git


OR
Download ZIP → Extract → Open in Eclipse

🧩 Step 2: Import Project into Eclipse

Open Eclipse / STS

Click File → Import

Select Existing Projects into Workspace

Browse the extracted folder

Click Finish

🔌 Step 3: Add JDBC Driver

Right-click project → Build Path

Click Configure Build Path

Go to Libraries

Click Add External JAR

Select:

ojdbc6.jar


Apply & Close

✅ Add to Classpath (NOT Modulepath)

🗄️ Step 4: Database Setup
Create User
CREATE USER revconnect IDENTIFIED BY revconnect;
GRANT CONNECT, RESOURCE TO revconnect;

Create Tables
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

CREATE TABLE posts (
    post_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    content VARCHAR2(1000),
    hashtags VARCHAR2(200),
    original_post_id NUMBER,
    created_at DATE DEFAULT SYSDATE
);

CREATE TABLE comments (
    comment_id NUMBER PRIMARY KEY,
    post_id NUMBER,
    user_id NUMBER,
    comment_text VARCHAR2(500),
    commented_at DATE DEFAULT SYSDATE
);

CREATE TABLE post_likes (
    like_id NUMBER PRIMARY KEY,
    post_id NUMBER,
    user_id NUMBER,
    liked_at DATE DEFAULT SYSDATE
);

CREATE TABLE connections (
    connection_id NUMBER PRIMARY KEY,
    follower_id NUMBER,
    following_id NUMBER,
    status VARCHAR2(20),
    created_at DATE DEFAULT SYSDATE
);

CREATE TABLE notifications (
    notification_id NUMBER PRIMARY KEY,
    user_id NUMBER,
    message VARCHAR2(255),
    category VARCHAR2(50),
    is_read CHAR(1),
    created_at DATE DEFAULT SYSDATE
);

CREATE TABLE notification_preferences (
    user_id NUMBER PRIMARY KEY,
    follow_enabled CHAR(1),
    like_enabled CHAR(1),
    comment_enabled CHAR(1)
);

⚙️ Step 5: Configure Database Connection

📁 File: DBConnection.java

public class DBConnection {
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:XE",
            "revconnect",
            "revconnect"
        );
    }
}

▶ Step 6: Run the Application

Open:

MainApp.java


Right-click → Run As → Java Application

Console menu appears:

==== RevConnect ====
1. Register
2. Login
3. Exit

🧪 Step 7: Run JUnit Tests

📁 Path:

src/test/java/com/revconnect/test


Run:

UserServiceTest

PostServiceTest

ConnectionServiceTest

NotificationServiceTest

✔ Right-click → Run as → JUnit Test
