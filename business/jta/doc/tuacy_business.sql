/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50513
Source Host           : 127.0.0.1:3306
Source Database       : tuacy_business

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2020-01-03 20:58:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business_info
-- ----------------------------
DROP TABLE IF EXISTS `business_info`;
CREATE TABLE `business_info` (
  `pkid` bigint(20) NOT NULL,
  `value` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of business_info
-- ----------------------------
INSERT INTO `business_info` VALUES ('1', '10');
