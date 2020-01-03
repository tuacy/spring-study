/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50513
Source Host           : 127.0.0.1:3306
Source Database       : tuacy_system

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2020-01-03 20:58:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_info
-- ----------------------------
DROP TABLE IF EXISTS `system_info`;
CREATE TABLE `system_info` (
  `pkid` bigint(20) NOT NULL,
  `value` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_info
-- ----------------------------
INSERT INTO `system_info` VALUES ('1', '10');
