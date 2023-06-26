CREATE TABLE `persons` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(80) NOT NULL,   
  `last_name` varchar(80) NOT NULL,
  `address` varchar(100) NOT NULL,
   `gender` varchar(7) NOT NULL,
  PRIMARY KEY (`id`)
);