/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.1.73-community : Database - book_store
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`book_store` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `book_store`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category_id` int(11) NOT NULL COMMENT '书籍分类ID',
  `book_name` varchar(200) NOT NULL COMMENT '书名',
  `author` varchar(100) NOT NULL COMMENT '作者',
  `publisher_name` varchar(100) NOT NULL COMMENT '出版社名称',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `publish_date` date NOT NULL COMMENT '出版日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`id`,`category_id`,`book_name`,`author`,`publisher_name`,`price`,`publish_date`,`create_time`,`update_time`) values (1,1,'HTTP权威指南','David Gourley , Brian Totty , Marjorie Sayer , Sailu Reddy , Anshu Aggarwal (作者) 陈涓 , 赵振平 (译者)','人民邮电出版社','92.65','2018-08-01','2018-08-07 15:34:56','2018-08-07 15:35:00'),(2,1,'JavaScript设计模式与开发实践','曾探','人民邮电出版社','50.15','2018-08-01','2018-08-07 15:37:15','2018-08-07 15:37:17'),(3,1,'JavaScript高级程序设计','Nicholas C.Zakas (作者) 李松峰 , 曹力 (译者)','人民邮电出版社','84.15','2018-08-01','2018-08-07 15:38:29','2018-08-07 15:38:31');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父类ID',
  `category_name` varchar(100) NOT NULL COMMENT '分类名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` date NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`parent_id`,`category_name`,`create_time`,`update_time`) values (1,0,'计算机技术','2018-08-07 15:36:05','2018-08-07');

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mobile_no` varchar(11) NOT NULL COMMENT '手机号码',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件',
  `sex` enum('MALE','FEMALE') DEFAULT NULL COMMENT '性别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_MOBILE_NO` (`mobile_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `book_id` int(11) NOT NULL COMMENT '书籍ID',
  `book_amt` decimal(13,2) NOT NULL COMMENT '书籍金额',
  `book_count` smallint(6) NOT NULL COMMENT '书籍数量',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `order_info` */

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `customer_id` int(11) NOT NULL COMMENT '顾客ID',
  `order_code` varchar(40) NOT NULL COMMENT '订单编号',
  `order_amt` decimal(13,2) NOT NULL COMMENT '订单金额',
  `order_state` enum('NONPAID','PAID','CANCELLED') NOT NULL COMMENT '订单状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
