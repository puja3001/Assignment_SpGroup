drop database if exists friends_management;

create database friends_management;

use friends_management;

create table users (
	email varchar(128) not null primary key,
	firstname varchar(128) not null,
	lastname varchar(128) not null,
	gender enum('M', 'F'),
	city varchar(128),
	country varchar(128)
);

create table relationships (
	userone varchar(128) not null,
	usertwo varchar(128) not null,
	status enum('friends') not null,
	constraint fk_userone foreign key (userone) references users(email),
	constraint fk_usertwo foreign key (usertwo) references users(email),
	primary key (userone, usertwo)
);

create table followings (
	username varchar(128) not null,
	fusername varchar(128) not null,
	fstatus enum('subscribed','blocked') not null,
	constraint fk_username foreign key (username) references users(email),
	constraint fk_following_username foreign key (fusername) references users(email),
	primary key (username, fusername)
);

-- Dumping data for table `users`
LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES ('andy@gmail.com','Andy', 'Smith', 'F', 'Singapore', 'Singapore'),('john@gmail.com','John', 'Cooper', 'M', 'Singapore', 'Singapore'),('puja@gmail.com','Puja', 'Ag', 'F', 'Singapore', 'Singapore');
UNLOCK TABLES;

-- Dumping data for table `relationships`
LOCK TABLES `relationships` WRITE;
INSERT INTO `relationships` VALUES ('andy@gmail.com','john@gmail.com','friends'),('puja@gmail.com','john@gmail.com','friends');
UNLOCK TABLES;

-- Dumping data for table `followings`
LOCK TABLES `followings` WRITE;
INSERT INTO `followings` VALUES ('andy@gmail.com','john@gmail.com', 'subscribed'),('john@gmail.com','andy@gmail.com', 'blocked');
UNLOCK TABLES;
