-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: schooldb7staging
-- ------------------------------------------------------
-- Server version	8.0.41

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

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_cities_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'Αθήνα'),(3,'Βόλος'),(7,'Δράμα'),(10,'Ηράκλειο'),(5,'Θεσσαλονίκη'),(9,'Καλαμάτα'),(6,'Κέρκυρα'),(4,'Λάρισσα'),(2,'Πάτρα'),(8,'Πύργος'),(11,'Χανιά');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `vat` varchar(45) NOT NULL,
  `fathername` varchar(45) DEFAULT NULL,
  `phone_num` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `street` varchar(45) NOT NULL,
  `street_num` varchar(45) NOT NULL,
  `city_id` int NOT NULL,
  `zipcode` varchar(45) NOT NULL,
  `uuid` varchar(45) NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_students_cities_city_id_idx` (`city_id`),
  KEY `idx_students_lastname` (`lastname`) /*!80000 INVISIBLE */,
  KEY `idx_students_vat` (`vat`) /*!80000 INVISIBLE */,
  KEY `idx_students_uuid` (`uuid`),
  CONSTRAINT `fk_students_cities_city_id` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Γεώργιος','Παναγιώτου','567839201','Στέφανος','6987654321','george@gmail.com','Σταδίου','55',2,'11223','c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa','2025-03-11 12:52:00','2025-04-08 21:44:02'),(2,'Μαρία','Κολοκοτρώνη','908172635','Θεόδωρος','6976543210','maria@gmail.com','Αθήνας','34',6,'88990','d5e8f3a1-93c2-41cb-8f54-3a68d6a1b7d8','2025-03-11 12:52:00','2025-03-11 12:52:00'),(3,'Παναγιώτης','Σωτηρόπουλος','342156781','Δημήτρης','6932185476','panos@gmail.com','Λεωφόρος Συγγρού','88',4,'66778','e5f7d1c9-2bf6-4803-8f21-bdf987c1a2e9','2025-03-11 12:52:00','2025-04-08 21:44:17'),(4,'Ευαγγελία','Μαυρομιχάλη','129874651','Ιωάννης','6923456789','eva@gmail.com','Ομόνοιας','22',7,'23456','a9d3c4e1-73f1-45c6-8eb9-4d9f67a1b3c8','2025-03-11 12:52:00','2025-04-08 21:44:23'),(5,'Στέφανος','Καραγιάννης','783456121','Αντώνιος','6909876543','stefanos@gmail.com','Κηφισίας','101',3,'77788','b1c2d3e4-82b3-49f6-a9c2-3e7d8f1a9b6c','2025-03-11 12:52:00','2025-04-08 21:44:44'),(6,'Αικατερίνη','Βασιλείου','214365871','Νικόλαος','6987654098','katerina@gmail.com','Πανεπιστημίου','66',5,'54321','f9e8d7c6-5b4a-4321-9c87-6d5e4c3b2a1f','2025-03-11 12:52:00','2025-04-08 21:44:30'),(7,'Δημήτριος','Λεβέντης','908745321','Μιχαήλ','6935678901','dimitris@gmail.com','Βασιλίσσης Σοφίας','45',6,'33221','d3b7c8f9-1e2a-47f6-9c8b-5a4d6e7f2c1b','2025-03-11 12:52:00','2025-04-08 21:44:52'),(8,'Νικόλαος','Σταματίου','782341652','Πέτρος','6978123456','nikos@gmail.com','Ακαδημίας','29',2,'99887','c4d7e8f9-53b2-47c1-a9d8-6e5f4a3b2c1d','2025-03-11 12:52:00','2025-04-08 21:45:01'),(9,'Χριστίνα','Αναγνωστοπούλου','324567892','Εμμανουήλ','6956781234','christina@gmail.com','Λιοσίων','77',4,'76543','a7b9c8d3-2f1e-46a5-8d7c-4e3f6b2a1c9d','2025-03-11 12:52:00','2025-04-08 21:45:17'),(10,'Ιωάννης','Νικολαΐδης','876543219','Αλέξανδρος','6943218765','johnny@gmail.com','Μεσογείων','98',1,'45678','a1c2d3b4-12ef-34ab-56cd-7890f1e2d3c4','2025-03-11 12:52:00','2025-04-08 21:45:33'),(11,'Σοφία','Κυριακοπούλου','112358132','Χαράλαμπος','6967894321','sofia.k@gmail.com','Ελευθερίου Βενιζέλου','11',5,'34567','b2d4e6f8-65a3-42f7-a321-0f1e2d3c4b5a','2025-03-11 12:52:00','2025-04-08 21:45:44'),(12,'Αντώνης','Μιχαηλίδης','102938475','Νίκος','6931234567','antonis.m@gmail.com','Ανδρέα Παπανδρέου','17',7,'12345','c3f5e7d9-45b2-41c3-a5f7-3e4c2a1b6d8f','2025-03-11 12:52:00','2025-04-08 21:45:55');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `vat` varchar(45) NOT NULL,
  `fathername` varchar(45) DEFAULT NULL,
  `phone_num` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `street` varchar(45) DEFAULT NULL,
  `street_num` varchar(45) DEFAULT NULL,
  `zipcode` varchar(45) DEFAULT NULL,
  `city_id` int DEFAULT NULL,
  `uuid` char(36) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_teachers_lastname` (`lastname`) /*!80000 INVISIBLE */,
  KEY `idx_teachers_vat` (`vat`) /*!80000 INVISIBLE */,
  KEY `idx_teachers_city_id` (`city_id`),
  CONSTRAINT `fk_teachers_city_id` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (1,'Γεώργιος','Παναγιώτου','567839201','Στέφανος','6987654321','george@gmail.com','Σταδίου','55','11223',2,'e27aee5e-f447-4203-9b4a-7c638f422057','2025-03-17 18:01:18','2025-04-08 21:35:30'),(2,'Άννα','Γιαννούτσου','123456788','Κώ','1234567890','anna@gmail.com','Γεωργούτσου','12','67856',5,'68bc653a-abc7-4c30-8eb9-247b6b380d2e','2025-03-17 18:01:18','2025-04-09 07:05:45'),(3,'Μάκης','Καπέτης','987654321','Ευάγγελος','6935465768','makis@gmail.com','Πατησίων','76','10434',1,'94110e89-c23b-4c41-8c6b-f3e8b1fe9664','2025-03-17 18:01:18','2025-03-17 18:01:18'),(4,'Νίκη','Γιαννούτσου','918273645','Αθανάσιος','6934564890','niki@gmail.com','Λαμπρούτσου','12','65098',7,'31cff198-3280-46c3-bd32-6b4b1fb80ba6','2025-03-17 18:01:18','2025-03-17 18:01:18'),(5,'Δημήτρης','Αναγνωστόπουλος','567128394','Ιωάννης','6947890123','dimitris@gmail.com','Ακαδημίας','33','10559',3,'a1b2c3d4-e5f6-7890-1234-abcdef123456','2025-03-18 10:45:00','2025-04-08 14:30:00'),(6,'Ελένη','Μαυρομάτη','876543210','Χρήστος','6971234567','eleni@gmail.com','Ιπποκράτους','89','11472',4,'b2c3d4e5-f6a1-1234-5678-fedcba654321','2025-03-18 11:15:00','2025-04-08 16:20:00'),(7,'Πάνος','Κωνσταντίνου','134679852','Θεόδωρος','6954321876','panos@gmail.com','Ερμού','101','10563',6,'c3d4e5f6-a1b2-4321-8765-1234abcd5678','2025-03-18 13:00:00','2025-04-08 17:45:00'),(8,'Κατερίνα','Σπυροπούλου','246813579','Διονύσης','6981234987','katerina@gmail.com','Αλεξάνδρας','65','11471',2,'d4e5f6a1-b2c3-5432-9876-abcdef654321','2025-03-18 14:25:00','2025-04-08 18:30:00'),(9,'Χρήστος','Αθανασίου','314159265','Μιχαήλ','6939876543','christos@gmail.com','Πανεπιστημίου','23','10679',1,'e5f6a1b2-c3d4-6543-1234-fedcba123456','2025-03-18 15:00:00','2025-04-08 19:15:00'),(10,'Σοφία','Καραγιάννη','192837465','Νικόλαος','6976543210','sofia@gmail.com','Κηφισίας','44','15125',5,'f6a1b2c3-d4e5-7654-2345-abcdef789012','2025-03-18 16:30:00','2025-04-08 20:00:00'),(11,'Αλέξανδρος','Βασιλείου','102938475','Αριστείδης','6923456789','alex@gmail.com','Αμαλίας','5','10557',3,'a2b3c4d5-e6f7-8765-3456-abc123def456','2025-03-18 17:45:00','2025-04-08 20:30:00'),(12,'Μαρία','Παπαδοπούλου','665544332','Γρηγόριος','6909876543','maria@gmail.com','Μητροπόλεως','9','10556',7,'b3c4d5e6-f7a2-9876-4567-fedc321cba98','2025-03-18 18:30:00','2025-04-08 21:00:00');
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `role` enum('ADMIN','LIGHT_ADMIN') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'thanos@aueb.gr','$2a$12$aHazLO91r.h7IUSzLfXfhejbBZyTRg2HIHwmXdhzKrMLOjLqL0zZy','ADMIN'),(2,'anna@gmail.com','$2a$12$.j.oBMUr8T1P8kdTWydAIOBr7SmOofxSwF/wlkZSCs5higLC7hVpO','LIGHT_ADMIN'),(3,'niki@aueb.gr','$2a$12$bI/ti.LEbS5BwzzQL0OsXefEQRT3m6wuLmFkyluUYtlKbkVEJStey','ADMIN'),(4,'test@aueb.gr','$2a$12$kiFcQXHQvrIUYSPLEcDBUefOKeJO4N/s0bf/6j0vBOl4aXYNkZa2K','ADMIN'),(5,'test2@aueb.gr','$2a$12$HuDly.XaGIT8I6c7o6BU6OhvQJVK6u8HeyJv4Wz3AjQqs7jBhxt9e','LIGHT_ADMIN');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-09 10:31:01
