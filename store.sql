/*
SQLyog Professional v12.14 (64 bit)
MySQL - 8.0.25 : Database - store
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`store` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `store`;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `uid` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `salt` char(36) DEFAULT NULL COMMENT '盐值',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `gender` int DEFAULT NULL COMMENT '性别:0-女，1-男',
  `avatar` varchar(50) DEFAULT NULL COMMENT '头像',
  `is_delete` int DEFAULT NULL COMMENT '是否删除：0-未删除，1-已删除',
  `created_user` varchar(20) DEFAULT NULL COMMENT '日志-创建人',
  `created_time` datetime DEFAULT NULL COMMENT '日志-创建时间',
  `modified_user` varchar(20) DEFAULT NULL COMMENT '日志-最后修改执行人',
  `modified_time` datetime DEFAULT NULL COMMENT '日志-最后修改时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_user` */

insert  into `t_user`(`uid`,`username`,`password`,`salt`,`phone`,`email`,`gender`,`avatar`,`is_delete`,`created_user`,`created_time`,`modified_user`,`modified_time`) values 
(1,'tim','123',NULL,'12764322','tim@qq.com',1,NULL,NULL,NULL,NULL,NULL,NULL),
(2,'abc','123',NULL,'e21412eqw21','tim@qq.com',1,NULL,0,'abc','2021-10-07 14:32:42',NULL,NULL),
(3,'abc1','53FEE56A4562E3B664ED6C1E371850B1',NULL,'','',1,NULL,0,'abc1','2021-10-07 14:49:00',NULL,NULL),
(4,'Tom001','1AEB89FEE8799CD321B93301DC6CF4D1','F4876769-A368-444C-A4AD-4A416DC854B8',NULL,NULL,NULL,NULL,0,'Tom001','2021-10-07 15:17:59','Tom001','2021-10-07 15:17:59'),
(5,'Tom002','AF6157D2883B8F485968753C211F8B0A','A9D1CB79-055F-4233-A6FF-27D603091018',NULL,NULL,NULL,NULL,0,'Tom002','2021-10-07 15:29:19','Tom002','2021-10-07 15:29:19'),
(6,'test01','321','F9882B7A-C6DF-4456-9699-C05248F5FB76','3123231221321','test01@qq.com',1,NULL,0,'test01','2021-10-07 16:54:10',NULL,NULL),
(7,'test02','4FD928C2F2D1AC5392AD324D9835E53B','DB705D8D-D626-4D52-86C9-BD25D5AA1FE7','135872322','asd@qq.com',0,NULL,0,'test02','2021-10-09 20:40:50','管理员','2021-10-10 10:36:58'),
(8,'test03','744FC7B04592541ED306CFF8B527C7B1','121224B2-EC9A-47F1-9857-62B748197C02','1234213213','test@qq.com',0,NULL,0,'test03','2021-10-10 10:47:11','test03','2021-10-10 10:54:53'),
(9,'test001','0068E98C20CDE3CD8AAFCC70AEFDD0DE','684DDA54-3B1F-4EC3-ACBB-35DFE3B080BD','1232412312','test001@qq.com',0,NULL,0,'test001','2021-10-10 12:55:28','test001','2021-10-10 13:01:32');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
