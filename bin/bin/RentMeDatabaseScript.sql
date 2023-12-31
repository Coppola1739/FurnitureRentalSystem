
use cs3230f23c;

drop table if exists `Member`;
CREATE TABLE `Member` (
    member_id CHAR(10) NOT NULL PRIMARY KEY,
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    b_date DATE NOT NULL,
    gender VARCHAR(10),
    phone_num CHAR(10) NOT NULL,
    street_add VARCHAR(80),
    city VARCHAR(50),
    state VARCHAR(20),
    zip CHAR(5),
    register_date DATE
);

drop table if exists `Category`;
CREATE TABLE `Category` (
    name VARCHAR(20)
);

drop table if exists `Style`;
CREATE TABLE `Style` (
    name VARCHAR(20)
);

drop table if exists `Furniture`;
CREATE TABLE `Furniture` (
    id CHAR(10) NOT NULL PRIMARY KEY,
    style_name VARCHAR(20),
    category_name VARCHAR(20),
    rental_rate DECIMAL(6 , 2 ) NOT NULL
);

drop table if exists `Return`;
CREATE TABLE `Return` (
    id CHAR(10) PRIMARY KEY,
    member_id CHAR(10),
    employee_num CHAR(10)
);

drop table if exists `Role`;
CREATE TABLE `Role` (
    name VARCHAR(20) PRIMARY KEY
);

drop table if exists `Rental`;
CREATE TABLE `Rental` (
    rental_id CHAR(10) PRIMARY KEY NOT NULL,
    member_id CHAR(10),
    employee_num CHAR(10),
    start_date DATE NOT NULL,
    due_date DATE
);

drop table if exists `Rental_Item`;
CREATE TABLE `Rental_Item` (
    rental_id CHAR(10) PRIMARY KEY NOT NULL,
    furniture_id CHAR(10),
    quantity INT,
    cost DECIMAL(6 , 2 )
);

drop table if exists `Return_Item`;
CREATE TABLE `Return_Item` (
    rental_id CHAR(10),
    furniture_id CHAR(10),
    return_id CHAR(10),
    fine_amount DECIMAL(6 , 2 ),
    quantity INT
);


drop table if exists `Employee`;
CREATE TABLE `Employee` (
    employee_num CHAR(10) NOT NULL PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    b_date DATE NOT NULL,
    gender VARCHAR(10),
    phone_num CHAR(10) NOT NULL,
    street_add VARCHAR(80),
    city VARCHAR(50),
    state VARCHAR(20),
    zip CHAR(5),
    register_date DATE,
    role varchar(20)
);



alter table `Rental`
add constraint	fk_rental_member foreign key (member_id) references `Member`.member_id;

alter table `Rental`
add constraint	fk_rental_employee foreign key (employee_num) references `Employee`.employee_num;

alter table `Rental_Item`
add constraint	fk_rental_item_furniture foreign key (furniture_id) references `Rental`.rental_id;

alter table `Rental_Item`
add constraint	fk_rental_item_rental foreign key (employee_num) references `Employee`.employee_num;

alter table `Employee`
add constraint fk_employee_role foreign key (role) references `Role`.name;

alter table `Return`
add constraint fk_return_member foreign key (member_id) references `Member`.member_id;

alter table `Return`
add constraint fk_return_employee foreign key (employee_num) references `Employee`.employee_num;

alter table `Furniture`
add constraint fk_furniture_category foreign key (category_name) references `Category`.name;

alter table `Furniture`
add constraint fk_furniture_style foreign key (style_name) references `Style`.name;

alter table `Return_Item`
add constraint fk_return_item_return foreign key (return_id) references `Return`.id;