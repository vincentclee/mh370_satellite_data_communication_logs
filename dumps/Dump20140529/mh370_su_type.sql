CREATE DATABASE  IF NOT EXISTS `mh370` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mh370`;
-- MySQL dump 10.13  Distrib 5.1.73, for redhat-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: mh370
-- ------------------------------------------------------
-- Server version	5.1.73

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
-- Table structure for table `su_type`
--

DROP TABLE IF EXISTS `su_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `su_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `su_type`
--

LOCK TABLES `su_type` WRITE;
/*!40000 ALTER TABLE `su_type` DISABLE KEYS */;
INSERT INTO `su_type` VALUES (1,'0x15 - Log-on/Log-off Acknowledge'),(2,'Eleven Octet User Data'),(3,'Eight Octet User Data'),(4,'0x62 - Acknowledge User Data'),(5,'0x71 - User Data (ISU) - RLS'),(6,'Subsequent Signalling Unit'),(7,'0x62 - Acknowledge User Data (R-channel)'),(8,'0x22 - Access Request (R/T-Channel)'),(9,'0x51 - T-Channel Assignment'),(10,'Four Octet User Data'),(11,'0x53 - Reservation Forthcoming (RFC)'),(12,'0x61 - Request for Acknowledge (RQA) User Data'),(13,'0x10 - Log-on Request (ISU)/Log-on Flight Information(SSU)'),(14,'0x11 - Log-on Confirm'),(15,'0x40 - P-/R-Channel Control (ISU)'),(16,'0x41 - T-Channel Control (ISU)'),(17,'0x20 - Access Request/Call Announcement Telephone/Circuit-Mode Data'),(18,'0x33 - C-Channel Assignment (Regularity)'),(19,'0x30 - Call Progress - Test'),(20,'0x30 - Call Progress - Channel Release'),(21,'0x14 - Log Control - Log-on Interrogation'),(22,'0x60 - Telephony Acknowledge'),(23,'0x30 - Call Progress - Status Report');
/*!40000 ALTER TABLE `su_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-29 15:39:07
