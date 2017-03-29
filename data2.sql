-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.17-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for rms
CREATE DATABASE IF NOT EXISTS `rms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rms`;

-- Dumping structure for table rms.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `emp_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` longtext,
  `city` varchar(255) DEFAULT NULL,
  `date_added` datetime NOT NULL,
  `division` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `emp_status` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `gender` int(11) NOT NULL,
  `hired_date` date NOT NULL,
  `job_family` varchar(255) NOT NULL,
  `last_modified` datetime NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `marital_status` int(11) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `post_code` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  `sub_division` varchar(255) DEFAULT NULL,
  `suspend_date` date DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `UK_fopic1oh5oln2khj8eat6ino0` (`email`),
  UNIQUE KEY `UK_buf2qp04xpwfp5qq355706h4a` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.family_members
CREATE TABLE IF NOT EXISTS `family_members` (
  `fam_id` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `gender` int(11) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `relation` int(11) NOT NULL,
  `emp_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fam_id`),
  KEY `FKf3pvaaf4sim9wote1sblqxndy` (`emp_id`),
  CONSTRAINT `FKf3pvaaf4sim9wote1sblqxndy` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.grades
CREATE TABLE IF NOT EXISTS `grades` (
  `grade_id` varchar(255) NOT NULL,
  `ds` int(11) NOT NULL,
  `end_date` date DEFAULT NULL,
  `grade` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `emp_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  KEY `FKkyi4cr0awkplpj85je0hs3gkl` (`emp_id`),
  CONSTRAINT `FKkyi4cr0awkplpj85je0hs3gkl` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.office_locations
CREATE TABLE IF NOT EXISTS `office_locations` (
  `loc_id` varchar(255) NOT NULL,
  `end_date` date DEFAULT NULL,
  `office_location` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `emp_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`loc_id`),
  KEY `FKkkk2sgdocfmfn90xqpkyp9bdc` (`emp_id`),
  CONSTRAINT `FKkkk2sgdocfmfn90xqpkyp9bdc` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.projects
CREATE TABLE IF NOT EXISTS `projects` (
  `project_id` varchar(255) NOT NULL,
  `end_date` date DEFAULT NULL,
  `job_desc` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `start_date` date NOT NULL,
  `emp_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FK1vb52nfqopectvb1rwsq0x95j` (`emp_id`),
  CONSTRAINT `FK1vb52nfqopectvb1rwsq0x95j` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.ref_division
CREATE TABLE IF NOT EXISTS `ref_division` (
  `div_code` varchar(255) NOT NULL,
  `division` varchar(255) NOT NULL,
  `job_family` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`div_code`),
  KEY `FKholx18apjdr9oj73047ueiemv` (`job_family`),
  CONSTRAINT `FKholx18apjdr9oj73047ueiemv` FOREIGN KEY (`job_family`) REFERENCES `ref_job_family` (`jf_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.ref_jf_level
CREATE TABLE IF NOT EXISTS `ref_jf_level` (
  `level_id` varchar(255) NOT NULL,
  `grade` varchar(255) NOT NULL,
  `max_ds` int(11) NOT NULL,
  `min_ds` int(11) NOT NULL,
  `job_family` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`level_id`),
  KEY `FKpdcfm6nffd0j3gekjr8bi8gdy` (`job_family`),
  CONSTRAINT `FKpdcfm6nffd0j3gekjr8bi8gdy` FOREIGN KEY (`job_family`) REFERENCES `ref_job_family` (`jf_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.ref_job_family
CREATE TABLE IF NOT EXISTS `ref_job_family` (
  `jf_code` varchar(255) NOT NULL,
  `job_family` varchar(255) NOT NULL,
  `max_ds` int(11) NOT NULL,
  `min_ds` int(11) NOT NULL,
  PRIMARY KEY (`jf_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.ref_office_address
CREATE TABLE IF NOT EXISTS `ref_office_address` (
  `address_id` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `post_code` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `street_address` varchar(255) NOT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rms.ref_subdivision
CREATE TABLE IF NOT EXISTS `ref_subdivision` (
  `sub_div_code` varchar(255) NOT NULL,
  `sub_division` varchar(255) NOT NULL,
  `division` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sub_div_code`),
  KEY `FK6stbygfapbcgp78otv2qwpke3` (`division`),
  CONSTRAINT `FK6stbygfapbcgp78otv2qwpke3` FOREIGN KEY (`division`) REFERENCES `ref_division` (`div_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
