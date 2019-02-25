drop schema if exists cdit;
create schema cdit;
use cdit;

create table user_table(
name varchar(20) not null,
salary float not null,
CONSTRAINT user_table_PK PRIMARY KEY (name)
);