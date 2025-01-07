CREATE TABLE IF NOT EXISTS `restaurant`.`DISHES` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NULL,
  `type` VARCHAR(16) NULL,
  `price` DECIMAL(10,2) NULL,
  `menu_id` INT NOT NULL,
  PRIMARY KEY (`id`, `menu_id`),
  INDEX `fk_DISHES_MENUS1_idx` (`menu_id` ASC) VISIBLE,
  CONSTRAINT `fk_DISHES_MENUS1`
    FOREIGN KEY (`menu_id`)
    REFERENCES `restaurant`.`MENUS` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;