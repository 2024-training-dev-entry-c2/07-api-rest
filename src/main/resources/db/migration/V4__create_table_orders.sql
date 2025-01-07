CREATE TABLE IF NOT EXISTS `restaurant`.`ORDERS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total` DECIMAL(10,2) NOT NULL,
  `discount` TINYINT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`id`, `customer_id`),
  INDEX `fk_ORDERS_customer_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_ORDERS_CUSTOMERS`
    FOREIGN KEY (`customer_id`)
    REFERENCES `restaurant`.`CUSTOMERS` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
