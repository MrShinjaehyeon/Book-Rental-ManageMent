/* 시퀀스 */
create sequence seq
    start with 1
    increment by 1
    maxvalue 9999999999
    minvalue 1
    nocycle;

/* 회원정보 */
create table member(
    id varchar2(15) constraint mem_id_pk primary key,
    password varchar2(15) constraint mem_pw_nn not null,
    name varchar2(15) constraint mem_name_nn not null,
    gender varchar2(6),
    birth varchar2(6),
    tel varchar2(13),
    regdate varchar2(10)constraint mem_regdt_nn not null);
    
/* 도서 정보 */
create table book(
    no_book number(10) constraint binfo_no_pk primary key,
    title varchar2(100) constraint binfo_title_nn not null,
    author varchar2(100) constraint binfo_author_nn not null,
    publisher varchar2(100) constraint binfo_publisher_nn not null,
    pb_year varchar2(4) constraint binfo_pbdt_nn not null,
    price number(10) constraint binfo_price_nn not null,
    division varchar2(12)constraint binfo_division_nn not null,
    lang varchar2(30) constraint binfo_lang_nn not null,
    category varchar2(30) constraint binfo_category_nn not null,
    status varchar2(9) constraint binfo_status_nn not null,
    regdate varchar2(10)constraint binfo_regdt_nn not null);

create table basket(
    no_book number(10) constraint basket_no_nn not null,
    title varchar2(100) constraint basket_title_nn not null,
    author varchar2(100) constraint basket_author_nn not null,
    publisher varchar2(100) constraint basket_publisher_nn not null,
    pb_year varchar2(4) constraint basket_pbdt_nn not null,
    price number(6) constraint basket_price_nn not null,
    division varchar2(12)constraint basket_division_nn not null,
    lang varchar2(30) constraint basket_lang_nn not null,
    category varchar2(30) constraint basket_category_nn not null,
    status varchar2(9) constraint basket_status_nn not null,
    regdate varchar2(10)constraint basket_regdt_nn not null);

create table rental(
    no_book number(10) CONSTRAINT renstat_no_nn not null,
    title varchar2(100) constraint renstat_renttitle_nn not null,
    id varchar2(15) CONSTRAINT renstat_id_nn not null,
    name varchar2(15) constraint renstat_rentname_nn not null,
    tel varchar2(13),
    rental_date varchar2(10) constraint renstat_rantaldt_nn not null,
    return_date varchar2(10) constraint renstat_returndt_nn not null,
    regdate varchar2(10)constraint renstat_regdt_nn not null);
    
insert into book values(seq.nextval,'인간 본성의 법칙','로버트 그린','위즈덤하우스','2019',32000,'국내도서','한국어','인문','보유', '2022-01-18');
insert into member values ('aaa','aaa','신재현','남자','','','2022-01-19');

commit;

select * from basket;
select * from book where pb_year = '2019';
delete from basket;
drop table basket;
drop sequence seq;

select constraint_name,table_name,r_constraint_name from user_constraints where constraint_name = 'RENSTAT_NO_FK';