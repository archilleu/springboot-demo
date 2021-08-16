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
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2897 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES (1,'admin',NULL,'com.hoya.admin.sevice.impl.SysDictServiceImpl.findPage()','{\"columnFilters\":{\"label\":{\"name\":\"label\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',4,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:16',NULL,NULL),(2,'admin',NULL,'com.hoya.admin.sevice.impl.SysRoleServiceImpl.findPage()','{\"columnFilters\":{\"name\":{\"name\":\"name\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',4,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:17',NULL,NULL),(3,'admin',NULL,'com.hoya.admin.sevice.impl.SysUserServiceImpl.findPage()','{\"columnFilters\":{\"name\":{\"name\":\"name\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',36,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:18',NULL,NULL),(4,'admin',NULL,'com.hoya.admin.sevice.impl.SysDictServiceImpl.findPage()','{\"columnFilters\":{\"label\":{\"name\":\"label\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',4,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:20',NULL,NULL),(5,'admin',NULL,'com.hoya.admin.sevice.impl.SysRoleServiceImpl.findPage()','{\"columnFilters\":{\"name\":{\"name\":\"name\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',4,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:20',NULL,NULL),(6,'admin',NULL,'com.hoya.admin.sevice.impl.SysUserServiceImpl.findPage()','{\"columnFilters\":{\"name\":{\"name\":\"name\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',27,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:21',NULL,NULL),(7,'admin',NULL,'com.hoya.admin.sevice.impl.SysRoleServiceImpl.findPage()','{\"columnFilters\":{\"name\":{\"name\":\"name\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',4,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:22',NULL,NULL),(8,'admin',NULL,'com.hoya.admin.sevice.impl.SysDictServiceImpl.findPage()','{\"columnFilters\":{\"label\":{\"name\":\"label\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}',4,'0:0:0:0:0:0:0:1','admin','2018-09-23 19:54:23',NULL,NULL),(2798,NULL,NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findByName()','\"admin\"',361,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2799,NULL,NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findByName()','\"admin\"',4,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2800,NULL,NULL,'com.hoya.admin.service.impl.SysMenuServiceImpl.findByUser()','\"admin\"',43,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2801,NULL,NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findPermissions()','\"admin\"',49,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2802,NULL,NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findByName()','\"admin\"',221,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2803,NULL,NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findByName()','\"admin\"',3,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2804,NULL,NULL,'com.hoya.admin.service.impl.SysMenuServiceImpl.findByUser()','\"admin\"',37,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2805,NULL,NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findPermissions()','\"admin\"',43,'0:0:0:0:0:0:0:1',NULL,NULL,NULL,NULL),(2806,'admin',NULL,'com.hoya.admin.service.impl.SysLoginLogServiceImpl.writeLoginLog()','\"admin\"',138,'0:0:0:0:0:0:0:1','admin','2019-01-21 10:15:43',NULL,NULL),(2807,'admin',NULL,'com.hoya.admin.service.impl.SysMenuServiceImpl.findTree()','\"admin\"',15,'0:0:0:0:0:0:0:1','admin','2019-01-21 10:15:44',NULL,NULL),(2808,'admin',NULL,'com.hoya.admin.service.impl.SysMenuServiceImpl.findByUser()','\"admin\"',16,'0:0:0:0:0:0:0:1','admin','2019-01-21 10:15:44',NULL,NULL),(2876,'admin',NULL,'com.hoya.admin.service.impl.SysLoginLogServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"userName\",\"value\":\"\"}]}',13,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:10',NULL,NULL),(2877,'admin',NULL,'com.hoya.admin.service.impl.SysRoleServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"name\",\"value\":\"\"}]}',15,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:13',NULL,NULL),(2878,'admin',NULL,'com.hoya.admin.service.impl.SysMenuServiceImpl.findTree()','null',10,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:13',NULL,NULL),(2879,'admin',NULL,'com.hoya.admin.service.impl.SysDeptServiceImpl.findTree()',NULL,3,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:14',NULL,NULL),(2880,'admin',NULL,'com.hoya.admin.service.impl.SysDeptServiceImpl.findTree()',NULL,2,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:14',NULL,NULL),(2881,'admin',NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"name\",\"value\":\"\"}]}',49,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:14',NULL,NULL),(2882,'admin',NULL,'com.hoya.admin.service.impl.SysRoleServiceImpl.findAll()',NULL,1,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:14',NULL,NULL),(2883,'admin',NULL,'com.hoya.admin.service.impl.SysMenuServiceImpl.findTree()','null',3,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:15',NULL,NULL),(2884,'admin',NULL,'com.hoya.admin.service.impl.SysConfigServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"label\",\"value\":\"\"}]}',8,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:15',NULL,NULL),(2885,'admin',NULL,'com.hoya.admin.service.impl.SysLoginLogServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"userName\",\"value\":\"\"}]}',7,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:16',NULL,NULL),(2886,'admin',NULL,'com.hoya.admin.service.impl.SysDeptServiceImpl.findTree()',NULL,2,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:22',NULL,NULL),(2887,'admin',NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"name\",\"value\":\"\"}]}',61,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:22',NULL,NULL),(2888,'admin',NULL,'com.hoya.admin.service.impl.SysRoleServiceImpl.findAll()',NULL,1,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:22',NULL,NULL),(2889,'admin',NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findPage()','{\"pageNum\":2,\"pageSize\":9,\"params\":[{\"name\":\"name\",\"value\":\"\"}]}',18,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:25',NULL,NULL),(2890,'admin',NULL,'com.hoya.admin.service.impl.SysRoleServiceImpl.findAll()',NULL,2,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:25',NULL,NULL),(2891,'admin',NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"name\",\"value\":\"\"}]}',43,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:27',NULL,NULL),(2892,'admin',NULL,'com.hoya.admin.service.impl.SysRoleServiceImpl.findAll()',NULL,1,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:27',NULL,NULL),(2893,'admin',NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.createUserExcelFile()','{\"pageNum\":1,\"pageSize\":100000,\"params\":[{\"name\":\"name\",\"value\":\"\"}]}',1577,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:48:38',NULL,NULL),(2894,'admin',NULL,'com.hoya.admin.service.impl.SysDeptServiceImpl.findTree()',NULL,1,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:49:24',NULL,NULL),(2895,'admin',NULL,'com.hoya.admin.service.impl.SysUserServiceImpl.findPage()','{\"pageNum\":1,\"pageSize\":9,\"params\":[{\"name\":\"name\",\"value\":\"\"}]}',75,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:49:24',NULL,NULL),(2896,'admin',NULL,'com.hoya.admin.service.impl.SysRoleServiceImpl.findAll()',NULL,2,'0:0:0:0:0:0:0:1','admin','2019-01-22 14:49:24',NULL,NULL);
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
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
