CREATE TABLE IF NOT EXISTS `it1869`.`authority` (
  `authority_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`authority_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `it1869`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS it1869.member (
	member_no int auto_increment not null,
    member_id varchar(10) unique key not null,
    member_pw char(60) not null,
    member_nickname varchar(20) unique key not null,
    member_oauth_key varchar(65) unique key null,
    created_time datetime,
    updated_time datetime,
    created_by int,
    updated_by int,
    PRIMARY KEY (`member_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `it1869`.`user_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `it1869`.`member_authority` (
  `member_no` int NOT NULL,
  `authority_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`member_no`, `authority_name`),
    FOREIGN KEY (`authority_name`)
    REFERENCES `it1869`.`authority` (`authority_name`),
    FOREIGN KEY (`member_no`)
    REFERENCES `it1869`.`member` (`member_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;