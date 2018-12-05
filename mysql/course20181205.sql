-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: localhost    Database: course_selection
-- ------------------------------------------------------
-- Server version	5.7.24-log

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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `course_number` char(5) NOT NULL,
  `course_title` varchar(45) CHARACTER SET utf8 NOT NULL,
  `instructor_number` int(10) unsigned NOT NULL,
  `course_size` tinyint(4) unsigned NOT NULL,
  `course_weekday` tinyint(4) unsigned NOT NULL,
  `course_classtime` varchar(15) NOT NULL,
  `course_remain` tinyint(4) unsigned NOT NULL,
  PRIMARY KEY (`course_number`),
  UNIQUE KEY `course_number_UNIQUE` (`course_number`),
  KEY `instructor_number` (`instructor_number`),
  CONSTRAINT `instructor_number` FOREIGN KEY (`instructor_number`) REFERENCES `instructor` (`instructor_number`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('BS777','籃球',10,10,5,'5,6,7',10),('DB101','Database',1,40,3,'1,2,3',38),('MS534','Music',5,20,2,'5,6,7',20),('SA104','軟體工程',5,10,5,'5,6,7',0);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instructor` (
  `Instructor_name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Instructor_office` char(4) DEFAULT NULL,
  `instructor_number` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`instructor_number`),
  UNIQUE KEY `instructor_number_UNIQUE` (`instructor_number`),
  UNIQUE KEY `Instructor_office_UNIQUE` (`Instructor_office`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES ('Meryl Streep',NULL,1),('Tom Cruise','C102',2),('周杰倫','D959',4),('Gail','S116',5),('李安',NULL,6),('李白','B987',8),('Leo','M552',9),('Reece',NULL,10);
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selection`
--

DROP TABLE IF EXISTS `selection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `selection` (
  `selection_number` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `course_number` char(5) NOT NULL,
  `student_number` int(10) unsigned NOT NULL,
  PRIMARY KEY (`selection_number`),
  UNIQUE KEY `selection_number_UNIQUE` (`selection_number`),
  KEY `course_number_idx` (`course_number`),
  KEY `student_number_idx` (`student_number`),
  CONSTRAINT `course_number` FOREIGN KEY (`course_number`) REFERENCES `course` (`course_number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `student_number` FOREIGN KEY (`student_number`) REFERENCES `student` (`student_number`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selection`
--

LOCK TABLES `selection` WRITE;
/*!40000 ALTER TABLE `selection` DISABLE KEYS */;
INSERT INTO `selection` VALUES (36,'SA104',19),(37,'DB101',19),(38,'DB101',18),(39,'SA104',18),(40,'SA104',17),(41,'SA104',16),(42,'SA104',15),(43,'SA104',14),(44,'SA104',13),(45,'SA104',12);
/*!40000 ALTER TABLE `selection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `student_number` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `student_name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `student_gender` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`student_number`),
  UNIQUE KEY `student_number_UNIQUE` (`student_number`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Jack',NULL),(2,'Marry','Female'),(3,'Jacky Chen','Male'),(4,'周杰倫','Male'),(12,'蔡依林','Female'),(13,'Joe','Male'),(14,'Yoyo',NULL),(15,'Harry','male'),(16,'Oliver','male'),(17,'Jacob','male'),(18,'Thomas','male'),(19,'Amelia','female'),(20,'Kyle','Bisexual'),(21,'KyleChen','Bisexual');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-05 21:00:53
