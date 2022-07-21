-- -----------------------------------------------------
-- Table `it1869`.`authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `it1869`.`authority` (
  `authority_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`authority_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `it1869`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `it1869`.`user` (
  `userid` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `it1869`.`user_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `it1869`.`user_authority` (
  `userid` VARCHAR(50) NOT NULL,
  `authority_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`userid`, `authority_name`),
  INDEX `FK6ktglpl5mjosa283rvken2py5` (`authority_name` ASC) VISIBLE,
  CONSTRAINT `FK6ktglpl5mjosa283rvken2py5`
    FOREIGN KEY (`authority_name`)
    REFERENCES `it1869`.`authority` (`authority_name`),
  CONSTRAINT `FKfsgxlx33p4e4nhscbhl1jptiq`
    FOREIGN KEY (`userid`)
    REFERENCES `it1869`.`user` (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;
