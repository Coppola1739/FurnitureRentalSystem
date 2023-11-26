-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: 160.10.217.6    Database: cs3230f23c
-- ------------------------------------------------------
-- Server version	8.0.32

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

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'f6b27acf-3a68-11eb-8944-000c29f23569:1-44345';

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('Cabinet'),('Chair'),('Sofa'),('Table');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_num` char(10) NOT NULL,
  `pid` int DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`employee_num`),
  KEY `fk_employee_personal_information` (`pid`),
  KEY `fk_employee_user` (`username`),
  CONSTRAINT `fk_employee_personal_information` FOREIGN KEY (`pid`) REFERENCES `personal_information` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_employee_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('0000001001',38,'bobmiller'),('0000001002',37,'charliebrown'),('1878351428',59,'adminTest1'),('5076195194',58,'gav'),('7336034794',60,'adminTest2'),('8164102677',62,'gavinc'),('9654815031',61,'gavTest');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furniture`
--

DROP TABLE IF EXISTS `furniture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `furniture` (
  `furniture_id` char(10) NOT NULL,
  `style_name` varchar(20) DEFAULT NULL,
  `category_name` varchar(20) DEFAULT NULL,
  `rental_rate` decimal(6,2) NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`furniture_id`),
  KEY `fk_furniture_category` (`category_name`),
  KEY `fk_furniture_style` (`style_name`),
  CONSTRAINT `fk_furniture_category` FOREIGN KEY (`category_name`) REFERENCES `category` (`name`),
  CONSTRAINT `fk_furniture_style` FOREIGN KEY (`style_name`) REFERENCES `style` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furniture`
--

