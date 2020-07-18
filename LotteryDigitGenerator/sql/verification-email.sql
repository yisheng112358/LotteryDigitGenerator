create database emailbox;

create table emails(
   emailid INT NOT NULL AUTO_INCREMENT,
   emailaddress VARCHAR(100) NOT null UNIQUE,
   PRIMARY KEY ( emailid )
);

insert into emails(emailaddress) values ('Eason@mail.com'), ('Fate@mail.com');