drop table if exists shift_type;
drop table if exists shift_detail;
drop table if exists shift;
drop table if exists pare;
drop table if exists pare_detail;
drop table if exists day_list;
drop table if exists bell_list;

CREATE TABLE "day_list" (
	"day_id"	INTEGER UNIQUE,
	"day_name"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("day_id" AUTOINCREMENT)
);


CREATE TABLE "bell_list" (
	"bell_id"	INTEGER NOT NULL,
	"time_start"	TEXT,
	"time_end"	TEXT,
	PRIMARY KEY("bell_id" AUTOINCREMENT)
);

CREATE TABLE "shift_type" (
	"shift_type_id"	INTEGER NOT NULL,
	"shift_type_name"	TEXT NOT NULL,
	PRIMARY KEY("shift_type_id")
);

CREATE TABLE "shift" (
	"shift_id"	INTEGER,
	"shift_type_id"	INTEGER,
	"shift_name"	TEXT NOT NULL,
	UNIQUE("shift_type_id","shift_name"),
	PRIMARY KEY("shift_id" AUTOINCREMENT)
);

create table shift_detail (
    shift_id integer,day_id integer,
    bell_id integer, primary key (shift_id,day_id,bell_id),
    FOREIGN KEY (shift_id) REFERENCES shift,
    FOREIGN KEY (day_id) references day_list,
    FOREIGN key (bell_id) REFERENCES bell_list );

CREATE TABLE "pare" (
	"pare_id"	INTEGER NOT NULL,
	"pare_name"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("pare_id" AUTOINCREMENT)
);

CREATE TABLE "pare_detail" (
	"pare_id"	INTEGER,
	"day_id"	INTEGER,
	"bell_id"	INTEGER,
	FOREIGN KEY("pare_id") REFERENCES "pare",
	FOREIGN KEY("day_id") REFERENCES "day_list",
	FOREIGN KEY("bell_id") REFERENCES "bell_list",
	PRIMARY KEY("pare_id","day_id","bell_id")
);
insert into day_list(day_id,day_name) values(1,'Понедельник');
insert into day_list(day_id,day_name) values	(2,'вторник');
insert into day_list(day_id,day_name) values	(3,'среда');
insert into day_list(day_id,day_name) values	(4,'четверг');
insert into day_list(day_id,day_name) values	(5,'пятница');
insert into day_list(day_id,day_name) values	(6,'суббота');
insert into day_list(day_id,day_name) values	(7,'воскресение');


insert INTO bell_list (bell_id,time_start,time_end) values(1,'10:00','10,30');
insert INTO bell_list (bell_id,time_start,time_end) values	(2,'11:00','11,30');
insert INTO bell_list (bell_id,time_start,time_end) values	(3,'12:00','12,30');
insert INTO bell_list (bell_id,time_start,time_end) values	(4,'13:00','10,30');
insert INTO bell_list (bell_id,time_start,time_end) values	(5,'14:00','14,30');
insert INTO bell_list (bell_id,time_start,time_end) values	(6,'15:00','15,30');
insert INTO bell_list (bell_id,time_start,time_end) values	(7,'16:00','16,30');
insert INTO bell_list (bell_id,time_start,time_end) values	(8,'17:00','17,30');
	

insert into shift_type(shift_type_id,shift_type_name) values (1,'расписание');
insert into shift_type(shift_type_id,shift_type_name) values (2,'преподаватели');
insert into shift_type(shift_type_id,shift_type_name) values (3,'помещения');

insert into shift(shift_id,shift_type_id,shift_name) values(1,1,'Первая смена');
insert into shift(shift_id,shift_type_id,shift_name) values(2,2,'График преподователя');
insert into shift(shift_id,shift_type_id,shift_name) values(3,3,'График помещения');

insert into shift_detail(shift_id,day_id,bell_id) values (1,1,1);
insert into shift_detail(shift_id,day_id,bell_id) values(1,1,2);
insert into shift_detail(shift_id,day_id,bell_id) values(1,1,3);
insert into shift_detail(shift_id,day_id,bell_id) values(1,1,4);
insert into shift_detail(shift_id,day_id,bell_id) values(1,1,5);
insert into shift_detail(shift_id,day_id,bell_id) values(1,1,6);

insert into shift_detail(shift_id,day_id,bell_id) values (1,2,1);
insert into shift_detail(shift_id,day_id,bell_id) values(1,2,2);
insert into shift_detail(shift_id,day_id,bell_id) values(1,2,3);
insert into shift_detail(shift_id,day_id,bell_id) values(1,2,4);
insert into shift_detail(shift_id,day_id,bell_id) values(1,2,5);
insert into shift_detail(shift_id,day_id,bell_id) values(1,2,6);

insert into shift_detail(shift_id,day_id,bell_id) values (1,3,1);
insert into shift_detail(shift_id,day_id,bell_id) values(1,3,2);
insert into shift_detail(shift_id,day_id,bell_id) values(1,3,3);
insert into shift_detail(shift_id,day_id,bell_id) values(1,3,4);
insert into shift_detail(shift_id,day_id,bell_id) values(1,3,5);
insert into shift_detail(shift_id,day_id,bell_id) values(1,3,6);

insert into shift_detail(shift_id,day_id,bell_id) values (1,4,1);
insert into shift_detail(shift_id,day_id,bell_id) values(1,4,2);
insert into shift_detail(shift_id,day_id,bell_id) values(1,4,3);
insert into shift_detail(shift_id,day_id,bell_id) values(1,4,4);
insert into shift_detail(shift_id,day_id,bell_id) values(1,4,5);
insert into shift_detail(shift_id,day_id,bell_id) values(1,4,6);

insert into shift_detail(shift_id,day_id,bell_id) values (1,5,1);
insert into shift_detail(shift_id,day_id,bell_id) values(1,5,2);
insert into shift_detail(shift_id,day_id,bell_id) values(1,5,3);
insert into shift_detail(shift_id,day_id,bell_id) values(1,5,4);
insert into shift_detail(shift_id,day_id,bell_id) values(1,5,5);
insert into shift_detail(shift_id,day_id,bell_id) values(1,5,6);

insert into pare(pare_id,pare_name) values (1,'1-я пара');
insert into pare(pare_id,pare_name) values (2,'2-я пара');
insert into pare(pare_id,pare_name) values (3,'3-я пара');
insert into pare(pare_id,pare_name) values (4,'4-я пара');
insert into pare(pare_id,pare_name) values (5,'5-я пара');

--create table pare_detail(pare_id,day_id,bell_id);
insert into pare_detail (pare_id,bell_id) values(1,1);
insert into pare_detail (pare_id,bell_id) values(1,2);
insert into pare_detail (pare_id,bell_id) values(2,3);
insert into pare_detail (pare_id,bell_id) values(2,4);
insert into pare_detail (pare_id,bell_id) values(3,5);
insert into pare_detail (pare_id,bell_id) values(3,6);


-- create table test (f1 integer);
-- insert into test(f1) values (234);
-- insert into test(f1) values (234.2);
-- insert into test(f1) values (date());
-- insert into test(f1) values (time());
-- insert into test(f1) values (datetime());