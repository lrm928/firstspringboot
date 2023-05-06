/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : meeting-manager-hw

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2023-05-02 10:16:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for stat_rule_config
-- ----------------------------
DROP TABLE IF EXISTS `stat_rule_config`;
CREATE TABLE `stat_rule_config` (
  `id` varchar(40) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(40) DEFAULT NULL COMMENT '创建用户',
  `status` varchar(2) DEFAULT '1' COMMENT '状态',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `default_val` int(11) DEFAULT NULL COMMENT '默认值',
  `rule_type` varchar(40) DEFAULT NULL COMMENT '规则类型',
  `sql_rule` varchar(2000) DEFAULT NULL COMMENT 'SQL规则',
  `target_desc` varchar(200) DEFAULT NULL COMMENT '指标描述',
  `target_name` varchar(40) DEFAULT NULL COMMENT '指标名称',
  `target_type` varchar(40) DEFAULT NULL COMMENT '指标分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stat_rule_config
-- ----------------------------
INSERT INTO `stat_rule_config` VALUES ('1', '2023-05-02 09:36:01', null, '1', '2023-05-02 09:36:01', null, 'val', 'select count(1) as val from c_device t', '设备数量', 'totalDevice', 'MainPage');
INSERT INTO `stat_rule_config` VALUES ('2', '2023-05-02 09:39:49', null, '1', '2023-05-02 09:39:49', '12000', 'val', null, '设备目标', 'maxDevice', 'MainPage');
INSERT INTO `stat_rule_config` VALUES ('3', '2023-05-02 09:57:19', null, '1', '2023-05-02 09:57:19', null, 'list', 'SELECT o.`name` AS depatName,count(u.id) AS userNum FROM sys_user u,sys_org o WHERE u.dept_id = o.id AND u.create_time >= \'{startTime}\' AND u.create_time <= \'{endTime}\' AND o.company_id = \'f2a551d0-6d76-49d5-b862-eb11f6f80f72\' GROUP BY o.`name`', '各部门人数', 'departNum', 'MainPage');
INSERT INTO `stat_rule_config` VALUES ('4', '2023-05-02 09:58:50', null, '1', '2023-05-02 09:58:50', null, 'chartByData', 'SELECT o.`name` AS xkey,count(u.id) AS val FROM sys_user u,sys_org o WHERE u.dept_id = o.id AND u.create_time >= \'{startTime}\' AND u.create_time <= \'{endTime}\' AND o.company_id = \'f2a551d0-6d76-49d5-b862-eb11f6f80f72\' GROUP BY o.`name`', '各部门人数分布', 'departNumChar', 'MainPage');
