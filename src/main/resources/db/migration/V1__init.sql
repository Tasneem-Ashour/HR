CREATE TABLE department(
  id int NOT NULL auto_increment,
  name varchar(255) not NULL,
  PRIMARY KEY (id)
);

CREATE TABLE  `team` (
  `id` int NOT NULL auto_increment,
  `name` varchar(255) not NULL,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `employee`(
  `id` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `salary` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `graduation` tinyblob,
  `dept_id` int DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `team_id` int DEFAULT NULL,
  `manager_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  FOREIGN KEY (`manager_id`) REFERENCES `employee` (`id`),
  FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`)
);

CREATE TABLE `expertise`(
  `id` int NOT NULL auto_increment,
  `name` varchar(255) not NULL,
  `emp_id` int not NULL,
  PRIMARY KEY (`id`),
   FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
);



