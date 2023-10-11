drop table if exists student cascade 
drop sequence if exists student_sequence
create sequence student_sequence start with 1 increment by 100
create table student (course_id bigint, student_id bigint not null, name varchar(255), primary key (student_id), constraint UC_STUDENT unique (name, course_id))
