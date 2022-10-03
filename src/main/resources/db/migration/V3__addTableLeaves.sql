create table leaves(
`id` int NOT NULL auto_increment,
`fromDate` date not null ,
`toDate` date not null ,
`emp_id` int not null,
primary key (id),
foreign key (emp_id) references `employee` (id) ON DELETE CASCADE
);