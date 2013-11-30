# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admin (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  passwd                    varchar(255),
  email                     varchar(255),
  constraint pk_admin primary key (id))
;

create table comment (
  id                        bigint auto_increment not null,
  user_email                varchar(255),
  create_time               datetime,
  content                   varchar(255),
  comment_to_id             bigint,
  constraint pk_comment primary key (id))
;

create table distance (
  id                        bigint auto_increment not null,
  walk_duration             float,
  drive_duration            float,
  constraint pk_distance primary key (id))
;

create table hpicture (
  id                        bigint auto_increment not null,
  house_pic_url             varchar(255),
  house_pic_title           varchar(255),
  house_id                  bigint,
  constraint pk_hpicture primary key (id))
;

create table house (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  owner_email               varchar(255),
  house_type                varchar(255),
  size                      float,
  address_line1             varchar(255),
  address_line2             varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  zip_code                  varchar(255),
  price                     float,
  leasing_type              varchar(255),
  neighbor                  varchar(255),
  requirements              varchar(255),
  rate                      double,
  totoal_rates              integer,
  availability              integer,
  updated_time              datetime,
  area                      varchar(255),
  services_id               bigint,
  constraint pk_house primary key (id))
;

create table house_provider (
  email                     varchar(255) not null,
  description               varchar(255),
  lname                     varchar(255),
  fname                     varchar(255),
  provider_type             varchar(255),
  occupation                varchar(255),
  verification_doc          varchar(255),
  address_line1             varchar(255),
  address_line2             varchar(255),
  city                      varchar(255),
  states                    varchar(255),
  zip_code                  varchar(255),
  constraint pk_house_provider primary key (email))
;

create table institute (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  address_line1             varchar(255),
  address_line2             varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  zip_code                  varchar(255),
  constraint pk_institute primary key (id))
;

create table message (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  content                   varchar(255),
  create_time               datetime,
  sender_email              varchar(255),
  receiver_email            varchar(255),
  sender_status             integer,
  receiver_status           integer,
  read_status               integer,
  constraint pk_message primary key (id))
;

create table service (
  id                        bigint auto_increment not null,
  water                     integer,
  gas                       integer,
  heat                      integer,
  internet                  integer,
  fitness                   integer,
  laundry                   integer,
  parking                   integer,
  constraint pk_service primary key (id))
;

create table transportation (
  id                        bigint auto_increment not null,
  line                      varchar(255),
  constraint pk_transportation primary key (id))
;

create table users (
  email                     varchar(255) not null,
  name                      varchar(255),
  passwd                    varchar(255),
  phone                     varchar(255),
  is_house_provider         integer,
  neighbor                  varchar(255),
  profile_image             varchar(255),
  preferred_type            varchar(255),
  constraint pk_users primary key (email))
;


create table house_transportation (
  house_id                       bigint not null,
  transportation_id              bigint not null,
  constraint pk_house_transportation primary key (house_id, transportation_id))
;

create table follows (
  users_email                    varchar(255) not null,
  follows_email                  varchar(255) not null,
  constraint pk_follows primary key (users_email, follows_email))
;

create table users_house (
  users_email                    varchar(255) not null,
  house_id                       bigint not null,
  constraint pk_users_house primary key (users_email, house_id))
;
alter table comment add constraint fk_comment_user_1 foreign key (user_email) references users (email) on delete restrict on update restrict;
create index ix_comment_user_1 on comment (user_email);
alter table comment add constraint fk_comment_commentTo_2 foreign key (comment_to_id) references house (id) on delete restrict on update restrict;
create index ix_comment_commentTo_2 on comment (comment_to_id);
alter table hpicture add constraint fk_hpicture_house_3 foreign key (house_id) references house (id) on delete restrict on update restrict;
create index ix_hpicture_house_3 on hpicture (house_id);
alter table house add constraint fk_house_owner_4 foreign key (owner_email) references house_provider (email) on delete restrict on update restrict;
create index ix_house_owner_4 on house (owner_email);
alter table house add constraint fk_house_services_5 foreign key (services_id) references service (id) on delete restrict on update restrict;
create index ix_house_services_5 on house (services_id);
alter table message add constraint fk_message_sender_6 foreign key (sender_email) references users (email) on delete restrict on update restrict;
create index ix_message_sender_6 on message (sender_email);
alter table message add constraint fk_message_receiver_7 foreign key (receiver_email) references users (email) on delete restrict on update restrict;
create index ix_message_receiver_7 on message (receiver_email);



alter table house_transportation add constraint fk_house_transportation_house_01 foreign key (house_id) references house (id) on delete restrict on update restrict;

alter table house_transportation add constraint fk_house_transportation_transportation_02 foreign key (transportation_id) references transportation (id) on delete restrict on update restrict;

alter table follows add constraint fk_follows_users_01 foreign key (users_email) references users (email) on delete restrict on update restrict;

alter table follows add constraint fk_follows_users_02 foreign key (follows_email) references users (email) on delete restrict on update restrict;

alter table users_house add constraint fk_users_house_users_01 foreign key (users_email) references users (email) on delete restrict on update restrict;

alter table users_house add constraint fk_users_house_house_02 foreign key (house_id) references house (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table admin;

drop table comment;

drop table distance;

drop table hpicture;

drop table house;

drop table house_transportation;

drop table house_provider;

drop table institute;

drop table message;

drop table service;

drop table transportation;

drop table users;

drop table follows;

drop table users_house;

SET FOREIGN_KEY_CHECKS=1;

