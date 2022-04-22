# TeachMeSkills homework Lesson 28

## Sweater `SOLUTION IN PROGRESS`

--- 

## Table of contents

### 1. [Task](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_28_Sweater#task)

### 2. [Database creation script](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_28_Sweater#database-creation-script)

### 3. [Database structure](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_28_Sweater#database-structure)

### 4. [Current view](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_28_Sweater#current-view)

--- 

### Task

> Create an analogue of a social network.

---

### Database creation script

```sql
CREATE DATABASE social_network;
USE social_network;

CREATE TABLE `comments`
(
    `id`        int           NOT NULL,
    `post_id`   int           NOT NULL,
    `author_id` int           NOT NULL,
    `content`   varchar(2048) NOT NULL,
    `timestamp` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `posts`
(
    `id`        int       NOT NULL,
    `author_id` int            DEFAULT NULL,
    `content`   varchar(2048)  DEFAULT NULL,
    `likes`     varchar(1024)  DEFAULT NULL,
    `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `roles`
(
    `id`           int NOT NULL,
    `name`         varchar(32) DEFAULT NULL,
    `access_level` int         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `users`
(
    `id`       int NOT NULL,
    `login`    varchar(32) DEFAULT NULL,
    `password` varchar(32) DEFAULT NULL,
    `email`    varchar(64) DEFAULT NULL,
    `name`     varchar(32) DEFAULT NULL,
    `surname`  varchar(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles`
(
    `user_id` int NOT NULL,
    `role_id` int NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
```

### Database structure

![database-structure](screens/database-structure.png)

### Current view

#### Home without authorization

![home-without-authorization](screens/home-without-authorization.png)

#### Registration

![registration](screens/registration.png)

#### Registration mail to owner

![registration-mail-to-owner](screens/registration-mail-to-owner.png)

#### Authorization

![authorization](screens/authorization.png)

#### Home page for common user

![common-user-home](screens/common-user-home.png)

#### Home page for administrator

![admin-user-home](screens/admin-user-home.png)

#### New Tweet page

![tweet-page](screens/new-tweet-page.png)

#### My Tweets page

![my-tweets](screens/my-tweets-page.png)

#### Post edit page

![post-edit](screens/post-edit-page.png)

#### All Tweets page

![all-tweets](screens/all-tweets-page.png)

#### Tweet page

![tweet-page](screens/tweet-page.png)

#### Admin panel

![admin-panel](screens/admin-panel.png)

![in-progress](screens/in-progress.png)