# slick-starting-on-the-right-foot
This is an activator project for showcasing best practices and providing a seed for starting with Slick.

#### Clone Project:

```
$ git clone git@github.com:knoldus/slick-starting-on-the-right-foot.git

$ cd slick-starting-on-the-right-foot

$ ./activator clean compile
```

#### Run unit test:

```
$ ./activator test
```

#### To eclipse:
```
$ ./activator eclipse
```

Unit tests have used h2 database.If you want run demo app so you need to create database in MySQL.

#### Follow these steps:

##### login into mysql server then:

```
mysql> create database bank_db;

mysql> use bank_db;

mysql> CREATE TABLE bank(id int PRIMARY KEY auto_increment,name varchar(200));

mysql>CREATE TABLE bankinfo(id int PRIMARY KEY auto_increment,owner varchar(200),bank_id int references bank(id),branches int );

mysql> CREATE TABLE bankproduct(id int PRIMARY KEY auto_increment,name varchar(200),bank_id int references bank(id));
```

#### Now you can run app:

```
$ ./activator run
```

you will see this output

```
info] Running com.knol.db.Demo 
[INFO] - [2015-08-16 18:42:25,070] - [com.zaxxer.hikari.HikariDataSource]  HikariCP pool mysql is starting.
List((Bank(ICICI bank,Some(1)),Some(BankInfo(Goverment,1000,1,Some(1)))), (Bank(SBI Bank,Some(2)),None))
List((Bank(ICICI bank,Some(1)),Some(BankProduct(car loan,1,Some(1)))), (Bank(SBI Bank,Some(2)),None))
```
