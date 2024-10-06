-- MySQL dump 10.13  Distrib 8.0.38, for macos14 (arm64)
--
-- Host: identileafdb.cfsskm8gg0nz.us-east-2.rds.amazonaws.com    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `Tree`
--

DROP TABLE IF EXISTS `Tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Tree` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `scientificName` varchar(45) NOT NULL,
  `commonName` varchar(45) NOT NULL,
  `PlantType_ID` int NOT NULL,
  `LeafType_ID` int NOT NULL,
  `BarkType_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Tree_PlantType_idx` (`PlantType_ID`),
  KEY `fk_Tree_LeafType1_idx` (`LeafType_ID`),
  KEY `fk_Tree_BarkType1_idx` (`BarkType_ID`),
  CONSTRAINT `fk_Tree_BarkType1` FOREIGN KEY (`BarkType_ID`) REFERENCES `BarkType` (`ID`),
  CONSTRAINT `fk_Tree_LeafType1` FOREIGN KEY (`LeafType_ID`) REFERENCES `LeafType` (`ID`),
  CONSTRAINT `fk_Tree_PlantType` FOREIGN KEY (`PlantType_ID`) REFERENCES `PlantType` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tree`
--

LOCK TABLES `Tree` WRITE;
/*!40000 ALTER TABLE `Tree` DISABLE KEYS */;
INSERT INTO `Tree` VALUES (1,'Juniperus virginiana','Eastern Red Cedar',1,1,1),(2,'Pinus taeda','Loblolly Pine',1,2,2),(3,'Acer rubrum','Red Maple',1,3,NULL),(4,'Liquidambar styraiflua','Sweetgum',1,3,NULL),(5,'Quercus alba','White Oak',1,3,3),(6,'Quercus marilandica','Blackjack Oak',1,3,NULL),(7,'Ulmus americana','American Elm',1,3,NULL),(8,'Crataegus phaenopyrum','Washington Hawthorn',1,3,NULL),(9,'Carya glabra','Pignut Hickory',1,3,NULL),(10,'Nyssa sylvatica','Blackgum',1,3,NULL);
/*!40000 ALTER TABLE `Tree` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-06 13:29:18
