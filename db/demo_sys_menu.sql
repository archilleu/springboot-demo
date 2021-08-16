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
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1426078740282204162 DEFAULT CHARSET=utf8 COMMENT='菜单管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,NULL,NULL,0,'el-icon-setting',0,NULL,NULL,NULL,NULL,0),(2,'用户管理',1,'/sys/user',NULL,1,'el-icon-service',1,NULL,NULL,NULL,NULL,0),(3,'查看',2,NULL,'sys:user:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(4,'新增',2,NULL,'sys:user:add',2,NULL,0,NULL,NULL,NULL,NULL,0),(5,'修改',2,NULL,'sys:user:edit',2,NULL,0,NULL,NULL,NULL,NULL,0),(6,'删除',2,NULL,'sys:user:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(7,'机构管理',1,'/sys/dept',NULL,1,'el-icon-news',2,NULL,NULL,NULL,NULL,0),(8,'查看',7,NULL,'sys:dept:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(9,'新增',7,NULL,'sys:dept:add',2,NULL,0,NULL,NULL,NULL,NULL,0),(10,'修改',7,NULL,'sys:dept:edit',2,NULL,0,NULL,NULL,NULL,NULL,0),(11,'删除',7,NULL,'sys:dept:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(12,'角色管理',1,'/sys/role',NULL,1,'el-icon-view',4,NULL,NULL,NULL,NULL,0),(13,'查看',12,NULL,'sys:role:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(14,'新增',12,NULL,'sys:role:add',2,NULL,0,NULL,NULL,NULL,NULL,0),(15,'修改',12,NULL,'sys:role:edit',2,NULL,0,NULL,NULL,NULL,NULL,0),(16,'删除',12,NULL,'sys:role:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(17,'菜单管理',1,'/sys/menu',NULL,1,'el-icon-menu',5,NULL,NULL,NULL,NULL,0),(18,'查看',17,NULL,'sys:menu:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(19,'新增',17,NULL,'sys:menu:add',2,NULL,0,NULL,NULL,NULL,NULL,0),(20,'修改',17,NULL,'sys:menu:edit',2,NULL,0,NULL,NULL,NULL,NULL,0),(21,'删除',17,NULL,'sys:menu:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(22,'字典管理',1,'/sys/dict',NULL,1,'el-icon-edit-outline',7,NULL,NULL,NULL,NULL,0),(23,'查看',22,NULL,'sys:dict:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(24,'新增',22,NULL,'sys:dict:add',2,NULL,0,NULL,NULL,NULL,NULL,0),(25,'修改',22,NULL,'sys:dict:edit',2,NULL,0,NULL,NULL,NULL,NULL,0),(26,'删除',22,NULL,'sys:dict:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(27,'系统配置',1,'/sys/config',NULL,1,'el-icon-edit-outline',7,NULL,NULL,NULL,NULL,0),(28,'查看',27,NULL,'sys:config:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(29,'新增',27,NULL,'sys:config:add',2,NULL,0,NULL,NULL,NULL,NULL,0),(30,'修改',27,NULL,'sys:config:edit',2,NULL,0,NULL,NULL,NULL,NULL,0),(31,'删除',27,NULL,'sys:config:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(32,'登录日志',1,'/sys/loginlog',NULL,1,'el-icon-info',8,NULL,NULL,'admin','2018-09-23 19:32:28',0),(33,'查看',32,NULL,'sys:loginlog:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(34,'删除',32,NULL,'sys:loginlog:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(35,'操作日志',1,'/sys/log',NULL,1,'el-icon-info',8,NULL,NULL,'admin','2018-09-23 19:32:28',0),(36,'查看',35,NULL,'sys:log:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(37,'删除',35,NULL,'sys:log:delete',2,NULL,0,NULL,NULL,NULL,NULL,0),(50,'在线用户',0,'/sys/online','',1,'el-icon-view',5,'admin','2018-11-15 14:39:30','admin','2018-11-15 14:56:18',0),(51,'查看',50,NULL,'sys:online:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(52,'使用案例',0,NULL,NULL,0,'el-icon-picture-outline',6,NULL,NULL,'admin','2018-11-15 14:39:43',0),(53,'国际化',52,'/demo/i18n',NULL,1,'el-icon-edit',1,NULL,NULL,NULL,NULL,0),(54,'查看',53,NULL,'sys:dict:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(55,'换皮肤',52,'/demo/theme',NULL,1,'el-icon-picture',2,NULL,NULL,NULL,NULL,0),(56,'查看',55,NULL,'sys:dict:view',2,NULL,0,NULL,NULL,NULL,NULL,0),(57,'第三方网站',52,'/http://www.baidu.com',NULL,1,'el-icon-edit',1,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
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
