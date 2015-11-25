create database if not exists bright_box_db;


CREATE TABLE if not exists `bright_box_db`.`bright_box` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `identifier` VARCHAR(45) NULL,
  UNIQUE INDEX `identifier_UNIQUE` (`identifier` ASC),
  PRIMARY KEY (`id`));
  
CREATE TABLE if not exists `bright_box_db`.`sensor_data` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fk_bright_box` INT NOT NULL,
  `timestamp` DATETIME NOT NULL,
  `bloom` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  `vega` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  `grow` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  `air_temperatur` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  `humidity` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  `ph_value` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  `ec_value` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  `water_temperatur` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`),
  INDEX `fk_bright_box_idx` (`fk_bright_box` ASC),
  CONSTRAINT `fk_bb`
    FOREIGN KEY (`fk_bright_box`)
    REFERENCES `bright_box_db`.`bright_box` (`id`)
    on update cascade
    on delete cascade);