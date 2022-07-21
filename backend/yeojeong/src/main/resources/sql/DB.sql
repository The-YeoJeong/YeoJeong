drop table traveler.img_file;
drop table traveler.heart;
drop table traveler.comment;
drop table traveler.travel_post_schedulecard;
drop table traveler.travel_post_datecard;
drop table traveler.travel_post_region;
drop table traveler.travel_post;
drop table traveler.member;
drop table traveler.region;

create table traveler.member(
	member_no int auto_increment primary key,
    member_id varchar(10) unique key not null,
    member_pw char(60) not null,
    member_nickname varchar(20) unique key not null,
    member_oauth_key varchar(65) unique key null,
    created_time datetime not null,
    updated_time datetime not null,
    created_by varchar(10) not null,
    updated_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.travel_post(
	post_no int auto_increment primary key,
    member_no int not null,
    foreign key(member_no) references traveler.member(member_no)
    on delete cascade,
    post_title varchar(40) not null,
    post_startdate date not null,
    post_enddate date not null,
    post_content text null,
    post_heart_cnt int not null default 0,
    post_onlyme boolean,
    created_time datetime not null,
    updated_time datetime not null,
    created_by varchar(10) not null,
    updated_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.region(
	region_no char(2) primary key,
    region_name char(4) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.travel_post_region(
	post_region_no int auto_increment primary key,
    post_no int not null,
    foreign key(post_no) references traveler.travel_post(post_no)
    on delete cascade,
    region_no char(2) not null,
    foreign key(region_no) references traveler.region(region_no)
    on update cascade,
    created_time datetime not null,
    updated_time datetime not null,
    created_by varchar(10) not null,
    updated_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.travel_post_datecard(
	post_datecard_no int auto_increment primary key,
    post_no int not null,
    foreign key(post_no) references traveler.travel_post(post_no)
    on delete cascade,
    post_datecard_title varchar(20),
    created_time datetime not null,
    updated_time datetime not null,
    created_by varchar(10) not null,
    updated_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.travel_post_schedulecard(
	post_schedulecard_no int auto_increment primary key,
    post_datecard_no int not null,
    foreign key(post_datecard_no) references traveler.travel_post_datecard(post_datecard_no)
    on delete cascade,
    post_schedulecard_place_name varchar(30) not null,
    post_schedulecard_place_address varchar(100) not null,
    post_schedulecard_content varchar(200) not null,
    created_time datetime not null,
    updated_time datetime not null,
    created_by varchar(10) not null,
    updated_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.comment(
	comment_no int auto_increment primary key,
    post_no int not null,
    foreign key(post_no) references traveler.travel_post(post_no)
    on delete cascade,
    member_no int not null,
    foreign key(member_no) references traveler.member(member_no)
    on delete cascade,
    comment_content varchar(100) not null,
    created_time datetime not null,
    updated_time datetime not null,
    created_by varchar(10) not null,
    updated_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.heart(
	heart_no int auto_increment primary key,
    post_no int not null,
    foreign key(post_no) references traveler.travel_post(post_no)
    on delete cascade,
    member_no int not null,
    foreign key(member_no) references traveler.member(member_no)
    on delete cascade,
    created_time datetime not null,
    created_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

create table traveler.img_file(
	file_no int auto_increment primary key,
    post_no int not null,
    foreign key(post_no) references traveler.travel_post(post_no)
    on delete cascade,
    file_name varchar(255) not null,
    file_path varchar(255) not null,
    file_save_name varchar(255) not null,
    file_size int not null,
    created_time datetime not null,
    created_by varchar(10) not null
)DEFAULT CHARACTER SET = utf8mb4;

insert into traveler.region values('00','전국');
insert into traveler.region values('11','서울');
insert into traveler.region values('21','부산');
insert into traveler.region values('22','대구');
insert into traveler.region values('23','인천');
insert into traveler.region values('24','광주');
insert into traveler.region values('25','대전');
insert into traveler.region values('26','울산');
insert into traveler.region values('29','세종');
insert into traveler.region values('31','경기');
insert into traveler.region values('32','강원');
insert into traveler.region values('33','충북');
insert into traveler.region values('34','충남');
insert into traveler.region values('35','전북');
insert into traveler.region values('36','전남');
insert into traveler.region values('37','경북');
insert into traveler.region values('38','경남');
insert into traveler.region values('39','제주');

CREATE TABLE IF NOT EXISTS `traveler`.`authority` (
  `authority_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`authority_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

insert into traveler.authority values('ROLE_USER');

CREATE TABLE IF NOT EXISTS `traveler`.`member_authority` (
  `member_no` int NOT NULL,
  `authority_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`member_no`, `authority_name`),
    FOREIGN KEY (`authority_name`)
    REFERENCES `traveler`.`authority` (`authority_name`),
    FOREIGN KEY (`member_no`)
    REFERENCES `traveler`.`member` (`member_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;