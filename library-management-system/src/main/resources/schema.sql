create table if not exists author (author_id bigint not null, bio varchar(255), country varchar(255), email_address varchar(255), first_name varchar(255), last_name varchar(255), primary key (author_id), constraint UC_AUTHOR unique (email_address))
create table if not exists book (first_published date, last_edition_date date, book_id bigint not null, borrower_id bigint, library_branch_id bigint, category varchar(255), edition varchar(255), isbn varchar(255), title varchar(255), primary key (book_id), constraint UC_BOOK unique (isbn))
create table if not exists book_author (author_id bigint not null, book_id bigint not null, id bigint not null, primary key (author_id, book_id, id), constraint UC_BOOK_AUTHOR unique (book_id, author_id))
create table if not exists book_genre (book_id bigint not null, genre_id bigint not null, id bigint not null, primary key (book_id, genre_id, id), constraint UC_BOOK_GENRE unique (book_id, genre_id))
create table if not exists borrower (borrower_id bigint not null, email_address varchar(255), first_name varchar(255), last_name varchar(255), phone varchar(255), primary key (borrower_id), constraint UC_BORROWER unique (email_address))
create table if not exists genre (genre_id bigint not null, genre_name varchar(255), primary key (genre_id), constraint UC_GENRE unique (genre_name))
create table if not exists library_branch (library_branch_id bigint not null, address varchar(255), library_branch_name varchar(255), primary key (library_branch_id), constraint UC_LIBRARY_BRANCH unique (library_branch_name))
alter table if exists book add constraint if not exists FK_BOOK_BORROWER_ID foreign key (borrower_id) references borrower
alter table if exists book add constraint if not exists FK_BOOK_LIB_BRANCH_ID foreign key (library_branch_id) references library_branch
alter table if exists book_author add constraint if not exists FK_BOOK_AUTHOR_AUTHOR_ID foreign key (author_id) references author
alter table if exists book_author add constraint if not exists FK_BOOK_AUTHOR_BOOK_ID foreign key (book_id) references book
alter table if exists book_genre add constraint if not exists FK_BOOK_GENRE_GENRE_ID foreign key (genre_id) references genre
alter table if exists book_genre add constraint if not exists FK_BOOK_GENRE_BOOK_ID foreign key (book_id) references book