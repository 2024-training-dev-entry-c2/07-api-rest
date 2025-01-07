CREATE TABLE IF NOT EXISTS `restaurant`.`CUSTOMERS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NULL,
  `email` VARCHAR(64) NULL,
  `type` VARCHAR(16) NULL,
  `age` TINYINT(1) NULL,
  `phone` VARCHAR(32) NULL,
  `address` VARCHAR(128) NULL,
  `is_active` TINYINT NULL,
  `created_at` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);