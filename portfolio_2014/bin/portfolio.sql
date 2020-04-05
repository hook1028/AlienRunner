create table userdata(
	nickname varchar2(20),
	password varchar2(20) not null,
	height number(3),
	goal number(3)
);

drop table userdata;
select * from userdata;
delete from userdata;


create table weight(
	day date constraint weight_day_PK primary key,
	weight number(3) not null
);
create table bust(
	day date constraint bust_day_PK primary key,
	bust number(3) not null
);
create table waist(
	day date constraint waist_day_PK primary key,
	waist number(3) not null
);
create table thigh(
	day date constraint thigh_day_PK primary key,
	thigh number(3) not null
);

select * from weight;
select * from waist;
select * from bust;
select * from thigh;

drop table weight;
drop table bust;
drop table waist;
drop table thigh;

delete from userdata;
delete from weight;
delete from bust;
delete from waist;
delete from thigh;


create table diet(
	food varchar2(30) constraint diet_food_PK primary key,
	mainly varchar2(10) not null
	);

drop table diet;

create table breakfast(
	day date,
	food varchar2(30) not null,
	mainly varchar2(10) not null
	);
	
drop table breakfast;
	
create table lunch(
	day date,
	food varchar2(30) not null,
	mainly varchar2(10) not null
	);

create table dinner(
	day date,
	food varchar2(30) not null,
	mainly varchar2(10) not null
	);
	
create table snack(
	day date,
	food varchar2(30) not null,
	mainly varchar2(10) not null
	);
	
create table exercise(
	exercise varchar2(30) constraint exercise_exercise_PK primary key,
	cal number,
	extype varchar2(10) not null
	);
	
create table exerciserecord(
	day date,
	exercise varchar2(30) not null,
	exnum number,
	cal number,
	extype varchar2(10) not null
	);
	
drop table exerciserecord;


