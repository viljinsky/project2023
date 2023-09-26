
-- Типы уроков
create table lesson_type (
    lesson_type_id integer not null primary key,
    lesson_type_name varchar(18)
);

-- список дней в сетке расписания
create table day_list (
    day_id integer primary key,
    day_short_name varchar(3),
    day_name varchar(10)
);

-- Статус расписания

create table schedule_state (
    schedule_state_id integer primary key not null,
    schedule_state_name varchar(40)
);

-- Тип группы;
create table group_type(
    group_type_id integer primary key,
    group_type_name varchar(40)
);

-- Недели 
create table week(
    week_id integer primary key ,             -- 0 каждую неделю; 1,2,.. 1-ая.2-я .. неделя
    week_name varchar(10)
);


-- список часов в сетке расписания

create table bell_list (
    bell_id integer primary key,
    time_start time,
    time_end time
);

-- Тип графика
create table shift_type(
    shift_type_id integer not null primary key,
    shift_type_name varchar(45),
    shift_id integer,  -- график по умолчанию
    foreign key(shift_id) references shift(shift_id)
);

-- Графики
create table shift(
    shift_id integer primary key autoincrement,
    shift_type_id integer references shift_type(shift_type_id),
    shift_name varchar(18) not null,
    unique (shift_type_id,shift_name)
);

create table shift_detail(
    shift_id integer references shift(shift_id) on delete cascade,
    day_id integer references day_list(day_id) on delete cascade,
    bell_id integer references bell_list(bell_id) on delete cascade,
    enable boolean default 1,
    primary key (shift_id,day_id,bell_id),
    unique (shift_id,day_id,bell_id)
);

-- Ограничения 

create table condition(
    condition_id int not null primary key,
    condition_name varchar(40) not null unique,
    condition_label varchar(40)
);


-- Предметная область
create table subject_domain(
    subject_domain_id integer primary key autoincrement,
    subject_domain_name varchr(40) not null unique
);

create table subject(
    subject_id integer primary key autoincrement,
    subject_name varchar(20) not null unique,
    hour_per_week integer default 2 ,
    group_type_id integer references group_type(group_type_id) default 0,
    hour_per_day integer default 1 ,
    subject_domain_id integer not null references subject_domain(subject_domain_id) on delete cascade,
    color varchar(11) default '240 240 240',
    difficulty integer default null,
    sort_order integer default 0
);

-- Тип профилей ;

create table profile_type (
    profile_type_id integer primary key,
    profile_type_name varchar(45),
    profile_id integer ,
    foreign key (profile_id) references profile(profile_id)
);

-- профили (специализация)

create table profile(
    profile_id integer primary key autoincrement,
    profile_type_id integer references profile_type(profile_type_id),
    profile_name varchar(18) not null,
    unique(profile_type_id,profile_name)
);

create table profile_item(
    profile_id integer references profile(profile_id) on delete cascade,
    subject_id integer references subject(subject_id) on delete cascade,
    primary key (profile_id,subject_id)
);

-- Регулярность проведения занятий

create table group_sequence (
    group_sequence_id integer primary key ,
    group_sequence_name varchar(20)
);

-- Уровеь обучения
create table skill(
    skill_id integer primary key autoincrement,
    sort_order integer not null default 0,
    skill_name varchar(18) not null unique
);

-- Здания (корпуса учебного заведения)
create table building(
    building_id integer primary key autoincrement,
    building_name varchar(20) not null unique
);

-- помещения 
create table room(
    room_id integer primary key autoincrement,
    room_name varchar(18) not null,
    capacity integer,
    building_id integer not null references building(building_id),
    profile_id integer not null references profile(profile_id),
    shift_id integer not null references shift(shift_id),
    multy int default 0,
    constraint room_uq unique (room_name, building_id) 
);

-- Список преподавателей
create table teacher(
    teacher_id integer primary key autoincrement,
    last_name varchar(18) not null,
    first_name varchar(18) not null,
    patronymic varchar(18) not null,
    photo binary,
    profile_id integer not null references profile(profile_id),
    shift_id integer  not null references shift(shift_id),
    room_id  integer references room(room_id) on delete set null,
    comments blob
);

create table depart(
    depart_id integer primary key autoincrement,
    depart_label varchar(10) not null unique,
    skill_id integer not null references skill(skill_id) on delete cascade,
    shift_id integer not null references shift(shift_id),
    curriculum_id integer not null references curriculum(curriculum_id),
    room_id integer  references room(room_id), -- классное помещение
    teacher_id integer references teacher(teacher_id), -- классный руководитель
    boy_count integer,
    gerl_count integer,
    schedule_state_id integer not null references schedule_state(schedule_state_id) default 0 
);

-- Учебный план
create table curriculum(
    curriculum_id integer primary key autoincrement,
    curriculum_name varchar(18) not null unique
);