LOCK TABLES `furniture` WRITE;
/*!40000 ALTER TABLE `furniture` DISABLE KEYS */;
INSERT INTO `furniture` VALUES ('1000000001','Modern','Sofa',29.99,6),('1000000002','Modern','Chair',19.99,16),('1000000003','Traditional','Table',15.99,29),('1000000004','Scandinavian','Sofa',34.99,8),('1000000005','Scandinavian','Chair',22.99,14),('1000000006','Rustic','Table',17.99,21),('1000000007','Traditional','Cabinet',25.99,2),('1000000008','Modern','Cabinet',27.99,2);
/*!40000 ALTER TABLE `furniture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` char(10) NOT NULL,
  `pid` int DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`member_id`),
  KEY `fk_member_personal_information` (`pid`),
  KEY `fk_member_user` (`username`),
  CONSTRAINT `fk_member_personal_information` FOREIGN KEY (`pid`) REFERENCES `personal_information` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_member_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('0000000001',41,'johndoe'),('0000000002',40,'janesmith'),('0000000003',39,'alicejohnson'),('2095678923',57,'gavin'),('6230222759',63,'alexjonesman'),('8011263749',55,'awdwa'),('8852728425',56,'wajfe'),('9408709410',64,'awdwaddd');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_information`
--

DROP TABLE IF EXISTS `personal_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personal_information` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `f_name` varchar(50) NOT NULL,
  `l_name` varchar(50) NOT NULL,
  `b_date` date NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone_num` char(13) NOT NULL,
  `street_add` varchar(80) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zip` char(5) DEFAULT NULL,
  `register_date` date DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_information`
--

LOCK TABLES `personal_information` WRITE;
/*!40000 ALTER TABLE `personal_information` DISABLE KEYS */;
INSERT INTO `personal_information` VALUES (37,'John','Dork','1990-05-10','Male','1234567890','123 Elm St','Anytown','CA','12345','2022-01-10'),(38,'Jane','Smith','1985-08-15','Female','0987654321','456 Oak St','Bigcity','TX','67890','2022-02-20'),(39,'Denji','Chainsaw','2016-09-28','Female','0000004664','Lives on the streets','Tokyo','AK','12345','2022-03-30'),(40,'Henry','Kissinger','2016-09-28','Female','0000000011','123 awdwa','123 wad','AK','12345','2022-04-10'),(41,'Rudy','Giuliani','2016-09-28','Female','0000000000','123 awdwa','123 wad','AK','12345','2022-05-20'),(55,'JKL','The Third','2016-09-14','Female','0200000987','1600 Pennsylvania','D.C.','AK','12347','2023-10-25'),(56,'Spencer','Crittenden','2016-09-28','Female','0000000000','123 awdwa','123 wad','AK','12345','2023-10-31'),(57,'Gavin','Coppola','2023-11-08','Male','6787131831','020 B','Carrollton','GA','30117','2023-11-12'),(58,'Gavin','Coppola','2023-11-01','Male','6722222222','23','C','AK','12344','2023-11-12'),(59,'gavin','coppola','2023-11-01','Male','2222222222','232','c','AK','30333','2023-11-12'),(60,'gavin','Coppola','2023-11-13','Male','2222222222','asdasd','add','AK','12312','2023-11-12'),(61,'Gavin','Coppola','2023-10-31','Male','6722222222','23','123','AL','12313','2023-11-12'),(62,'Sue','Coppola','2023-11-01','Male','5555555555','23','car','AK','13003','2023-11-13'),(63,'test','manddd','2023-10-02','Male','1112223333','1231 AWDAWDwd','1231 ADWaddd','AK','31231','2023-11-23'),(64,'awdwa','awdwa','2023-10-30','Female','1231231233','123 awdaw','awdawd','AZ','12345','2023-11-24');
/*!40000 ALTER TABLE `personal_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental`
--

DROP TABLE IF EXISTS `rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rental` (
  `rental_id` char(10) NOT NULL,
  `member_id` char(10) DEFAULT NULL,
  `employee_num` char(10) DEFAULT NULL,
  `start_date` date NOT NULL,
  `due_date` date DEFAULT NULL,
  PRIMARY KEY (`rental_id`),
  KEY `fk_rental_member` (`member_id`),
  KEY `fk_rental_employee` (`employee_num`),
  CONSTRAINT `fk_rental_employee` FOREIGN KEY (`employee_num`) REFERENCES `employee` (`employee_num`),
  CONSTRAINT `fk_rental_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental`
--

LOCK TABLES `rental` WRITE;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
INSERT INTO `rental` VALUES ('RNT0000001','6230222759','0000001001','2023-10-30','2023-12-05'),('RNT0000002','6230222759','0000001001','2023-10-30','2023-11-13'),('RNT0000003','6230222759','0000001001','2023-10-30','2023-11-01'),('RNT0000004','6230222759','0000001001','2023-10-29','2023-11-10'),('RNT0000005','2095678923','0000001001','2023-11-01','2023-12-01'),('RNT0000006','2095678923','0000001001','2023-11-05','2023-12-01'),('RNT0000007','2095678923','0000001001','2023-11-06','2023-12-01'),('RNT0000008','2095678923','0000001001','2023-11-06','2023-12-01');
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental_item`
--

DROP TABLE IF EXISTS `rental_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rental_item` (
  `rental_id` char(10) NOT NULL,
  `furniture_id` char(10) NOT NULL,
  `quantity` int DEFAULT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`rental_id`,`furniture_id`),
  KEY `fk_rental_item_furniture` (`furniture_id`),
  CONSTRAINT `fk_rental_item_furniture` FOREIGN KEY (`furniture_id`) REFERENCES `furniture` (`furniture_id`),
  CONSTRAINT `fk_rental_item_rental` FOREIGN KEY (`rental_id`) REFERENCES `rental` (`rental_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental_item`
--

LOCK TABLES `rental_item` WRITE;
/*!40000 ALTER TABLE `rental_item` DISABLE KEYS */;
INSERT INTO `rental_item` VALUES ('RNT0000001','1000000001',1,29.99),('RNT0000001','1000000002',3,19.99),('RNT0000001','1000000003',1,15.99),('RNT0000001','1000000004',1,34.99),('RNT0000001','1000000005',2,22.99),('RNT0000001','1000000006',1,17.99),('RNT0000001','1000000007',1,25.99),('RNT0000002','1000000002',2,19.99),('RNT0000002','1000000003',2,15.99),('RNT0000002','1000000005',2,22.99),('RNT0000003','1000000002',4,19.99),('RNT0000003','1000000003',5,15.99),('RNT0000003','1000000004',1,34.99),('RNT0000003','1000000005',2,22.99),('RNT0000003','1000000006',1,17.99),('RNT0000004','1000000001',1,29.99),('RNT0000005','1000000005',1,22.99),('RNT0000005','1000000006',1,17.99),('RNT0000006','1000000003',1,15.99),('RNT0000006','1000000007',1,25.99),('RNT0000007','1000000005',1,22.99),('RNT0000007','1000000006',1,17.99),('RNT0000008','1000000006',1,17.99);
/*!40000 ALTER TABLE `rental_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return`
--

DROP TABLE IF EXISTS `return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return` (
  `return_id` char(10) NOT NULL,
  `member_id` char(10) DEFAULT NULL,
  `employee_num` char(10) DEFAULT NULL,
  PRIMARY KEY (`return_id`),
  KEY `fk_return_member` (`member_id`),
  KEY `fk_return_employee` (`employee_num`),
  CONSTRAINT `fk_return_employee` FOREIGN KEY (`employee_num`) REFERENCES `employee` (`employee_num`),
  CONSTRAINT `fk_return_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return`
--

LOCK TABLES `return` WRITE;
/*!40000 ALTER TABLE `return` DISABLE KEYS */;
INSERT INTO `return` VALUES ('0000000000','6230222759','0000001001'),('0000000001','6230222759','0000001001'),('0000000002','6230222759','0000001001'),('0000000003','6230222759','0000001001'),('0000000004','6230222759','0000001001'),('0000000005','2095678923','0000001002'),('0000000006','2095678923','0000001002'),('0000000007','2095678923','0000001001');
/*!40000 ALTER TABLE `return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_item`
--

DROP TABLE IF EXISTS `return_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_item` (
  `rental_id` char(10) NOT NULL,
  `furniture_id` char(10) NOT NULL,
  `return_id` char(10) NOT NULL,
  `fine_amount` decimal(10,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`rental_id`,`furniture_id`,`return_id`),
  KEY `fk_return_item_furniture` (`furniture_id`),
  KEY `fk_return_item_return` (`return_id`),
  CONSTRAINT `fk_return_item_furniture` FOREIGN KEY (`furniture_id`) REFERENCES `furniture` (`furniture_id`),
  CONSTRAINT `fk_return_item_rental` FOREIGN KEY (`rental_id`) REFERENCES `rental` (`rental_id`),
  CONSTRAINT `fk_return_item_return` FOREIGN KEY (`return_id`) REFERENCES `return` (`return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_item`
--

LOCK TABLES `return_item` WRITE;
/*!40000 ALTER TABLE `return_item` DISABLE KEYS */;
INSERT INTO `return_item` VALUES ('RNT0000001','1000000001','0000000000',0.00,1),('RNT0000001','1000000002','0000000000',0.00,3),('RNT0000001','1000000003','0000000000',0.00,1),('RNT0000001','1000000004','0000000000',0.00,1),('RNT0000001','1000000005','0000000000',0.00,1),('RNT0000001','1000000005','0000000001',0.00,1),('RNT0000001','1000000005','0000000004',0.00,2),('RNT0000001','1000000006','0000000001',0.00,1),('RNT0000001','1000000007','0000000000',0.00,1),('RNT0000002','1000000002','0000000002',439.78,2),('RNT0000002','1000000003','0000000002',351.78,2),('RNT0000002','1000000005','0000000002',505.78,2),('RNT0000003','1000000002','0000000003',1839.08,4),('RNT0000003','1000000003','0000000003',1838.85,5),('RNT0000003','1000000004','0000000003',804.77,1),('RNT0000003','1000000005','0000000003',1057.54,2),('RNT0000003','1000000006','0000000003',413.77,1),('RNT0000005','1000000005','0000000005',0.00,1),('RNT0000005','1000000006','0000000005',0.00,1),('RNT0000006','1000000003','0000000006',0.00,1),('RNT0000006','1000000007','0000000006',0.00,1),('RNT0000007','1000000005','0000000007',0.00,1),('RNT0000007','1000000006','0000000006',0.00,1);
/*!40000 ALTER TABLE `return_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('disabled'),('employee'),('manager'),('member');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `style`
--

DROP TABLE IF EXISTS `style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `style` (
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `style`
--

LOCK TABLES `style` WRITE;
/*!40000 ALTER TABLE `style` DISABLE KEYS */;
INSERT INTO `style` VALUES ('Modern'),('Rustic'),('Scandinavian'),('Traditional');
/*!40000 ALTER TABLE `style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(45) NOT NULL,
  `password` varchar(72) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `fk_login_role` (`role`),
  CONSTRAINT `fk_login_role` FOREIGN KEY (`role`) REFERENCES `role` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('adminTest1','$2a$10$CUB6pak90Mm9/A0pyy6m/u3ZfujvdVq0AIVhaAPjmasXF.ykDWJVK','disabled'),('adminTest2','$2a$10$CUB6pak90Mm9/A0pyy6m/ube.4czzn9YMZh0Rb/S1YXUcW7Wg8Y5q','disabled'),('alexjonesman','$2a$10$CUB6pak90Mm9/A0pyy6m/uKSr1xbfhURZHjmWC7JCvRaauW11mEdO','member'),('alicejohnson','$2a$10$CUB6pak90Mm9/A0pyy6m/uQ6BGDhsliNGpLCeFZW6TnpuElfGG9mO','member'),('awdwa','$2a$10$CUB6pak90Mm9/A0pyy6m/uhoO7htGQshOoBnrnz3OFcvrOxQVdaBm','member'),('awdwaddd','$2a$10$CUB6pak90Mm9/A0pyy6m/uKSr1xbfhURZHjmWC7JCvRaauW11mEdO','member'),('bobmiller','$2a$10$CUB6pak90Mm9/A0pyy6m/u9kv6eJQiNfzwfnZvLK6gWYj5QZUcAsS','employee'),('charliebrown','$2a$10$CUB6pak90Mm9/A0pyy6m/uPJuQ80MMU9eWs/tYmKGkyyvMVQUcuFW','manager'),('gav','$2a$10$CUB6pak90Mm9/A0pyy6m/uqGU2Ex091XhN0YGG2IucT4lDkwOBRmS','disabled'),('gavin','$2a$10$CUB6pak90Mm9/A0pyy6m/u9VTYIlpYnDmGA8iCNP7HYXIWWefECgu','employee'),('gavinc','$2a$10$CUB6pak90Mm9/A0pyy6m/ugCE9Z2MbZ6drvVV.tLZ0qSsviHxy/C2','disabled'),('gavTest','$2a$10$CUB6pak90Mm9/A0pyy6m/ud8tpMIZO9h20eFGfT7cklEv5Olg9n7y','disabled'),('janesmith','$2a$10$CUB6pak90Mm9/A0pyy6m/u2G0ClquO/WjJIwYjIjkJTGhuXWwXtz6','member'),('johndoe','$2a$10$CUB6pak90Mm9/A0pyy6m/u9VTYIlpYnDmGA8iCNP7HYXIWWefECgu','member'),('wajfe','$2a$10$CUB6pak90Mm9/A0pyy6m/uhoO7htGQshOoBnrnz3OFcvrOxQVdaBm','member');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cs3230f23c'
--
/*!50003 DROP PROCEDURE IF EXISTS `AddRental` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `AddRental`(
IN p_member_id CHAR(10),
    IN p_employee_num CHAR(10),
    IN p_start_date DATE,
    IN p_end_date DATE,
    IN p_furniture_id CHAR(10),
    IN p_quantity INT,
    IN p_cost DECIMAL(6, 2)
    )
BEGIN
DECLARE v_new_return_id CHAR(10);

    INSERT INTO `Return`(member_id, employee_num)
    VALUES (p_member_id, p_employee_num);

    SET v_new_return_id = LAST_INSERT_ID();

    INSERT INTO Return_Item(rental_id, furniture_id, return_id, fine_amount, quantity)
    VALUES (p_rental_id, p_furniture_id, v_new_return_id, p_fine_amount, p_quantity);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddRentalWithMultipleItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `AddRentalWithMultipleItems`(
IN in_member_id CHAR(10), 
    IN in_employee_num CHAR(10), 
    IN in_start_date DATE,
    IN in_due_date DATE,
    IN furniture_ids TEXT,
    IN quantities TEXT,
    IN costs TEXT)
BEGIN
DECLARE v_rental_id CHAR(10);
    DECLARE cur_index INT DEFAULT 1;
    DECLARE cur_furniture_id CHAR(10);
    DECLARE cur_quantity INT;
    DECLARE cur_cost DECIMAL(6,2);
    
SELECT 
    IFNULL(MAX(rental_id), 'RNT0000000')
INTO v_rental_id FROM
    Rental;
    SET v_rental_id = CONCAT('RNT', LPAD(CAST(SUBSTRING(v_rental_id, 4) AS UNSIGNED) + 1, 7, '0'));

    INSERT INTO Rental(rental_id, member_id, employee_num, start_date, due_date)
    VALUES (v_rental_id, in_member_id, in_employee_num, in_start_date, in_due_date);

    WHILE cur_index <= LENGTH(furniture_ids) - LENGTH(REPLACE(furniture_ids, ',', '')) + 1 DO
        SET cur_furniture_id = SUBSTRING_INDEX(SUBSTRING_INDEX(furniture_ids, ',', cur_index), ',', -1);
        SET cur_quantity = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(quantities, ',', cur_index), ',', -1) AS SIGNED);
        SET cur_cost = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(costs, ',', cur_index), ',', -1) AS DECIMAL(6,2));

        INSERT INTO Rental_Item(rental_id, furniture_id, quantity, cost)
        VALUES (v_rental_id, cur_furniture_id, cur_quantity, cur_cost);

        SET cur_index = cur_index + 1;
    END WHILE;

SELECT v_rental_id AS rental_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddReturn` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `AddReturn`(
	IN p_return_id CHAR(10),
	IN p_member_id CHAR(10),
    IN p_employee_num CHAR(10)
    -- IN p_start_date DATE,
    -- IN p_end_date DATE,
    -- IN p_furniture_id CHAR(10),
    -- IN p_quantity INT,
    -- IN p_cost DECIMAL(6, 2)
    )
BEGIN
DECLARE v_new_return_id CHAR(10);

    INSERT INTO `Return`(return_id, member_id, employee_num)
    VALUES (p_return_id, p_member_id, p_employee_num);

    SET v_new_return_id = LAST_INSERT_ID();

    -- INSERT INTO Return_Item(rental_id, furniture_id, return_id, fine_amount, quantity)
    -- VALUES (p_rental_id, p_furniture_id, v_new_return_id, p_fine_amount, p_quantity);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ChangeEmployeeRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `ChangeEmployeeRole`(
    IN p_username VARCHAR(20),
    IN p_role VARCHAR(20)
)
BEGIN
    UPDATE `user`
    JOIN employee ON employee.username = `user`.username
    SET
		role = p_role
    WHERE
        `employee`.username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FetchRentalDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `FetchRentalDetails`(IN in_rental_id CHAR(10))
BEGIN
SELECT 
        r.rental_id AS 'Rental ID',
        m.member_id AS 'Member ID',
        CONCAT(pi.f_name, " ", pi.l_name) AS 'Member_Fullname',
        e.employee_num AS 'Employee ID',
        CONCAT(epi.f_name, " ", epi.l_name) AS 'Employee_Fullname',
        r.start_date AS 'Start Date',
        r.due_date AS 'Due Date',
        ri.furniture_id AS 'Furniture ID',
        f.style_name AS 'Style Name',
        f.category_name AS 'Category Name',
        ri.quantity AS 'Quantity',
        ri.cost AS 'Cost Per Item',
        (ri.cost * ri.quantity) AS 'Total Cost for Item'
    FROM 
        Rental r
    JOIN Rental_Item ri ON r.rental_id = ri.rental_id
    JOIN Furniture f ON ri.furniture_id = f.furniture_id
    JOIN Member m ON r.member_id = m.member_id
    JOIN personal_information pi ON m.pid = pi.pid
    JOIN Employee e ON r.employee_num = e.employee_num
    JOIN personal_information epi ON e.pid = epi.pid
    WHERE 
        r.rental_id = in_rental_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetEmployeeRentalCountAndAmount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetEmployeeRentalCountAndAmount`(IN p_username VARCHAR(45))
BEGIN
    SELECT 
        COUNT(DISTINCT rental.rental_id) AS RentalCount,
        IFNULL(SUM(rental_item.cost * rental_item.quantity), 0) AS TotalAmount
    FROM
        rental
    INNER JOIN
        employee ON rental.employee_num = employee.employee_num
    LEFT JOIN
        rental_item ON rental.rental_id = rental_item.rental_id
    WHERE
        employee.username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetEmployeeReturnCountByUsername` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetEmployeeReturnCountByUsername`(IN p_username VARCHAR(45))
BEGIN
    SELECT
        employee.employee_num,
        CONCAT(personal_information.f_name, ' ', personal_information.l_name) AS EmployeeName,
        COUNT(`return`.return_id) AS ReturnCount
    FROM
        employee
    INNER JOIN
        `return` ON employee.employee_num = `return`.employee_num
    INNER JOIN
        personal_information ON employee.pid = personal_information.pid
    WHERE
        employee.username = p_username
    GROUP BY
        employee.employee_num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetEmployeeTotalFines` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetEmployeeTotalFines`(IN p_username VARCHAR(45))
BEGIN
    SELECT
        SUM(ri.fine_amount) AS TotalFines
    FROM
        employee e
    INNER JOIN
        `return` r ON e.employee_num = r.employee_num
    INNER JOIN
        return_item ri ON r.return_id = ri.return_id
    WHERE
        e.username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetUnreturnedItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `GetUnreturnedItems`(in p_rental_id CHAR(10))
BEGIN
SELECT
    ri.rental_id,
    ri.furniture_id,
    ri.cost,
    ri.quantity AS rental_quantity,
    COALESCE(roi.quantity, 0) AS return_quantity,
    ri.quantity - COALESCE(roi.quantity, 0) AS quantity_difference
FROM
    rental_item ri
LEFT JOIN
    return_item roi ON ri.rental_id = roi.rental_id AND ri.furniture_id = roi.furniture_id
WHERE
    ri.rental_id = p_rental_id
    HAVING
    quantity_difference > 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `UpdateEmployee`(
    IN p_employee_num VARCHAR(20),
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_bDate DATE,
    IN p_gender VARCHAR(10),
    IN p_phoneNum VARCHAR(13),
    IN p_streetAdd VARCHAR(80),
    IN p_city VARCHAR(50),
    IN p_state VARCHAR(20),
    IN p_zip VARCHAR(5)
)
BEGIN
    UPDATE personal_information
    JOIN employee ON personal_information.pid = employee.pid
    SET
        f_name = p_fName,
        l_name = p_lName,
        b_date = p_bDate,
        gender = p_gender,
        phone_num = p_phoneNum,
        street_add = p_streetAdd,
        city = p_city,
        state = p_state,
        zip = p_zip
    WHERE
        `employee`.employee_num = p_employee_num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`cs3230f23c`@`%` PROCEDURE `UpdateUser`(
    IN p_memberId VARCHAR(20),
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_bDate DATE,
    IN p_gender VARCHAR(10),
    IN p_phoneNum VARCHAR(13),
    IN p_streetAdd VARCHAR(80),
    IN p_city VARCHAR(50),
    IN p_state VARCHAR(20),
    IN p_zip VARCHAR(5)
)
BEGIN
    UPDATE personal_information
    JOIN member ON personal_information.pid = member.pid
    SET
        f_name = p_fName,
        l_name = p_lName,
        b_date = p_bDate,
        gender = p_gender,
        phone_num = p_phoneNum,
        street_add = p_streetAdd,
        city = p_city,
        state = p_state,
        zip = p_zip
    WHERE
        `member`.member_id = p_memberId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-26 12:16:39
