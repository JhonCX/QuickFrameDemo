/*
MySQL Data Transfer
Source Host: localhost
Source Database: quick_frame
Target Host: localhost
Target Database: quick_frame
Date: 2019/11/11 19:55:02
*/
CREATE DATABASE quick_frame;
USER quick_frame;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for q_user
-- ----------------------------
CREATE TABLE `q_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(32) DEFAULT NULL,
  `pwd` varchar(32) NOT NULL,
  `nick_name` varchar(32) DEFAULT NULL,
  `type` varchar(32) NOT NULL,
  `flag` tinyint(4) NOT NULL,
  `login_time` varchar(32) DEFAULT NULL,
  `register_time` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `q_user` VALUES ('1', 'admin@admin.com', 'admin', 'admin', 'ROLE_ADMIN', '1', '2019-11-11 19:47:50', '2019-11-10 23:36:29');
