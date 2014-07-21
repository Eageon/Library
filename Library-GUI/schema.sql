-- MySQL dump 10.13  Distrib 5.6.17, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: Library
-- ------------------------------------------------------
-- Server version	5.6.17-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BOOK`
--

DROP TABLE IF EXISTS `BOOK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOOK` (
  `Book_id` char(10) NOT NULL,
  `Title` varchar(100) NOT NULL,
  PRIMARY KEY (`Book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BOOK_AUTHORS`
--

DROP TABLE IF EXISTS `BOOK_AUTHORS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOOK_AUTHORS` (
  `Book_id` char(10) NOT NULL DEFAULT '',
  `Author_name` varchar(30) NOT NULL DEFAULT '',
  `Fname` varchar(15) DEFAULT NULL,
  `Minit` varchar(10) DEFAULT NULL,
  `Lname` varchar(15) NOT NULL,
  PRIMARY KEY (`Book_id`,`Author_name`),
  CONSTRAINT `BOOK_AUTHORS_ibfk_1` FOREIGN KEY (`Book_id`) REFERENCES `BOOK` (`Book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BOOK_COPIES`
--

DROP TABLE IF EXISTS `BOOK_COPIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOOK_COPIES` (
  `Book_id` char(10) NOT NULL DEFAULT '',
  `Branch_id` varchar(4) NOT NULL DEFAULT '',
  `No_of_copies` int(11) DEFAULT NULL,
  PRIMARY KEY (`Book_id`,`Branch_id`),
  KEY `Branch_id` (`Branch_id`),
  CONSTRAINT `BOOK_COPIES_ibfk_2` FOREIGN KEY (`Branch_id`) REFERENCES `LIBRARY_BRANCH` (`Branch_id`),
  CONSTRAINT `BOOK_COPIES_ibfk_1` FOREIGN KEY (`Book_id`) REFERENCES `BOOK` (`Book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BOOK_LOANS`
--

DROP TABLE IF EXISTS `BOOK_LOANS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOOK_LOANS` (
  `Loan_id` int(11) NOT NULL AUTO_INCREMENT,
  `Book_id` char(10) DEFAULT NULL,
  `Branch_id` varchar(4) DEFAULT NULL,
  `Card_no` int(11) DEFAULT NULL,
  `Date_out` date NOT NULL,
  `Due_date` date NOT NULL,
  `Date_in` date DEFAULT NULL,
  PRIMARY KEY (`Loan_id`),
  KEY `Book_id` (`Book_id`),
  KEY `Branch_id` (`Branch_id`),
  KEY `Card_no` (`Card_no`),
  CONSTRAINT `BOOK_LOANS_ibfk_1` FOREIGN KEY (`Book_id`) REFERENCES `BOOK` (`Book_id`),
  CONSTRAINT `BOOK_LOANS_ibfk_2` FOREIGN KEY (`Branch_id`) REFERENCES `LIBRARY_BRANCH` (`Branch_id`),
  CONSTRAINT `BOOK_LOANS_ibfk_3` FOREIGN KEY (`Card_no`) REFERENCES `BORROWER` (`Card_no`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BORROWER`
--

DROP TABLE IF EXISTS `BORROWER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BORROWER` (
  `Card_no` int(11) NOT NULL AUTO_INCREMENT,
  `Fname` varchar(15) DEFAULT NULL,
  `Lname` varchar(15) NOT NULL,
  `Address` varchar(30) NOT NULL,
  `City` varchar(20) NOT NULL,
  `State` char(2) NOT NULL,
  `Phone` char(14) DEFAULT NULL,
  PRIMARY KEY (`Card_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9045 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FINE`
--

DROP TABLE IF EXISTS `FINE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FINE` (
  `Loan_id` int(11) NOT NULL,
  `Fine_amt` float(6,2) NOT NULL DEFAULT '0.00',
  `Paid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Loan_id`),
  CONSTRAINT `FINE_ibfk_1` FOREIGN KEY (`Loan_id`) REFERENCES `BOOK_LOANS` (`Loan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LIBRARY_BRANCH`
--

DROP TABLE IF EXISTS `LIBRARY_BRANCH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LIBRARY_BRANCH` (
  `Branch_id` varchar(4) NOT NULL DEFAULT '',
  `Branch_name` varchar(30) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `City` varchar(20) NOT NULL,
  `State` char(2) NOT NULL,
  `Zip` char(5) DEFAULT NULL,
  PRIMARY KEY (`Branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-20 21:23:09
