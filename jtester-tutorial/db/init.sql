CREATE TABLE `phone_group` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `description` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name` (`group_name`)
)ENGINE=InnoDB
AUTO_INCREMENT=2 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';


CREATE TABLE `phone_item` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `mobile` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `mobile2` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `family_phone` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `gender` TINYINT(1) DEFAULT NULL,
  `company` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `title` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `mail` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=3 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';


CREATE TABLE `phone_item_group` (
  `item_id` INTEGER(11) NOT NULL,
  `group_id` INTEGER(11) NOT NULL,
  PRIMARY KEY (`item_id`, `group_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';