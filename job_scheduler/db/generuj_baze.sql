-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema db_scheduler
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `db_scheduler` ;

-- -----------------------------------------------------
-- Schema db_scheduler
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_scheduler` DEFAULT CHARACTER SET utf8 ;
USE `db_scheduler` ;

-- -----------------------------------------------------
-- Table `db_scheduler`.`GROUPS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`GROUPS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`GROUPS` (
  `GROUP_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`GROUP_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`PRODUCTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`PRODUCTS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`PRODUCTS` (
  `PRODUCT_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NULL,
  `DESCRIPTION` VARCHAR(64) NULL,
  `ATTRIBUTE_1` VARCHAR(64) NULL,
  `ATTRIBUTE_2` VARCHAR(64) NULL,
  `ATTRIBUTE_3` VARCHAR(64) NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GROUP_ID` INT NOT NULL,
  PRIMARY KEY (`PRODUCT_ID`),
  INDEX `fk_PRODUCTS_GROUPS1_idx` (`GROUP_ID` ASC),
  CONSTRAINT `fk_PRODUCTS_GROUPS1`
    FOREIGN KEY (`GROUP_ID`)
    REFERENCES `db_scheduler`.`GROUPS` (`GROUP_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`RESOURCE_TYPES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`RESOURCE_TYPES` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`RESOURCE_TYPES` (
  `RESOURCE_TYPE_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GROUP_ID` INT NOT NULL,
  PRIMARY KEY (`RESOURCE_TYPE_ID`),
  INDEX `fk_RESOURCE_TYPES_GROUPS1_idx` (`GROUP_ID` ASC),
  CONSTRAINT `fk_RESOURCE_TYPES_GROUPS1`
    FOREIGN KEY (`GROUP_ID`)
    REFERENCES `db_scheduler`.`GROUPS` (`GROUP_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`PRODUCT_OPERATIONS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`PRODUCT_OPERATIONS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`PRODUCT_OPERATIONS` (
  `PRODUCT_OPERATION_ID` INT NOT NULL AUTO_INCREMENT,
  `OPERATION_NUMBER` INT NULL,
  `NAME` VARCHAR(64) NULL,
  `DESCRIPTION` VARCHAR(64) NULL,
  `DURATION` INT NULL,
  `PREPARATION_DURATION` INT NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PRODUCT_ID` INT NOT NULL,
  `RESOURCE_TYPE_ID` INT NOT NULL,
  PRIMARY KEY (`PRODUCT_OPERATION_ID`),
  INDEX `fk_PRODUCT_OPERATIONS_PRODUCTS_idx` (`PRODUCT_ID` ASC),
  INDEX `fk_PRODUCT_OPERATIONS_RESOURCE_TYPES1_idx` (`RESOURCE_TYPE_ID` ASC),
  CONSTRAINT `fk_PRODUCT_OPERATIONS_PRODUCTS`
    FOREIGN KEY (`PRODUCT_ID`)
    REFERENCES `db_scheduler`.`PRODUCTS` (`PRODUCT_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_PRODUCT_OPERATIONS_RESOURCE_TYPES1`
    FOREIGN KEY (`RESOURCE_TYPE_ID`)
    REFERENCES `db_scheduler`.`RESOURCE_TYPES` (`RESOURCE_TYPE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`RESOURCES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`RESOURCES` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`RESOURCES` (
  `RESOURCE_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NULL,
  `DESCRIPTION` VARCHAR(64) NULL,
  `COST_PER_HOUR` INT NULL,
  `EFFICIENCY` INT NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `RESOURCE_TYPE_ID` INT NOT NULL,
  PRIMARY KEY (`RESOURCE_ID`),
  INDEX `fk_RESOURCES_RESOURCE_TYPES1_idx` (`RESOURCE_TYPE_ID` ASC),
  CONSTRAINT `fk_RESOURCES_RESOURCE_TYPES1`
    FOREIGN KEY (`RESOURCE_TYPE_ID`)
    REFERENCES `db_scheduler`.`RESOURCE_TYPES` (`RESOURCE_TYPE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`USERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`USERS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`USERS` (
  `USER_ID` INT NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(64) NULL,
  `PASSWORD` VARCHAR(64) NULL,
  `FIRST_NAME` VARCHAR(64) NULL,
  `LAST_NAME` VARCHAR(64) NULL,
  `EMAIL` VARCHAR(64) NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ENABLED` TINYINT(4) NOT NULL,
  `GROUP_ID` INT NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE INDEX `USERNAME_UNIQUE` (`USERNAME` ASC),
  INDEX `fk_USERS_GROUPS1_idx` (`GROUP_ID` ASC),
  CONSTRAINT `fk_USERS_GROUPS1`
    FOREIGN KEY (`GROUP_ID`)
    REFERENCES `db_scheduler`.`GROUPS` (`GROUP_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`PERSISTENT_LOGINS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`PERSISTENT_LOGINS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`PERSISTENT_LOGINS` (
  `USERNAME` VARCHAR(64) NOT NULL,
  `SERIES` VARCHAR(64) NOT NULL,
  `TOKEN` VARCHAR(64) NOT NULL,
  `LAST_USED` TIMESTAMP NOT NULL,
  PRIMARY KEY (`SERIES`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`USER_ROLES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`USER_ROLES` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`USER_ROLES` (
  `USER_ROLE_ID` INT NOT NULL AUTO_INCREMENT,
  `ROLE` VARCHAR(64) NULL,
  `ROLE_DESCRIPTION` VARCHAR(45) NULL,
  `USER_ID` INT NOT NULL,
  PRIMARY KEY (`USER_ROLE_ID`),
  INDEX `fk_USER_ROLES_USERS1_idx` (`USER_ID` ASC),
  CONSTRAINT `fk_USER_ROLES_USERS1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `db_scheduler`.`USERS` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`USER_GROUPS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`USER_GROUPS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`USER_GROUPS` (
  `GROUP_ID` INT NOT NULL,
  `USER_ID` INT NOT NULL,
  INDEX `fk_USER_GROUPS_GROUPS1_idx` (`GROUP_ID` ASC),
  PRIMARY KEY (`GROUP_ID`, `USER_ID`),
  INDEX `fk_USER_GROUPS_USERS1_idx` (`USER_ID` ASC),
  CONSTRAINT `fk_USER_GROUPS_GROUPS1`
    FOREIGN KEY (`GROUP_ID`)
    REFERENCES `db_scheduler`.`GROUPS` (`GROUP_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_GROUPS_USERS1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `db_scheduler`.`USERS` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`PRODUCTION_PLANS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`PRODUCTION_PLANS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`PRODUCTION_PLANS` (
  `PRODUCTION_PLAN_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NULL,
  `START` DATETIME NULL,
  `END` DATETIME NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GROUP_ID` INT NOT NULL,
  PRIMARY KEY (`PRODUCTION_PLAN_ID`),
  INDEX `fk_PRODUCTION_PLANS_GROUPS1_idx` (`GROUP_ID` ASC),
  CONSTRAINT `fk_PRODUCTION_PLANS_GROUPS1`
    FOREIGN KEY (`GROUP_ID`)
    REFERENCES `db_scheduler`.`GROUPS` (`GROUP_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`ORDERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`ORDERS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`ORDERS` (
  `ORDER_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NULL,
  `DESCRIPTION` VARCHAR(64) NULL,
  `DUE_DATE` DATETIME NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PRODUCTION_PLAN_ID` INT NULL,
  `GROUP_ID` INT NOT NULL,
  PRIMARY KEY (`ORDER_ID`),
  INDEX `fk_ORDERS_PRODUCTION_PLANS1_idx` (`PRODUCTION_PLAN_ID` ASC),
  INDEX `fk_ORDERS_GROUPS1_idx` (`GROUP_ID` ASC),
  CONSTRAINT `fk_ORDERS_PRODUCTION_PLANS1`
    FOREIGN KEY (`PRODUCTION_PLAN_ID`)
    REFERENCES `db_scheduler`.`PRODUCTION_PLANS` (`PRODUCTION_PLAN_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDERS_GROUPS1`
    FOREIGN KEY (`GROUP_ID`)
    REFERENCES `db_scheduler`.`GROUPS` (`GROUP_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`ORDER_PRODUCTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`ORDER_PRODUCTS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`ORDER_PRODUCTS` (
  `ORDER_ID` INT NOT NULL,
  `PRODUCT_ID` INT NOT NULL,
  `AMOUNT` INT NULL,
  INDEX `fk_ORDER_PRODUCTS_ORDERS1_idx` (`ORDER_ID` ASC),
  INDEX `fk_ORDER_PRODUCTS_PRODUCTS1_idx` (`PRODUCT_ID` ASC),
  PRIMARY KEY (`ORDER_ID`, `PRODUCT_ID`),
  CONSTRAINT `fk_ORDER_PRODUCTS_ORDERS1`
    FOREIGN KEY (`ORDER_ID`)
    REFERENCES `db_scheduler`.`ORDERS` (`ORDER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDER_PRODUCTS_PRODUCTS1`
    FOREIGN KEY (`PRODUCT_ID`)
    REFERENCES `db_scheduler`.`PRODUCTS` (`PRODUCT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scheduler`.`ITEMS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_scheduler`.`ITEMS` ;

CREATE TABLE IF NOT EXISTS `db_scheduler`.`ITEMS` (
  `ITEM_ID` INT NOT NULL AUTO_INCREMENT,
  `START` DATETIME NULL,
  `END` DATETIME NULL,
  `PREPARATION_START` DATETIME NULL,
  `CREATED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `EDITED_ON` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PRODUCTION_PLAN_ID` INT NOT NULL,
  `RESOURCE_ID` INT NOT NULL,
  PRIMARY KEY (`ITEM_ID`),
  INDEX `fk_ITEMS_PRODUCTION_PLANS1_idx` (`PRODUCTION_PLAN_ID` ASC),
  INDEX `fk_ITEMS_RESOURCES1_idx` (`RESOURCE_ID` ASC),
  CONSTRAINT `fk_ITEMS_PRODUCTION_PLANS1`
    FOREIGN KEY (`PRODUCTION_PLAN_ID`)
    REFERENCES `db_scheduler`.`PRODUCTION_PLANS` (`PRODUCTION_PLAN_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_ITEMS_RESOURCES1`
    FOREIGN KEY (`RESOURCE_ID`)
    REFERENCES `db_scheduler`.`RESOURCES` (`RESOURCE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
