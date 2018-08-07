-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: gradebook
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Woody','Allen','woody.allen@gmail.com','admin'),(2,'Tony','Montana','tony.montana@gmail.com','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `attendance_view`
--

DROP TABLE IF EXISTS `attendance_view`;
/*!50001 DROP VIEW IF EXISTS `attendance_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `attendance_view` AS SELECT 
 1 AS `id`,
 1 AS `group_id`,
 1 AS `semester_id`,
 1 AS `gradebook_id`,
 1 AS `task_id`,
 1 AS `title`,
 1 AS `start_date`,
 1 AS `task_length`,
 1 AS `student_id`,
 1 AS `first_name`,
 1 AS `last_name`,
 1 AS `attendance_id`,
 1 AS `present`,
 1 AS `absent_with_reason`,
 1 AS `absent`,
 1 AS `class_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `faculty_id` int(10) unsigned NOT NULL,
  `name` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_department_faculty1_idx` (`faculty_id`),
  CONSTRAINT `fk_department_faculty1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,1,'Architecture and Urban Design'),(3,2,'Computer Engineering');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Architecture'),(2,'Informatics and Computer Science');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradebook`
--

DROP TABLE IF EXISTS `gradebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradebook` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `semester_id` int(10) unsigned NOT NULL,
  `group_id` int(10) unsigned NOT NULL,
  `subject` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `fk_gradebook_semester1_idx` (`semester_id`),
  KEY `fk_gradebook_group1_idx` (`group_id`),
  CONSTRAINT `fk_gradebook_group1` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gradebook_semester1` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradebook`
--

LOCK TABLES `gradebook` WRITE;
/*!40000 ALTER TABLE `gradebook` DISABLE KEYS */;
INSERT INTO `gradebook` VALUES (1,3,1,'History of Art','The history of art is the history of any activity or product made by humans in a visual form for aesthetical or communicative purposes, expressing ideas, emotions or, in general, a worldview. Over time visual art has been classified in diverse ways, from the medieval distinction between liberal arts and mechanical arts, to the modern distinction between fine arts and applied arts, or to the many contemporary definitions, which define art as a manifestation of human creativity. The subsequent expansion of the list of principal arts in the 20th century reached to nine: architecture, dance, sculpture, music, painting, poetry (described broadly as a form of literature with aesthetic purpose or function, which also includes the distinct genres of theatre and narrative), film, photography and graphic arts. In addition to the old forms of artistic expression such as fashion and gastronomy, new modes of expression are being considered as arts such as video, computer art, performance, advertising, animation, television and videogames.'),(2,2,2,'Drawing','Drawing is one of the major forms of expression within the visual arts. It is generally concerned with the marking of lines and areas of tone onto paper, where the accurate representation of the visual world is expressed upon a plane surface. Traditional drawings were monochrome, or at least had little colour, while modern colored-pencil drawings may approach or cross a boundary between drawing and painting. In Western terminology, drawing is distinct from painting, even though similar media often are employed in both tasks. Dry media, normally associated with drawing, such as chalk, may be used in pastel paintings. Drawing may be done with a liquid medium, applied with brushes or pens. Similar supports likewise can serve both: painting generally involves the application of liquid paint onto prepared canvas or panels, but sometimes an underdrawing is drawn first on that same support.'),(9,9,1,'Programming Languages','This course is an introduction to the basic concepts of programming languages, with a strong emphasis on functional programming. The course uses the languages ML, Racket, and Ruby as vehicles for teaching the concepts, but the real intent is to teach enough about how any language тАЬfits togetherтАЭ to make you more effective programming in any language -- and in learning new ones.\n\nThis course is neither particularly theoretical nor just about programming specifics -- it will give you a framework for understanding how to use language constructs effectively and how to design correct and elegant programs. By using different languages, you will learn to think more deeply than in terms of the particular syntax of one language. The emphasis on functional programming is essential for learning how to write robust, reusable, composable, and elegant programs. Indeed, many of the most important ideas in modern languages have their roots in functional programming. Get ready to learn a fresh and beautiful way to look at software and how to have fun building it.\n\nThe course assumes some prior experience with programming, as described in more detail in the first module.\n\nThe course is divided into three Coursera courses: Part A, Part B, and Part C.  As explained in more detail in the first module of Part A, the overall course is a substantial amount of challenging material, so the three-part format provides two intermediate milestones and opportunities for a pause before continuing.  The three parts are designed to be completed in order and set up to motivate you to continue through to the end of Part C.  The three parts are not quite equal in length: Part A is almost as substantial as Part B and Part C combined.\n\nWeek 1 of Part A has a more detailed list of topics for all three parts of the course, but it is expected that most course participants will not (yet!) know what all these topics mean.');
/*!40000 ALTER TABLE `gradebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `number` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `faculty_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_academic_group_faculty1_idx` (`faculty_id`),
  CONSTRAINT `fk_academic_group_faculty1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (1,'ARCH15-1',1),(2,'ARCH15-2',1);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturer`
--

DROP TABLE IF EXISTS `lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecturer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `department_id` int(10) unsigned NOT NULL,
  `first_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_lecturer_department1_idx` (`department_id`),
  CONSTRAINT `fk_lecturer_department1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer`
--

LOCK TABLES `lecturer` WRITE;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` VALUES (1,1,'Anastasiy','Tovstik','anastasiy.tovstik@gmail.com','lecturer'),(2,1,'Yulia','Tovstik','yulia.tovstik@gmail.com','lecturer');
/*!40000 ALTER TABLE `lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `lecturer_gradebooks`
--

DROP TABLE IF EXISTS `lecturer_gradebooks`;
/*!50001 DROP VIEW IF EXISTS `lecturer_gradebooks`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `lecturer_gradebooks` AS SELECT 
 1 AS `id`,
 1 AS `lecturer_id`,
 1 AS `first_name`,
 1 AS `last_name`,
 1 AS `group_id`,
 1 AS `number`,
 1 AS `semester_id`,
 1 AS `academic_year`,
 1 AS `name`,
 1 AS `gradebook_id`,
 1 AS `subject`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `lecturer_has_gradebook`
--

DROP TABLE IF EXISTS `lecturer_has_gradebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecturer_has_gradebook` (
  `gradebook_id` int(10) unsigned NOT NULL,
  `lecturer_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`gradebook_id`,`lecturer_id`),
  KEY `fk_lecturer_has_gradebook_gradebook1_idx` (`gradebook_id`),
  KEY `fk_lecturer_has_gradebook_lecturer1_idx` (`lecturer_id`),
  CONSTRAINT `fk_course_lecturer_gradebook1` FOREIGN KEY (`gradebook_id`) REFERENCES `gradebook` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_lecturer_lecturer1` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer_has_gradebook`
--

LOCK TABLES `lecturer_has_gradebook` WRITE;
/*!40000 ALTER TABLE `lecturer_has_gradebook` DISABLE KEYS */;
INSERT INTO `lecturer_has_gradebook` VALUES (1,1),(1,2),(2,1),(2,2),(9,1),(9,2);
/*!40000 ALTER TABLE `lecturer_has_gradebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semester`
--

DROP TABLE IF EXISTS `semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `semester` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `academic_year` smallint(4) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semester`
--

LOCK TABLES `semester` WRITE;
/*!40000 ALTER TABLE `semester` DISABLE KEYS */;
INSERT INTO `semester` VALUES (1,'Spring',2014),(2,'Fall',2014),(3,'Spring',2015),(4,'Fall',2015),(5,'Spring',2016),(6,'Fall',2016),(7,'Spring',2017),(8,'Fall',2017),(9,'Spring',2018),(10,'Fall',2018),(11,'Spring',2019),(12,'Fall',2019),(13,'Spring',2020),(14,'Fall',2020);
/*!40000 ALTER TABLE `semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL,
  `first_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_student_group_idx` (`group_id`),
  CONSTRAINT `fk_student_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,1,'Bilbo','Baggins','bilbo.baggins@gmail.com','student'),(2,1,'Sam','Rockwell','sam.rockwell@gmail.com','student'),(3,1,'Brad','Pitt','brad.pitt@gmail.com','student'),(4,1,'George','Clooney','george.clooney@gmail.com','student'),(5,1,'Tom','Cruise','tom.cruise@gmail.com','student'),(6,1,'Johnny','Depp','johnny.depp@gmail.com','student'),(7,1,'Amber','Heard','amber.heard@gmail.com','student'),(8,1,'Tim','Robbins','tim.robbins@gmail.com','student'),(9,1,'Mel','Gibbson','mel.gibbson@gmail.com','student'),(10,1,'Jim','Carrey','jim.carrey@gmail.com','student'),(11,2,'Jessica','Alba','jessica.alba@gmail.com','student'),(12,2,'Eli','Roth','eli.roth@gmail.com','student'),(13,2,'Sasha','Grey','sasha.grey@gmail.com','student'),(14,2,'Nicolas','Cage','nicolas.cage@gmail.com','student'),(15,2,'Bill','Murray','bill.murray@gmail.com','student'),(16,2,'John','Travolta','john.travolta@gmail.com','student'),(17,2,'Gary','Oldman','gary.oldman@gmail.com','student'),(18,2,'Harrison','Ford','harrison.ford@gmail.com','student'),(19,2,'Eva','Green','eva.green@gmail.com','student'),(20,2,'Emma','Watson','emma.watson@gmail.com','student');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_attendance`
--

DROP TABLE IF EXISTS `student_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_attendance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(10) unsigned NOT NULL,
  `task_id` int(10) unsigned NOT NULL,
  `class_date` date NOT NULL,
  `present` tinyint(1) NOT NULL,
  `absent` tinyint(1) NOT NULL,
  `absent_with_reason` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_attendance_task1_idx` (`task_id`),
  KEY `fk_student_attendance_student1_idx` (`student_id`),
  CONSTRAINT `fk_student_attendance_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_attendance_task1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=459 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_attendance`
--

LOCK TABLES `student_attendance` WRITE;
/*!40000 ALTER TABLE `student_attendance` DISABLE KEYS */;
INSERT INTO `student_attendance` VALUES (1,1,1,'2015-09-29',0,0,1),(2,1,1,'2015-10-01',1,0,0),(3,1,1,'2015-10-06',0,0,0),(4,1,1,'2015-10-08',0,0,0),(5,1,1,'2015-10-13',0,0,0),(6,1,1,'2015-10-15',0,0,0),(7,2,1,'2015-09-29',0,1,0),(8,2,1,'2015-10-01',0,1,0),(9,2,1,'2015-10-06',0,0,0),(10,2,1,'2015-10-08',0,0,0),(11,2,1,'2015-10-13',0,0,0),(12,2,1,'2015-10-15',0,0,0),(13,3,1,'2015-09-29',1,0,0),(14,3,1,'2015-10-01',0,0,1),(15,3,1,'2015-10-06',0,0,0),(16,3,1,'2015-10-08',0,0,0),(17,3,1,'2015-10-13',0,0,0),(18,3,1,'2015-10-15',0,0,0),(19,4,1,'2015-09-29',1,0,0),(20,4,1,'2015-10-01',1,0,0),(21,4,1,'2015-10-06',0,0,0),(22,4,1,'2015-10-08',0,0,0),(23,4,1,'2015-10-13',0,0,0),(24,4,1,'2015-10-15',0,0,0),(25,5,1,'2015-09-29',0,0,1),(26,5,1,'2015-10-01',0,0,0),(27,5,1,'2015-10-06',0,0,0),(28,5,1,'2015-10-08',0,0,0),(29,5,1,'2015-10-13',0,0,0),(30,5,1,'2015-10-15',0,0,0),(31,6,1,'2015-09-29',1,0,0),(32,6,1,'2015-10-01',0,0,1),(33,6,1,'2015-10-06',0,0,0),(34,6,1,'2015-10-08',0,0,0),(35,6,1,'2015-10-13',0,0,0),(36,6,1,'2015-10-15',0,0,0),(37,7,1,'2015-09-29',1,0,0),(38,7,1,'2015-10-01',1,0,0),(39,7,1,'2015-10-06',1,0,0),(40,7,1,'2015-10-08',0,0,0),(41,7,1,'2015-10-13',0,0,0),(42,7,1,'2015-10-15',0,0,0),(43,8,1,'2015-09-29',1,0,0),(44,8,1,'2015-10-01',0,0,0),(45,8,1,'2015-10-06',0,0,0),(46,8,1,'2015-10-08',0,0,0),(47,8,1,'2015-10-13',0,0,0),(48,8,1,'2015-10-15',0,0,0),(49,9,1,'2015-09-29',1,0,0),(50,9,1,'2015-10-01',1,0,0),(51,9,1,'2015-10-06',0,0,0),(52,9,1,'2015-10-08',0,0,0),(53,9,1,'2015-10-13',0,0,0),(54,9,1,'2015-10-15',0,0,0),(55,10,1,'2015-09-29',1,0,0),(56,10,1,'2015-10-01',1,0,0),(57,10,1,'2015-10-06',0,0,0),(58,10,1,'2015-10-08',0,0,0),(59,10,1,'2015-10-13',0,0,0),(60,10,1,'2015-10-15',0,0,0),(61,1,2,'2015-10-20',0,0,0),(62,1,2,'2015-10-22',0,0,0),(63,1,2,'2015-10-27',0,0,0),(64,1,2,'2015-10-29',0,0,0),(65,2,2,'2015-10-20',0,0,0),(66,2,2,'2015-10-22',0,0,0),(67,2,2,'2015-10-27',0,0,0),(68,2,2,'2015-10-29',0,0,0),(69,3,2,'2015-10-20',0,0,0),(70,3,2,'2015-10-22',0,0,0),(71,3,2,'2015-10-27',0,0,0),(72,3,2,'2015-10-29',0,0,0),(73,4,2,'2015-10-20',0,0,0),(74,4,2,'2015-10-22',0,0,0),(75,4,2,'2015-10-27',0,0,0),(76,4,2,'2015-10-29',0,0,0),(77,5,2,'2015-10-20',0,0,0),(78,5,2,'2015-10-22',0,0,0),(79,5,2,'2015-10-27',0,0,0),(80,5,2,'2015-10-29',0,0,0),(81,6,2,'2015-10-20',0,0,0),(82,6,2,'2015-10-22',0,0,0),(83,6,2,'2015-10-27',0,0,0),(84,6,2,'2015-10-29',0,0,0),(85,7,2,'2015-10-20',0,0,0),(86,7,2,'2015-10-22',0,0,0),(87,7,2,'2015-10-27',0,0,0),(88,7,2,'2015-10-29',0,0,0),(89,8,2,'2015-10-20',0,0,0),(90,8,2,'2015-10-22',0,0,0),(91,8,2,'2015-10-27',0,0,0),(92,8,2,'2015-10-29',0,0,0),(93,9,2,'2015-10-20',0,0,0),(94,9,2,'2015-10-22',0,0,0),(95,9,2,'2015-10-27',0,0,0),(96,9,2,'2015-10-29',0,0,0),(97,10,2,'2015-10-20',0,0,0),(98,10,2,'2015-10-22',0,0,0),(99,10,2,'2015-10-27',0,0,0),(100,10,2,'2015-10-29',0,0,0),(101,11,3,'2014-09-01',0,0,0),(102,11,3,'2014-09-03',0,0,0),(103,11,3,'2014-09-08',0,0,0),(104,11,3,'2014-09-10',0,0,0),(105,11,3,'2014-09-15',0,0,0),(106,11,3,'2014-09-17',0,0,0),(107,12,3,'2014-09-01',0,0,0),(108,12,3,'2014-09-03',0,0,0),(109,12,3,'2014-09-08',0,0,0),(110,12,3,'2014-09-10',0,0,0),(111,12,3,'2014-09-15',0,0,0),(112,12,3,'2014-09-17',0,0,0),(113,13,3,'2014-09-01',0,0,0),(114,13,3,'2014-09-03',0,0,0),(115,13,3,'2014-09-08',0,0,0),(116,13,3,'2014-09-10',0,0,0),(117,13,3,'2014-09-15',0,0,0),(118,13,3,'2014-09-17',0,0,0),(119,14,3,'2014-09-01',0,0,0),(120,14,3,'2014-09-03',0,0,0),(121,14,3,'2014-09-08',0,0,0),(122,14,3,'2014-09-10',0,0,0),(123,14,3,'2014-09-15',0,0,0),(124,14,3,'2014-09-17',0,0,0),(125,15,3,'2014-09-01',0,0,0),(126,15,3,'2014-09-03',0,0,0),(127,15,3,'2014-09-08',0,0,0),(128,15,3,'2014-09-10',0,0,0),(129,15,3,'2014-09-15',0,0,0),(130,15,3,'2014-09-17',0,0,0),(131,16,3,'2014-09-01',0,0,0),(132,16,3,'2014-09-03',0,0,0),(133,16,3,'2014-09-08',0,0,0),(134,16,3,'2014-09-10',0,0,0),(135,16,3,'2014-09-15',0,0,0),(136,16,3,'2014-09-17',0,0,0),(137,17,3,'2014-09-01',0,0,0),(138,17,3,'2014-09-03',0,0,0),(139,17,3,'2014-09-08',0,0,0),(140,17,3,'2014-09-10',0,0,0),(141,17,3,'2014-09-15',0,0,0),(142,17,3,'2014-09-17',0,0,0),(143,18,3,'2014-09-01',0,0,0),(144,18,3,'2014-09-03',0,0,0),(145,18,3,'2014-09-08',0,0,0),(146,18,3,'2014-09-10',1,0,0),(147,18,3,'2014-09-15',1,0,0),(148,18,3,'2014-09-17',1,0,0),(149,19,3,'2014-09-01',0,0,0),(150,19,3,'2014-09-03',0,0,0),(151,19,3,'2014-09-08',0,0,0),(152,19,3,'2014-09-10',0,0,0),(153,19,3,'2014-09-15',0,0,0),(154,19,3,'2014-09-17',0,0,0),(155,20,3,'2014-09-01',0,0,0),(156,20,3,'2014-09-03',0,0,0),(157,20,3,'2014-09-08',0,0,0),(158,20,3,'2014-09-10',0,0,0),(159,20,3,'2014-09-15',0,0,0),(160,20,3,'2014-09-17',0,0,0),(339,5,9,'2018-07-09',0,0,0),(340,5,9,'2018-07-16',0,0,0),(341,5,9,'2018-07-23',0,0,0),(342,5,9,'2018-07-30',0,0,0),(343,5,9,'2018-07-11',0,0,0),(344,5,9,'2018-07-18',0,0,0),(345,5,9,'2018-07-25',0,0,0),(346,5,9,'2018-08-01',0,0,0),(347,5,9,'2018-07-13',0,0,0),(348,5,9,'2018-07-20',0,0,0),(349,5,9,'2018-07-27',0,0,0),(350,5,9,'2018-08-03',0,0,0),(351,3,9,'2018-07-09',0,0,0),(352,3,9,'2018-07-16',0,0,0),(353,3,9,'2018-07-23',0,0,0),(354,3,9,'2018-07-30',0,0,0),(355,3,9,'2018-07-11',0,0,0),(356,3,9,'2018-07-18',0,0,0),(357,3,9,'2018-07-25',0,0,0),(358,3,9,'2018-08-01',0,0,0),(359,3,9,'2018-07-13',0,0,0),(360,3,9,'2018-07-20',0,0,0),(361,3,9,'2018-07-27',0,0,0),(362,3,9,'2018-08-03',0,0,0),(363,9,9,'2018-07-09',0,0,0),(364,9,9,'2018-07-16',0,0,0),(365,9,9,'2018-07-23',0,0,0),(366,9,9,'2018-07-30',0,0,0),(367,9,9,'2018-07-11',0,0,0),(368,9,9,'2018-07-18',0,0,0),(369,9,9,'2018-07-25',0,0,0),(370,9,9,'2018-08-01',0,0,0),(371,9,9,'2018-07-13',0,0,0),(372,9,9,'2018-07-20',0,0,0),(373,9,9,'2018-07-27',0,0,0),(374,9,9,'2018-08-03',0,0,0),(375,10,9,'2018-07-09',0,0,0),(376,10,9,'2018-07-16',0,0,0),(377,10,9,'2018-07-23',0,0,0),(378,10,9,'2018-07-30',0,0,0),(379,10,9,'2018-07-11',0,0,0),(380,10,9,'2018-07-18',0,0,0),(381,10,9,'2018-07-25',0,0,0),(382,10,9,'2018-08-01',0,0,0),(383,10,9,'2018-07-13',0,0,0),(384,10,9,'2018-07-20',0,0,0),(385,10,9,'2018-07-27',0,0,0),(386,10,9,'2018-08-03',0,0,0),(387,1,9,'2018-07-09',0,0,0),(388,1,9,'2018-07-16',0,0,0),(389,1,9,'2018-07-23',0,0,0),(390,1,9,'2018-07-30',0,0,0),(391,1,9,'2018-07-11',0,0,0),(392,1,9,'2018-07-18',0,0,0),(393,1,9,'2018-07-25',0,0,0),(394,1,9,'2018-08-01',0,0,0),(395,1,9,'2018-07-13',0,0,0),(396,1,9,'2018-07-20',0,0,0),(397,1,9,'2018-07-27',0,0,0),(398,1,9,'2018-08-03',0,0,0),(399,4,9,'2018-07-09',0,0,0),(400,4,9,'2018-07-16',0,0,0),(401,4,9,'2018-07-23',0,0,0),(402,4,9,'2018-07-30',0,0,0),(403,4,9,'2018-07-11',0,0,0),(404,4,9,'2018-07-18',0,0,0),(405,4,9,'2018-07-25',0,0,0),(406,4,9,'2018-08-01',0,0,0),(407,4,9,'2018-07-13',0,0,0),(408,4,9,'2018-07-20',0,0,0),(409,4,9,'2018-07-27',0,0,0),(410,4,9,'2018-08-03',0,0,0),(411,2,9,'2018-07-09',0,0,0),(412,2,9,'2018-07-16',0,0,0),(413,2,9,'2018-07-23',0,0,0),(414,2,9,'2018-07-30',0,0,0),(415,2,9,'2018-07-11',0,0,0),(416,2,9,'2018-07-18',0,0,0),(417,2,9,'2018-07-25',0,0,0),(418,2,9,'2018-08-01',0,0,0),(419,2,9,'2018-07-13',0,0,0),(420,2,9,'2018-07-20',0,0,0),(421,2,9,'2018-07-27',0,0,0),(422,2,9,'2018-08-03',0,0,0),(423,6,9,'2018-07-09',0,0,0),(424,6,9,'2018-07-16',0,0,0),(425,6,9,'2018-07-23',0,0,0),(426,6,9,'2018-07-30',0,0,0),(427,6,9,'2018-07-11',0,0,0),(428,6,9,'2018-07-18',0,0,0),(429,6,9,'2018-07-25',0,0,0),(430,6,9,'2018-08-01',0,0,0),(431,6,9,'2018-07-13',0,0,0),(432,6,9,'2018-07-20',0,0,0),(433,6,9,'2018-07-27',0,0,0),(434,6,9,'2018-08-03',0,0,0),(435,7,9,'2018-07-09',0,0,0),(436,7,9,'2018-07-16',0,0,0),(437,7,9,'2018-07-23',0,0,0),(438,7,9,'2018-07-30',0,0,0),(439,7,9,'2018-07-11',0,0,0),(440,7,9,'2018-07-18',0,0,0),(441,7,9,'2018-07-25',0,0,0),(442,7,9,'2018-08-01',0,0,0),(443,7,9,'2018-07-13',0,0,0),(444,7,9,'2018-07-20',0,0,0),(445,7,9,'2018-07-27',0,0,0),(446,7,9,'2018-08-03',1,0,0),(447,8,9,'2018-07-09',0,0,0),(448,8,9,'2018-07-16',0,0,0),(449,8,9,'2018-07-23',0,0,0),(450,8,9,'2018-07-30',0,0,0),(451,8,9,'2018-07-11',0,0,0),(452,8,9,'2018-07-18',0,0,0),(453,8,9,'2018-07-25',0,0,0),(454,8,9,'2018-08-01',0,0,0),(455,8,9,'2018-07-13',0,0,0),(456,8,9,'2018-07-20',0,0,0),(457,8,9,'2018-07-27',0,0,0),(458,8,9,'2018-08-03',0,0,0);
/*!40000 ALTER TABLE `student_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_grade`
--

DROP TABLE IF EXISTS `student_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_grade` (
  `student_id` int(10) unsigned NOT NULL,
  `task_id` int(10) unsigned NOT NULL,
  `grade` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`student_id`,`task_id`),
  KEY `fk_student_grade_task1_idx` (`task_id`),
  KEY `fk_student_grade_student1_idx` (`student_id`),
  CONSTRAINT `fk_student_grade_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_grade_task1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_grade`
--

LOCK TABLES `student_grade` WRITE;
/*!40000 ALTER TABLE `student_grade` DISABLE KEYS */;
INSERT INTO `student_grade` VALUES (1,1,46),(1,2,21),(1,9,0),(2,1,45),(2,2,0),(2,9,0),(3,1,48),(3,2,20),(3,9,0),(4,1,28),(4,2,28),(4,9,0),(5,1,50),(5,2,0),(5,9,0),(6,1,42),(6,2,0),(6,9,0),(7,1,45),(7,2,25),(7,9,0),(8,1,29),(8,2,0),(8,9,0),(9,1,38),(9,2,0),(9,9,0),(10,1,36),(10,2,0),(10,9,0),(11,3,0),(12,3,40),(13,3,0),(14,3,0),(15,3,38),(16,3,0),(17,3,0),(18,3,0),(19,3,0),(20,3,0);
/*!40000 ALTER TABLE `student_grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `student_gradebooks`
--

DROP TABLE IF EXISTS `student_gradebooks`;
/*!50001 DROP VIEW IF EXISTS `student_gradebooks`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `student_gradebooks` AS SELECT 
 1 AS `student_id`,
 1 AS `first_name`,
 1 AS `last_name`,
 1 AS `group_id`,
 1 AS `number`,
 1 AS `semester_id`,
 1 AS `academic_year`,
 1 AS `name`,
 1 AS `gradebook_id`,
 1 AS `subject`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gradebook_id` int(10) unsigned NOT NULL,
  `title` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `start_date` date NOT NULL,
  `task_length` tinyint(2) unsigned NOT NULL,
  `on_course_mon` tinyint(1) NOT NULL,
  `on_course_tue` tinyint(1) NOT NULL,
  `on_course_wed` tinyint(1) NOT NULL,
  `on_course_thu` tinyint(1) NOT NULL,
  `on_course_fri` tinyint(1) NOT NULL,
  `max_grade` tinyint(3) unsigned NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `fk_task_gradebook1_idx` (`gradebook_id`),
  CONSTRAINT `fk_task_gradebook1` FOREIGN KEY (`gradebook_id`) REFERENCES `gradebook` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,1,'Renaissance and Baroque','2015-09-28',3,0,1,0,1,0,50,'The Renaissance is the return yet again to valuation of the material world, and this paradigm shift is reflected in art forms, which show the corporeality of the human body, and the three-dimensional reality of landscape. Although textbooks periodize Western art by movements, as described above, they also do so by century, especially in Italian art. Many art historians give a nod to the historical importance of Italian Renaissance and Baroque art by referring to centuries in which it was prominent with the Italian terms: trecento for the fourteenth century, quattrocento for the fifteenth, cinquecento for the sixteenth, seicento for the seventeenth, and settecento for the eighteenth.'),(2,1,'Neoclassicalism to Realism','2015-10-19',2,0,1,0,1,0,30,'The 18th and 19th centuries included Neoclassicism, Romantic art, Academic art, and Realism in art. Art historians disagree when Modern art began, some tracing it as far back as Francisco Goya in the Napoleonic period, the mid-19th century with the industrial revolution or the late 19th century with the advent of Impressionism. The art movements of the late 19th through the early 21st centuries are too numerous to detail here, but can be broadly divided into two categories: Modernism and Contemporary art. The latter is sometimes referred to with another term, which has a subtly different connotation, Postmodern art.'),(3,2,'Greek Head','2014-09-01',3,1,0,1,0,0,40,'Greek sculpture from 800 to 300 BCE took early inspiration from Egyptian and Near Eastern monumental art, and over centuries evolved into a uniquely Greek vision of the art form. Greek artists would reach a peak of artistic excellence which captured the human form in a way never before seen and which was much copied. Greek sculptors were particularly concerned with proportion, poise, and the idealised perfection of the human body, and their figures in stone and bronze have become some of the most recognisable pieces of art ever produced by any civilization.'),(9,9,'Java Programming: Arrays, Lists, and Structured Data','2018-07-09',4,1,0,1,0,1,100,'Build on the software engineering skills you learned in тАЬJava Programming: Solving Problems with SoftwareтАЭ by learning new data structures. Use these data structures to build more complex programs that use JavaтАЩs object-oriented features. At the end of the course you will write an encryption program and a program to break your encryption algorithm.');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `task_view`
--

DROP TABLE IF EXISTS `task_view`;
/*!50001 DROP VIEW IF EXISTS `task_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `task_view` AS SELECT 
 1 AS `id`,
 1 AS `group_id`,
 1 AS `semester_id`,
 1 AS `gradebook_id`,
 1 AS `task_id`,
 1 AS `title`,
 1 AS `start_date`,
 1 AS `task_length`,
 1 AS `max_grade`,
 1 AS `student_id`,
 1 AS `first_name`,
 1 AS `last_name`,
 1 AS `grade`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `is_account_non_expired` bit(1) NOT NULL,
  `is_account_non_locked` bit(1) NOT NULL,
  `is_credentials_non_expired` bit(1) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('amber.heard@gmail.com','','','','','$2a$10$J2TgkMvMuZA87GvJ9Z2Z7.P9UUT5XYB.QtLFwiAwKbWW2lwnPxGCm'),('anastasiy.tovstik@gmail.com','','','','','$2a$10$wzDvMcyeIczUJ06a2kYY1udUExcl/bCWm3gmkFG1j66T15cU3pQ3q'),('john.doe@mail.com','','','','','$2a$10$ZZFBLp5WZtORg93prnW3G.ykbwpT7TYVK5SFDsYQV6pzDdcbsegim'),('john.doe2@mail.com','','','','','$2a$10$/FM.Mf9w015ztuFKlu4ASuYzlJoiIJXBepda7fcB4q9HyALRA2td.'),('woody.allen@gmail.com','','','','','$2a$10$blOWrCuOaHe498I26gfhKuwLgl42ZRYDNhDeiJYxAsJoyY45.IfcG');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `roles` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `FKfinmcawb90mtj05cpco76b963` (`user_email`),
  CONSTRAINT `FKfinmcawb90mtj05cpco76b963` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('john.doe@mail.com','ROLE_USER, ROLE_ADMIN'),('john.doe2@mail.com','ROLE_USER'),('amber.heard@gmail.com','ROLE_USER'),('woody.allen@gmail.com','ROLE_USER, ROLE_ADMIN'),('anastasiy.tovstik@gmail.com','ROLE_USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `attendance_view`
--

/*!50001 DROP VIEW IF EXISTS `attendance_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE VIEW `attendance_view` AS select uuid_short() AS `id`,`gradebook`.`group_id` AS `group_id`,`gradebook`.`semester_id` AS `semester_id`,`gradebook`.`id` AS `gradebook_id`,`task`.`id` AS `task_id`,`task`.`title` AS `title`,`task`.`start_date` AS `start_date`,`task`.`task_length` AS `task_length`,`student`.`id` AS `student_id`,`student`.`first_name` AS `first_name`,`student`.`last_name` AS `last_name`,`student_attendance`.`id` AS `attendance_id`,`student_attendance`.`present` AS `present`,`student_attendance`.`absent_with_reason` AS `absent_with_reason`,`student_attendance`.`absent` AS `absent`,`student_attendance`.`class_date` AS `class_date` from (((`student_attendance` join `task` on((`student_attendance`.`task_id` = `task`.`id`))) join `student` on((`student_attendance`.`student_id` = `student`.`id`))) join `gradebook` on((`task`.`gradebook_id` = `gradebook`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `lecturer_gradebooks`
--

/*!50001 DROP VIEW IF EXISTS `lecturer_gradebooks`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE VIEW `lecturer_gradebooks` AS select uuid_short() AS `id`,`lecturer`.`id` AS `lecturer_id`,`lecturer`.`first_name` AS `first_name`,`lecturer`.`last_name` AS `last_name`,`group`.`id` AS `group_id`,`group`.`number` AS `number`,`semester`.`id` AS `semester_id`,`semester`.`academic_year` AS `academic_year`,`semester`.`name` AS `name`,`gradebook`.`id` AS `gradebook_id`,`gradebook`.`subject` AS `subject` from ((((`lecturer` join `lecturer_has_gradebook` on((`lecturer`.`id` = `lecturer_has_gradebook`.`lecturer_id`))) join `gradebook` on((`lecturer_has_gradebook`.`gradebook_id` = `gradebook`.`id`))) join `group` on((`gradebook`.`group_id` = `group`.`id`))) join `semester` on((`gradebook`.`semester_id` = `semester`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `student_gradebooks`
--

/*!50001 DROP VIEW IF EXISTS `student_gradebooks`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE VIEW `student_gradebooks` AS select `student`.`id` AS `student_id`,`student`.`first_name` AS `first_name`,`student`.`last_name` AS `last_name`,`group`.`id` AS `group_id`,`group`.`number` AS `number`,`semester`.`id` AS `semester_id`,`semester`.`academic_year` AS `academic_year`,`semester`.`name` AS `name`,`gradebook`.`id` AS `gradebook_id`,`gradebook`.`subject` AS `subject` from (((`student` join `group` on((`student`.`group_id` = `group`.`id`))) join `gradebook` on((`group`.`id` = `gradebook`.`group_id`))) join `semester` on((`gradebook`.`semester_id` = `semester`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `task_view`
--

/*!50001 DROP VIEW IF EXISTS `task_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE VIEW `task_view` AS select uuid_short() AS `id`,`gradebook`.`group_id` AS `group_id`,`gradebook`.`semester_id` AS `semester_id`,`gradebook`.`id` AS `gradebook_id`,`task`.`id` AS `task_id`,`task`.`title` AS `title`,`task`.`start_date` AS `start_date`,`task`.`task_length` AS `task_length`,`task`.`max_grade` AS `max_grade`,`student`.`id` AS `student_id`,`student`.`first_name` AS `first_name`,`student`.`last_name` AS `last_name`,`student_grade`.`grade` AS `grade` from (((`student_grade` join `task` on((`student_grade`.`task_id` = `task`.`id`))) join `student` on((`student_grade`.`student_id` = `student`.`id`))) join `gradebook` on((`task`.`gradebook_id` = `gradebook`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-06  0:18:46
