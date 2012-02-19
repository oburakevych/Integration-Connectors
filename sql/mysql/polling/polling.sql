CREATE DATABASE `polling` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
use `polling`;

CREATE TABLE `user_profile` (
   `company_account_id` VARCHAR(255) NOT NULL,
   `country` VARCHAR(255),
   PRIMARY KEY (`company_account_id`)
) ENGINE=InnoDB;

CREATE TABLE `plugin_usage_info` (
   `company_account_id` VARCHAR(255) NOT NULL,
   `activated` TIMESTAMP,
   `disabled` TIMESTAMP,
   PRIMARY KEY (`company_account_id`)
) ENGINE=InnoDB;

CREATE TABLE `user_feedback` (
    `company_account_id` VARCHAR(255) NOT NULL,
    `feedback_pg` VARCHAR(255),
    `feedback_pg_int` VARCHAR(255),
    `feedback_txt` VARCHAR(1024),
    PRIMARY KEY (`company_account_id`)
) ENGINE=InnoDB;



