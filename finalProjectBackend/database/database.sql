-- MySQL Script generated by MySQL Workbench
-- Wed Nov 23 23:45:02 2016
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `password` VARCHAR(128) NULL,
  `email` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Restaurant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`restaurant` ;

CREATE TABLE IF NOT EXISTS `mydb`.`restaurant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(256) NULL,
  `address` VARCHAR(45) NULL,
  `max_capacity` VARCHAR(45) NULL,
  `enabled` TINYINT(1) NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_restaurant_user` FOREIGN KEY (`user_id`) REFERENCES `mydb`.`user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`comment` ;

CREATE TABLE IF NOT EXISTS `mydb`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `text` VARCHAR(256) NULL,
  `date` VARCHAR(45) NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `mydb`.`user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`review` ;

CREATE TABLE IF NOT EXISTS `mydb`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `restaurant_id` INT NOT NULL,
  `user_id` INT NULL,
  `description` VARCHAR(256) NULL,
  `comment_id` INT DEFAULT NULL,
  `date` VARCHAR(45) NULL,
  `stars` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Review_Restaurant`  FOREIGN KEY (`restaurant_id`) REFERENCES `mydb`.`restaurant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Review_user` FOREIGN KEY (`user_id`) REFERENCES `mydb`.`user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Review_Comment` FOREIGN KEY (`comment_id`) REFERENCES `mydb`.`comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;