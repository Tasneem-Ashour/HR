create table `user_credential`(
  id int NOT NULL auto_increment,
  `email` varchar(255) not null unique ,
  `password` varchar(255) not null,
  employee_id int not null unique ,
  PRIMARY KEY (id),
  foreign key (employee_id) references `employee` (id) ON DELETE CASCADE
);