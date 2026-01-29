# RevConnect – ER Diagram

```mermaid
erDiagram

    USERS {
        int user_id PK
        string name
        string email
        string password
        string bio
        string user_type
        string profile_pic
        string location
        string website
        datetime created_at
    }

    POSTS {
        int post_id PK
        int user_id FK
        string content
        string hashtags
        datetime created_at
    }

    COMMENTS {
        int comment_id PK
        int post_id FK
        int user_id FK
        string comment
        datetime created_at
    }

    POST_LIKES {
        int like_id PK
        int post_id FK
        int user_id FK
        datetime liked_at
    }

    CONNECTIONS {
        int connection_id PK
        int follower_id FK
        int following_id FK
        string status
        datetime created_at
    }

    NOTIFICATIONS {
        int notification_id PK
        int user_id FK
        string message
        string category
        boolean is_read
        datetime created_at
    }

    NOTIFICATION_PREFERENCES {
        int user_id PK
        boolean follow_enabled
        boolean like_enabled
        boolean comment_enabled
    }

    USERS ||--o{ POSTS : creates
    USERS ||--o{ COMMENTS : writes
    USERS ||--o{ POST_LIKES : likes
    USERS ||--o{ CONNECTIONS : follows
    USERS ||--o{ NOTIFICATIONS : receives
    USERS ||--|| NOTIFICATION_PREFERENCES : has
    POSTS ||--o{ COMMENTS : contains
    POSTS ||--o{ POST_LIKES : receives
