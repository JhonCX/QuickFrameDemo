/*
MySQL Data Transfer
Source Host: localhost
Source Database: quick_frame_two
Target Host: localhost
Target Database: quick_frame_two
Date: 2019/11/11 19:55:11
*/
CREATE DATABASE quick_frame_two;
USER quick_frame_two;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for server_set
-- ----------------------------
CREATE TABLE `server_set` (
  `id` int(11) DEFAULT NULL,
  `login_flag` tinyint(4) NOT NULL DEFAULT '1',
  `register_flag` tinyint(4) NOT NULL DEFAULT '1',
  `message` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `server_set` VALUES ('1', '1', '1', null);
