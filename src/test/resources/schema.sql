
CREATE TABLE bank(id int PRIMARY KEY auto_increment,name varchar(200));

CREATE TABLE bankinfo(id int PRIMARY KEY auto_increment,owner varchar(200),bank_id int references bank(id),branches int );

CREATE TABLE bankproduct(id int PRIMARY KEY auto_increment,name varchar(200),bank_id int references bank(id));

