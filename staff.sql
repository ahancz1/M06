START TRANSACTION;

CREATE TABLE `staff` (
  `id` char(9) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `mi` char(1) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `phone` char(10) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `staff` (`id`, `lastName`, `firstName`, `mi`, `address`, `city`, `state`, `phone`, `email`) 
VALUES ('912109', 'Ashley', 'Hancz', 'S', '123 Street', 'South Bend', 'IN', '5741234567', '');

ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`);

COMMIT;
