create table `user`(
  id int NOT NULL auto_increment,
  email varchar(255) not null,
  `password` varchar(255) not null,
  role_id int not null,
  employee_id int not null,
  PRIMARY KEY (id),
  foreign key (role_id) references `role` (id),
  foreign key (employee_id) references `employee` (id) ON DELETE CASCADE
);