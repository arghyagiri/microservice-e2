drop table if exists tuition cascade 
drop sequence if exists tuition_sequence
create sequence tuition_sequence start with 1 increment by 100
create table tuition (course_id bigint, tuition_id bigint not null, fee_amount bigint not null, primary key (tuition_id), constraint UC_TUITION unique (course_id))
