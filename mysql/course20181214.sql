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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES ('Meryl Streep',NULL,1),('Tom Cruise','C102',2),('周杰倫','D959',4),('Gail','S116',5),('李安',NULL,6),('李白','B987',8),('Leo','M552',9),('Reece',NULL,10),('Joy','C184',11),('Instructor0','THjq',12),('Instructor0','iJis',13),('Instructor1','nWqP',14),('Instructor2','BMqx',15),('Instructor3','ZHnn',16),('Instructor4','QvCQ',17),('Instructor0','swmN',18);
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
  PRIMARY KEY (`course_number`,`student_number`),
  UNIQUE KEY `selection_number_UNIQUE` (`selection_number`),
  KEY `course_number_idx` (`course_number`),
  KEY `student_number_idx` (`student_number`),
  CONSTRAINT `course_number` FOREIGN KEY (`course_number`) REFERENCES `course` (`course_number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `student_number` FOREIGN KEY (`student_number`) REFERENCES `student` (`student_number`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selection`
--

LOCK TABLES `selection` WRITE;
/*!40000 ALTER TABLE `selection` DISABLE KEYS */;
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
  `student_classtime` json DEFAULT NULL,
  PRIMARY KEY (`student_number`),
  UNIQUE KEY `student_number_UNIQUE` (`student_number`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (146,'Student5','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(147,'Student6','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(148,'Student7','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(149,'Student8','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(150,'Student9','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(151,'Student10','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(152,'Student11','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(153,'Student12','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(154,'Student13','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(155,'Student14','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(156,'Student15','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(157,'Student16','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(158,'Student17','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(159,'Student18','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(160,'Student19','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(161,'Student20','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(162,'Student21','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(163,'Student22','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(164,'Student23','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(165,'Student24','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(166,'Student25','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(167,'Student26','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(168,'Student27','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(169,'Student28','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(170,'Student29','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(171,'Student30','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(172,'Student31','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(173,'Student32','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(174,'Student33','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(175,'Student34','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(176,'Student35','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(177,'Student36','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(178,'Student37','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(179,'Student38','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(180,'Student39','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(181,'Student40','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(182,'Student41','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(183,'Student42','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(184,'Student43','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(185,'Student44','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(186,'Student45','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(187,'Student46','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(188,'Student47','Bisexual','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(189,'Student48','Male','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}'),(190,'Student49','Female','{\"1\": \"\", \"2\": \"\", \"3\": \"\", \"4\": \"\", \"5\": \"\"}');
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

-- Dump completed on 2018-12-14 17:32:28
