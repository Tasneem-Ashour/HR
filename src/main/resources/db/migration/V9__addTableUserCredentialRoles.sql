CREATE TABLE `user_credential_roles` (
  `user_id` int NOT NULL  ,
  `roles_id` int NOT NULL  ,
  foreign key (user_id) references `user_credential` (id) ON DELETE CASCADE ,
  foreign key (roles_id) references `role` (id)
)
