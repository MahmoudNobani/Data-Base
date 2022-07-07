drop database employeeDB;
create database employeeDB;
use employeeDB;


create table inventory (
   dvd_id int auto_increment PRIMARY KEY,
   title varchar(70),
   purchase_price float, 
   drental_price float, 
   type1 varchar(20),
   p_year varchar(20),
   rating varchar(20),
   branch_id int,
   foreign key (branch_id) references branch(branch_id)
);

CREATE TABLE Branch (
   branch_id int auto_increment NOT NULL,
   address varchar(50), 
   Landline varchar(10),
   primary key (branch_id)
);

CREATE TABLE Employee(
Emp_id int auto_increment,
Efname varchar(10),
Elname varchar(10),
Dob date,
phoneN varchar(10),
address varchar(50),
hire_date date,
base_salary float,
job_desc varchar(15),
Branch_id int,  
passW  varchar(10),
primary  key (Emp_id),
foreign  key(Branch_id) references Branch(branch_id) on delete cascade
);

insert into branch values (1,"ramallah","123v");
insert into branch values (2,"nablus","45s");

insert into inventory (title,purchase_price,drental_price,type1,p_year,rating,branch_id) values ("godfather",20,5.5,"drama","1970","9.2",1);
insert into inventory (title,purchase_price,drental_price,type1,p_year,rating,branch_id) values ("intersteller",20,5.5,"sci-fi","2010","8.6",2);


-- key, name, lastname, bday, num, address ,hiredate, salary, manager?, branch, password
insert into  Employee values ("1",'general','manager','1980-02-04','0567498329',"ramallah",'2000-08-12',4000,"gm",1,1);
insert into  Employee values ("2",'Osama','Rihami','1994-4-24','0567498329',"ramallah",'2010-06-02',4000,"manager",2,12345);
insert into  Employee values ("3",'mahmoud','nobani','2000-6-13','0594188280',"nablus",'2015-07-25',2500,"employee",2,1212);
insert into  Employee values ("4",'ahmad','nobani','2000-4-10','0591234580',"nablus",'2016-04-28',2500,"employee",1,1212);


-- ALTER TABLE employee change job_desc manager int;
select * from employee;
select * from Employee where Emp_id = 1;
select * from employee where Branch_id = 2;



