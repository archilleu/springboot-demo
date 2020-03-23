-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.7.28-log

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
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `spread` tinyint(4) DEFAULT '0' COMMENT '是否展开  0：不展开  1：展开',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='菜单管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,0,'系统管理',NULL,NULL,0,'larry-xitong',1,0),(2,46,'用户管理','/sys/user/index.html',NULL,1,NULL,NULL,1),(3,46,'角色管理','sys/role/index.html',NULL,1,NULL,NULL,2),(4,49,'菜单管理','/sys/menu/index.html',NULL,1,NULL,NULL,1),(5,47,'SQL监控','druid/sql.html',NULL,1,'larry-uicon_sql',0,1),(6,42,'定时任务','modules/job/schedule.html',NULL,1,'larry-renwu2',0,1),(7,6,'查看',NULL,'sys:schedule:list,sys:schedule:info',2,NULL,0,0),(8,6,'新增',NULL,'sys:schedule:save',2,NULL,0,0),(9,6,'修改',NULL,'sys:schedule:update',2,NULL,0,0),(10,6,'删除',NULL,'sys:schedule:delete',2,NULL,0,0),(11,6,'暂停',NULL,'sys:schedule:pause',2,NULL,0,0),(12,6,'恢复',NULL,'sys:schedule:resume',2,NULL,0,0),(13,6,'立即执行',NULL,'sys:schedule:run',2,NULL,0,0),(14,6,'日志列表',NULL,'sys:schedule:log',2,NULL,0,0),(15,2,'查看',NULL,'sys:user:list,sys:user:info',2,NULL,0,0),(16,2,'新增',NULL,'sys:user:save,sys:role:select',2,NULL,0,0),(17,2,'修改',NULL,'sys:user:update,sys:role:select',2,NULL,0,0),(18,2,'删除',NULL,'sys:user:delete',2,NULL,0,0),(19,3,'查看',NULL,'sys:role:list,sys:role:info',2,NULL,0,0),(20,3,'新增',NULL,'sys:role:save,sys:menu:perms',2,NULL,0,0),(21,3,'修改',NULL,'sys:role:update,sys:menu:perms',2,NULL,0,0),(22,3,'删除',NULL,'sys:role:delete',2,NULL,0,0),(23,4,'查看',NULL,'sys:menu:list,sys:menu:info',2,NULL,0,0),(24,4,'新增',NULL,'sys:menu:save,sys:menu:select',2,NULL,0,0),(25,4,'修改',NULL,'sys:menu:update,sys:menu:select',2,NULL,0,0),(26,4,'删除',NULL,'sys:menu:delete',2,NULL,0,0),(27,42,'参数管理','modules/sys/config.html','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-xitong-pressed',0,2),(29,47,'系统日志','modules/sys/log.html','sys:log:list',1,'larry-10109',0,2),(30,42,'文件上传','modules/oss/oss.html','sys:oss:all',1,'larry-friendLink',0,4),(31,46,'部门管理','modules/sys/dept.html',NULL,1,'larry-Shape',0,3),(32,31,'查看',NULL,'sys:dept:list,sys:dept:info',2,NULL,0,0),(33,31,'新增',NULL,'sys:dept:save,sys:dept:select',2,NULL,0,0),(34,31,'修改',NULL,'sys:dept:update,sys:dept:select',2,NULL,0,0),(35,31,'删除',NULL,'sys:dept:delete',2,NULL,0,0),(36,42,'字典管理','modules/sys/dict.html',NULL,1,'larry-sensitive',0,3),(37,36,'查看',NULL,'sys:dict:list,sys:dict:info',2,NULL,0,6),(38,36,'新增',NULL,'sys:dict:save',2,NULL,0,6),(39,36,'修改',NULL,'sys:dict:update',2,NULL,0,6),(40,36,'删除',NULL,'sys:dict:delete',2,NULL,0,6),(41,48,'文章管理','modules/demo/news.html','demo:news:list,demo:news:info,demo:news:save,demo:news:update,demo:news:delete',1,'larry-neirongfabu',0,1),(42,1,'系统设置',NULL,NULL,0,'larry-wsmp-setting',0,2),(46,1,'系统用户管理',NULL,NULL,0,'larry-paikexitong_yonghuguanli',0,0),(47,1,'系统监控',NULL,NULL,0,'larry-shouye-anquanguanli',0,3),(48,0,'功能示例',NULL,NULL,0,'larry-diannao3',0,1),(49,1,'系统菜单',NULL,NULL,0,'larry-caidanguanli3',0,1),(50,49,'图标管理','modules/demo/font.html',NULL,1,'larry-qizhi',0,2),(51,2,'导出',NULL,'sys:user:export',2,NULL,0,0),(61,6,'test','12',NULL,0,NULL,NULL,0);
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

-- Dump completed on 2020-03-23 15:51:27
