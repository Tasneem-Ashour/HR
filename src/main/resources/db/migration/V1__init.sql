create TABLE department(
  id int NOT NULL auto_increment,
  name varchar(255) not NULL,
  PRIMARY KEY (id)
);

create TABLE  `team` (
  `id` int NOT NULL auto_increment,
  `name` varchar(255) not NULL,
  PRIMARY KEY (`id`)
) ;

create TABLE `employee`(
  `id` int NOT NULL auto_increment,
  `first_name` varchar(255) not NULL,
  `last_name` varchar(255) not NULL,
  `salary` varchar(255) not NULL,
  `dob` date not NULL,
  `graduation` varchar(4),
  `dept_id` int DEFAULT NULL,
  `gender` varchar(255) not NULL,
  `team_id` int DEFAULT NULL,
  `manager_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  FOREIGN KEY (`manager_id`) REFERENCES `employee` (`id`),
  FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`)
);

create TABLE `expertise`(
  `id` int NOT NULL auto_increment,
  `name` varchar(255) not NULL,
  `emp_id` int ,
  PRIMARY KEY (`id`),
   FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
);