-- Детали учебного плана
create table curriculum_detail(
    curriculum_id integer references curriculum(curriculum_id) on delete cascade,
    skill_id integer references skill(skill_id) on delete cascade,
    subject_id integer references subject(subject_id) on delete cascade on update cascade,
    hour_per_day integer not null,
    hour_per_week integer not null,
    difficulty integer default null,
    group_type_id integer not null default 0 references group_type(group_type_id),
    group_sequence_id integer default 0 references group_sequence(group_sequence_id),
    lesson_type_id integer not null default 0 references lesson_type(lesson_type_id),
    constraint pk_cur_det primary key (curriculum_id,skill_id,subject_id),
    constraint check_hour check (hour_per_week>=hour_per_day)    
);

-- Ограничения

create table curriculum_condition (
    curriculum_id int not null references curriculum(curriculum_id) on delete cascade,
    skill_id int not null references skill(skill_id) on delete cascade,
    subject_id int not null references subject(subject_id) on delete cascade,
    condition_id int not null references condition(condition_id),
    constraint pk_condition_detail primary key (curriculum_id,skill_id,subject_id,condition_id),
    constraint fk_condition_detail_curriculum_detail foreign key (curriculum_id,skill_id,subject_id) 
        references curriculum_detail(curriculum_id,skill_id,subject_id) on delete cascade
);

-- потоки
create table stream (
    stream_id integer primary key autoincrement,
    stream_name varchar(40),
    subject_id integer not null references subject(subject_id) on delete cascade,
    skill_id integer not null references skill(skill_id) on delete cascade,
    room_id integer references room(room_id) on delete set null,
    teacher_id integer references teacher(teacher_id)  on delete set null
);


create table subject_group (
    group_id integer,
    depart_id integer references depart(depart_id) on delete cascade,
    subject_id integer references subject(subject_id) on delete restrict,
    skill_id integer not null,
    curriculum_id integer not null,
    teacher_id integer references teacher(teacher_id) on delete set null,
    room_id integer references room(room_id) on delete set null,
    week_id integer references week(week_id) default 0,
    stream_id integer references stream(stream_id) on delete set null,
    pupil_count integer,
    primary key (depart_id,subject_id,group_id),
    foreign key (skill_id,curriculum_id,subject_id) 
    references curriculum_detail(skill_id,curriculum_id,subject_id) on delete cascade
);

-- Расписание
create table schedule (
    day_id integer references day_list(day_id) on delete restrict,
    bell_id integer references bell_list(bell_id) on delete restrict,
    depart_id integer references depart(depart_id) on delete cascade,
    subject_id integer references subject(subject_id),
    group_id integer,
    teacher_id integer references teacher(teacher_id) on delete set null,
    room_id integer references room(room_id) on delete set null,
    ready boolean default 0, -- false
    primary key (day_id,bell_id,depart_id,subject_id,group_id),
    foreign key (depart_id,subject_id,group_id) references subject_group(depart_id,subject_id,group_id) on delete restrict,
    foreign key (day_id) references day_list(day_id),
    foreign key (bell_id) references bell_list(bell_id)
);

create table user_role(
	id integer primary key,
	role_name varchar(45) not null unique
);

create table users (
	id integer primary key autoincrement,
        user_name varchar(18) not null unique,
	password  varchar(45),
	nick_name varchar(45) not null,
	user_role_id integer not null references user_role(id)	
);

create table attr (
    param_name varchar(40) not null unique,
    param_value varchar(40)
);

create table holiday(
  holiday_id integer not null primary key autoincrement,
  holiday_date date not null unique,
  instead_date date,
  holiday_name varchar(45),
  comments varchar(45)  
);

-- флаг замещений отмена - замещение
create table journal_flag (flag Integer not null primary key,
    flag_name varchar,count_value int);


create table journal (
    journal_id integer not null primary key autoincrement,
    create_time datetime not null default current_timestamp,
    teacher_id integer references teacher(teacher_id),
    date_begin date not null,
    date_end date default null,
    first_lesson integer default null,
    last_lesson integer default null,
    flag integer not null default 0,
    comments text,
    parent_id integer default null references journal(journal_id) on delete cascade,
    holiday_id integer default null references holiday(holiday_id) on delete cascade
);

create table journal_detail(
    journal_id integer not null references journal(journal_id) on delete cascade,
    detail_id integer not null,
    date date not null,
    day_id integer,
    bell_id integer,
    depart_id integer,
    group_id integer,
    subject_id integer,
    teacher_id integer,
    room_id integer,
    primary key (journal_id,detail_id),
    unique(journal_id,date,bell_id,depart_id,group_id,subject_id,teacher_id),
    foreign key (depart_id,group_id,subject_id)
    references subject_group(depart_id,group_id,subject_id) on delete cascade
);

create table journal_scope (
    journal_id integer not null references journal(journal_id) on delete cascade,
    depart_id integer not null references depart(depart_id) on delete cascade,
    primary key (journal_id,depart_id)
);


create table subject_condition (
    subject_id integer not null references subject(subject_id) on delete cascade,
    condition_id integer not null references subject(subject_id) on delete cascade,
    constraint pk_subject_condition primary key (subject_id,condition_id ),
    constraint check_subject check(subject_id!=condition_id)
);
