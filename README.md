# REVCONNECT – CONSOLE BASED SOCIAL NETWORKING APPLICATION

RevConnect is a console-based social networking application built using Java, JDBC, and Oracle Database.
It allows users to register, login, create posts, follow users, like and comment on posts, and receive notifications — all through a clean console-based interface.

This project is designed to demonstrate:

* Core Java concepts
* JDBC connectivity
* Proper code structure
* Authentication and authorization
* Exception handling
* Real-world application flow

---

## FEATURES

AUTHENTICATION & PROFILE

* User registration with email and password
* Login validation
* View and edit profile
* Profile fields:

  * Name
  * Bio
  * Profile picture path
  * Location
  * Website
* Secure password storage using SHA-256 hashing

POST MANAGEMENT

* Create posts
* View own posts
* Delete posts
* Share posts
* View likes and comments
* Hashtag support (optional)

SOCIAL INTERACTIONS

* Like posts
* Unlike posts
* Comment on posts
* Delete own comments
* View all comments on a post

CONNECTION SYSTEM

* Send follow request
* Accept follow request
* View followers
* View following
* Unfollow users

NOTIFICATION SYSTEM

* Follow notifications
* Like notifications
* Comment notifications
* Share notifications
* Unread notification count
* Mark notifications as read

TESTING

* JUnit test cases included
* Tests for:

  * UserService
  * PostService
  * ConnectionService
  * NotificationService

---

## TECHNOLOGIES USED

Java
JDBC
Oracle Database
JUnit 5
Log4j
SHA-256 (Password hashing)
Eclipse / STS

---

## DATABASE SETUP

1. Create required tables in Oracle:

   * users
   * posts
   * comments
   * connections
   * notifications
   * notification_preferences

2. Update database credentials in:
   DBConnection.java

   url      → jdbc:oracle:thin:@localhost:1521:XE
   username → your_db_username
   password → your_db_password

---

## HOW TO RUN THE PROJECT

1. Open project in Eclipse / STS
2. Add required JAR files:

   * ojdbc8.jar
   * log4j-api
   * log4j-core
   * junit-jupiter
3. Run:
   MainApp.java

---

## RUNNING TEST CASES

Right click on:
src/test/java
→ Run As → JUnit Test

All major services are tested.

---

## PASSWORD SECURITY

* Passwords are hashed using SHA-256
* Plain passwords are never stored
* Hashing is applied during:

  * Registration
  * Login verification

---

## HIGHLIGHTS

✔ Clean MVC architecture
✔ Secure authentication
✔ Proper exception handling
✔ Modular code
✔ Real-world logic
✔ Console-based UI
✔ Notification system
✔ Database integrity

---

## DEVELOPER

Name : Prajwal Badiger
Role : Java Backend Developer
Skills : Java, JDBC, SQL, Backend Development

---

## FUTURE ENHANCEMENTS

* Convert to Spring Boot
* REST API implementation
* Web UI (React)
* JWT authentication
* WebSocket notifications
* Docker deployment

