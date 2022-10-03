create table `raises`(
`id` int NOT NULL auto_increment,
`raisesDate` date not null ,
`amount` varchar(255) not null,
`emp_id` int not null,
primary key (id),
foreign key (emp_id) references `employee` (id) ON DELETE CASCADE
)