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
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `nick_name` varchar(150) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(150) DEFAULT NULL COMMENT '头像',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(40) DEFAULT NULL COMMENT '加密盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','超管',NULL,'bd1718f058d8a02468134432b8656a86','YzcmCZNvbXocrsz9dm8e','admin@qq.com','13612345678',1,4,'admin','2018-08-14 11:11:11','admin','2018-08-14 11:11:11',0),(22,'liubei','刘备',NULL,'373d507e01bddb2063bed4d9de7f6640','f9cb48a79ed34df3809e','test@qq.com','13889700023',1,7,'admin','2018-09-23 03:43:00','admin','2019-01-09 19:41:13',0),(23,'zhaoyun','赵云',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,7,'admin','2018-09-23 19:43:44','admin','2018-09-23 19:43:52',0),(24,'zhugeliang','诸葛亮',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',7,11,'admin','2018-09-23 19:44:23','admin','2018-09-23 19:44:29',0),(25,'caocao','曹操',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,8,'admin','2018-09-23 19:45:32','admin','2019-01-10 17:59:14',0),(26,'dianwei','典韦',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,10,'admin','2018-09-23 19:45:48','admin','2018-09-23 19:45:57',0),(27,'xiahoudun','夏侯惇',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,8,'admin','2018-09-23 19:46:09','admin','2018-09-23 19:46:17',0),(28,'xunyu','荀彧',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,10,'admin','2018-09-23 19:46:38','admin','2018-11-04 15:33:17',0),(29,'sunquan','孙权',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,10,'admin','2018-09-23 19:46:54','admin','2018-09-23 19:47:03',0),(30,'zhouyu','周瑜',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,11,'admin','2018-09-23 19:47:28','admin','2018-09-23 19:48:04',0),(31,'luxun','陆逊',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,11,'admin','2018-09-23 19:47:44','admin','2018-09-23 19:47:58',0),(32,'huanggai','黄盖',NULL,'fd80ebd493a655608dc893a9f897d845','YzcmCZNvbXocrsz9dm8e','test@qq.com','13889700023',1,11,'admin','2018-09-23 19:48:38','admin','2018-09-23 19:49:02',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-16 11:52:55
