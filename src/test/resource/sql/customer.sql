/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : boge_test

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-03-17 10:20:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID，自增主键',
  `name` varchar(255) NOT NULL COMMENT '客户名称\n',
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人\n',
  `telephone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='客户表';

-- ----------------------------
-- Records of customer
-- ----------------------------
/*INSERT INTO `customer` VALUES ('1', '盘丝洞', '紫霞', '666666', 'zixia@pansidong.com', '我的意中人身披金甲，脚踏五彩祥云来接我');
INSERT INTO `customer` VALUES ('2', '斧头帮', '至尊宝', '888888', 'zhizunbao@futoubang.com', '我怎么爱上一个我讨厌的人呢？');
*/