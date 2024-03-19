-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.7.33

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
-- Table structure for table `sys_login_log`
--

DROP TABLE IF EXISTS `sys_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `status` varchar(50) DEFAULT NULL COMMENT '登录状态（online:在线，登录初始状态，方便统计在线人数；login:退出登录后将online置为login；logout:退出登录）',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2803 DEFAULT CHARSET=utf8 COMMENT='系统登录日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login_log`
--

LOCK TABLES `sys_login_log` WRITE;
/*!40000 ALTER TABLE `sys_login_log` DISABLE KEYS */;
INSERT INTO `sys_login_log` VALUES (1,'admin','login','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:16',NULL,NULL),(2,'admin','logout','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:17',NULL,NULL),(3,'admin','login','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:18',NULL,NULL),(4,'admin','logout','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:20',NULL,NULL),(5,'admin','login','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:20',NULL,NULL),(6,'admin','logout','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:21',NULL,NULL),(7,'admin','login','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:22',NULL,NULL),(8,'admin','login','0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:23','admin','2019-01-21 10:15:43'),(2798,'admin','logout','0:0:0:0:0:0:0:1','admin','2019-01-21 10:15:43',NULL,NULL),(2799,'admin','login','0:0:0:0:0:0:0:1','admin','2019-01-21 10:15:43','admin','2019-01-21 10:16:54'),(2800,'admin','logout','0:0:0:0:0:0:0:1','admin','2019-01-21 10:16:54',NULL,NULL),(2801,'admin','login','0:0:0:0:0:0:0:1','admin','2019-01-21 10:16:54','admin','2019-01-22 14:43:09');
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-16 11:52:54
