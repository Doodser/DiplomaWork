CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `is_moderator` tinyint NOT NULL,
                         `reg_time` datetime NOT NULL,
                         `name` varchar(255) NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `code` varchar(255) DEFAULT NULL,
                         `photo` text,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `posts` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `is_active` tinyint NOT NULL,
                         `moderation_status` enum('NEW','ACCEPTED','DECLINED') NOT NULL,
                         `moderator_id` int DEFAULT NULL,
                         `user_id` int NOT NULL,
                         `time` datetime NOT NULL,
                         `title` varchar(255) NOT NULL,
                         `text` text NOT NULL,
                         `view_count` int NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post_votes` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `user_id` int NOT NULL,
                              `post_id` int NOT NULL,
                              `time` datetime NOT NULL,
                              `value` tinyint NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tags` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tag2post` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `post_id` int NOT NULL,
                            `tag_id` int NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post_comments` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `parent_id` int DEFAULT NULL,
                                 `post_id` int NOT NULL,
                                 `user_id` int NOT NULL,
                                 `time` datetime NOT NULL,
                                 `text` text NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `captcha_codes` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `time` datetime NOT NULL,
                                 `code` tinytext NOT NULL,
                                 `secret_code` tinytext NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `global_settings` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `code` varchar(255) NOT NULL,
                                   `name` varchar(255) NOT NULL,
                                   `value` varchar(255) NOT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
