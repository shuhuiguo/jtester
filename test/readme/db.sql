--use db presentationtdd

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `presentationtdd`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `presentationtdd`;

#
# Structure for the `another` table : 
#

CREATE TABLE `another` (
  `id` INTEGER(11) NOT NULL,
  `name` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL,
  `age` INTEGER(11) DEFAULT NULL,
  `field3` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `field3` (`field3`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `char_encoding_test` table : 
#

CREATE TABLE `char_encoding_test` (
  `message` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `dbmaintain_scripts` table : 
#

CREATE TABLE `dbmaintain_scripts` (
  `file_name` VARCHAR(150) COLLATE utf8_general_ci DEFAULT NULL,
  `version` VARCHAR(25) COLLATE utf8_general_ci DEFAULT NULL,
  `file_last_modified_at` BIGINT(20) DEFAULT NULL,
  `checksum` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL,
  `executed_at` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `succeeded` BIGINT(20) DEFAULT NULL
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `jtester_address` table : 
#

CREATE TABLE `jtester_address` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `create_date` DATETIME DEFAULT NULL,
  `creator` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `modified_date` DATETIME DEFAULT NULL,
  `modifior` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `is_deleted` BIT(1) DEFAULT NULL,
  `address` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `country` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `city` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `postcode` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `province` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4313875E892CE089` (`user_id`)
)ENGINE=InnoDB
AUTO_INCREMENT=2 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `jtester_user` table : 
#

CREATE TABLE `jtester_user` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `create_date` DATETIME DEFAULT NULL,
  `creator` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `modified_date` DATETIME DEFAULT NULL,
  `modifior` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `is_deleted` BIT(1) DEFAULT NULL,
  `name` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `email` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=44 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `tdd_user` table : 
#

CREATE TABLE `tdd_user` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `last_name` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `post_code` CHAR(6) COLLATE utf8_general_ci DEFAULT NULL,
  `sarary` DOUBLE(15,3) DEFAULT NULL,
  `address_id` INTEGER(11) DEFAULT NULL,
  `my_date` DATETIME DEFAULT NULL,
  `big_int` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=5 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';


CREATE TABLE `foreign_test` (
  `id` INTEGER(11) NOT NULL,
  `f_id` INTEGER(11) DEFAULT NULL,
  `not_null` INTEGER(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `f_id` (`f_id`),
  CONSTRAINT `foreign_test_fk` FOREIGN KEY (`f_id`) REFERENCES `tdd_user` (`id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';