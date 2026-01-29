┌────────────────────┐
│        USERS        │
├────────────────────┤
│ user_id (PK)        │
│ name                │
│ email (UNIQUE)      │
│ password            │
│ bio                 │
│ user_type           │
│ profile_pic         │
│ location            │
│ website             │
│ created_at          │
└─────────┬──────────┘
          │
          │ 1
          │
          │ M
┌─────────▼──────────┐
│        POSTS        │
├────────────────────┤
│ post_id (PK)        │
│ user_id (FK)        │
│ content             │
│ hashtags            │
│ original_post_id    │
│ created_at          │
└─────────┬──────────┘
          │
   ┌──────┴───────┐
   │              │
   │              │
   ▼              ▼
┌────────────┐   ┌────────────┐
│  COMMENTS   │   │ POST_LIKES │
├────────────┤   ├────────────┤
│ comment_id │   │ like_id     │
│ post_id FK │   │ post_id FK  │
│ user_id FK │   │ user_id FK  │
│ comment    │   │ liked_at    │
│ created_at │   └────────────┘
└────────────┘


┌────────────────────┐
│    CONNECTIONS     │
├────────────────────┤
│ connection_id (PK) │
│ follower_id (FK)   │
│ following_id (FK)  │
│ status             │
│ created_at         │
└─────────┬──────────┘
          │
          │
          ▼
┌────────────────────────┐
│     NOTIFICATIONS       │
├────────────────────────┤
│ notification_id (PK)   │
│ user_id (FK)           │
│ message                │
│ category               │
│ is_read                │
│ created_at             │
└─────────┬──────────────┘
          │
          ▼
┌────────────────────────────┐
│ NOTIFICATION_PREFERENCES   │
├────────────────────────────┤
│ user_id (PK, FK)           │
│ follow_enabled             │
│ like_enabled               │
│ comment_enabled            │
└────────────────────────────┘
