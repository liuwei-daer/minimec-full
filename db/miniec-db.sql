/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : miniec-dev

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 16/03/2018 16:56:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(15) DEFAULT NULL COMMENT '用户手机号码',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `salt` varchar(100) NOT NULL COMMENT '密码盐值',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：启用，0：禁用',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `avatar_url` varchar(50) DEFAULT NULL COMMENT '头像地址',
  `is_delete` int(11) DEFAULT '0' COMMENT '是否删除，0：未删除，1：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES (1, '15618956058', '刘为', '15618956058', '$2a$10$cKRbR9IJktfmKmf/wShyo.5.J8IxO/7YVn8twuWFtvPgruAF8gtKq', '$2a$10$z6doWLeoNkzu9qcmyKZeIu', 1, 1, '2018-01-02 09:59:06', '2018-03-16 08:55:30', NULL, 0);
INSERT INTO `admin_user` VALUES (4, '13800000000', '测试112', '13800000000', '$2a$10$cKRbR9IJktfmKmf/wShyo.5.J8IxO/7YVn8twuWFtvPgruAF8gtKq', '$2a$10$QRZsWdLmYlSTm7GCfLJxV.', 0, NULL, '2018-03-12 11:41:39', '2018-03-16 08:55:33', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for dict_item
-- ----------------------------
DROP TABLE IF EXISTS `dict_item`;
CREATE TABLE `dict_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_id` int(11) DEFAULT NULL,
  `dict_name` varchar(100) DEFAULT NULL,
  `item_code` varchar(50) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `order_no` int(11) DEFAULT '1' COMMENT '排序号',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '启用状态，1：启用，2：未启用',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` int(11) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `memo` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `DICT_PK1` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict_item
-- ----------------------------
BEGIN;
INSERT INTO `dict_item` VALUES (1, 0, NULL, 'SEX', '性别', 1, 1, 1, '2018-03-14 06:15:04', NULL, '2018-03-15 07:29:32', NULL);
INSERT INTO `dict_item` VALUES (3, 1, '性别', '1', '男', 1, 1, 1, '2017-03-27 14:05:21', NULL, '2017-04-10 10:57:06', '');
INSERT INTO `dict_item` VALUES (4, 1, '性别', '2', '女', 2, 1, 1, '2017-03-27 14:05:34', NULL, '2017-04-10 10:57:06', '');
COMMIT;

-- ----------------------------
-- Table structure for operat_log
-- ----------------------------
DROP TABLE IF EXISTS `operat_log`;
CREATE TABLE `operat_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `user_mobile` varchar(15) DEFAULT NULL,
  `ip_addr` varchar(20) DEFAULT NULL,
  `action_url` varchar(100) DEFAULT NULL,
  `sub_module` varchar(100) DEFAULT NULL,
  `module` varchar(100) DEFAULT NULL,
  `mean` varchar(100) DEFAULT NULL,
  `function` varchar(100) DEFAULT NULL,
  `sub_function` varchar(100) DEFAULT NULL,
  `param_data` varchar(4000) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `memo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for param_info
-- ----------------------------
DROP TABLE IF EXISTS `param_info`;
CREATE TABLE `param_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(64) NOT NULL,
  `param_value` varchar(255) DEFAULT NULL,
  `param_name` varchar(64) NOT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` int(11) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `memo` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_info
-- ----------------------------
BEGIN;
INSERT INTO `param_info` VALUES (1, 'test_key', '2', '测试参数', 1, '2018-03-15 10:55:51', 1, '2018-03-15 10:57:37', '测试参数');
COMMIT;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父节点编号',
  `parent_ids` varchar(45) DEFAULT NULL COMMENT '父节点链编号',
  `name` varchar(200) DEFAULT NULL COMMENT '节点名称',
  `type` int(11) DEFAULT NULL COMMENT '资源类型:对应数据字典表中数据',
  `url` varchar(200) DEFAULT NULL,
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `sort` varchar(50) DEFAULT NULL COMMENT '排序',
  `icon` varchar(50) DEFAULT NULL COMMENT '资源图标',
  `create_by` int(11) NOT NULL COMMENT '创建者ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1：删除，0：未删除。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统资源表';

-- ----------------------------
-- Records of resource
-- ----------------------------
BEGIN;
INSERT INTO `resource` VALUES (1, 0, '0', '系统根节点', 0, '', '', '1', '', 1, '2017-03-30 15:22:32', 1, '2017-04-15 00:28:59', 0);
INSERT INTO `resource` VALUES (2, 1, '1', '系统管理', 1, '', '', '1', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:10', 0);
INSERT INTO `resource` VALUES (3, 2, '1,2', '用户管理', 1, '/sys/user/', 'sys:user:manage', '1', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (4, 2, '1,2', '角色管理', 1, '/sys/role/', 'sys:role:manage', '2', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (5, 2, '1,2', '资源管理', 1, '/sys/resource/', 'sys:resource:manage', '3', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (6, 3, '1,2,3', '用户列表', 4, '/sys/user/list', 'sys:user:list', '1', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (7, 3, '1,2,3', '新增用户', 2, '/sys/user/add', 'sys:user:add', '2', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (8, 3, '1,2,3', '修改用户', 2, '/sys/user/edit', 'sys:user:update', '3', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (9, 3, '1,2,3', '删除用户', 2, '/sys/user/del', 'sys:user:delete', '4', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (10, 4, '1,2,4', '角色列表', 4, '/sys/role/list', 'sys:role:list', '1', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (11, 4, '1,2,4', '新增角色', 2, '/sys/role/add', 'sys:role:add', '2', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (12, 4, '1,2,4', '修改角色', 2, '/sys/role/edit', 'sys:role:update', '3', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (13, 4, '1,2,4', '删除角色', 2, '/sys/role/del', 'sys:role:delete', '4', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (14, 5, '1,2,5', '资源列表', 4, '/sys/resource/list', 'sys:resource:list', '1', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (15, 5, '1,2,5', '新增资源', 2, '/sys/resource/add', 'sys:resource:add', '2', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (16, 5, '1,2,5', '修改资源', 2, '/sys/resource/edit', 'sys:resource:update', '3', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (17, 5, '1,2,5', '删除资源', 2, '/sys/resource/del', 'sys:role:delete', '4', '', 1, '2017-03-30 15:22:32', 1, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (18, 4, '1,2,4', '角色赋权', 2, '/sys/role/grantFun', 'sys:role:grantFun', '5', '', 1, '2017-04-07 20:37:29', NULL, '2018-03-12 01:37:50', 0);
INSERT INTO `resource` VALUES (19, 2, '1,2', '测试管理', 1, '/sys/test/', 'sys:test:manage', '4', NULL, 1, '2018-03-12 07:25:54', NULL, '2018-03-12 07:26:36', 0);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_key` varchar(45) DEFAULT NULL,
  `role_name` varchar(45) DEFAULT NULL,
  `status` int(2) DEFAULT '1' COMMENT '启用状态，1：启用，0：未启用',
  `create_by` int(11) NOT NULL COMMENT '创建者ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1：删除，0：未删除。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'admin', '超级管理员', 1, 1, '2018-01-03 11:31:38', 1, '2018-03-12 04:40:23', 0);
INSERT INTO `role` VALUES (2, 'test', '测试', 0, 1, '2018-01-03 11:31:38', 1, '2018-03-15 07:25:35', 0);
COMMIT;

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色资源表';

-- ----------------------------
-- Records of role_resource
-- ----------------------------
BEGIN;
INSERT INTO `role_resource` VALUES (1, 1, 1);
INSERT INTO `role_resource` VALUES (2, 1, 2);
INSERT INTO `role_resource` VALUES (3, 1, 3);
INSERT INTO `role_resource` VALUES (4, 1, 4);
INSERT INTO `role_resource` VALUES (5, 1, 5);
INSERT INTO `role_resource` VALUES (6, 1, 6);
INSERT INTO `role_resource` VALUES (7, 1, 7);
INSERT INTO `role_resource` VALUES (8, 1, 8);
INSERT INTO `role_resource` VALUES (9, 1, 9);
INSERT INTO `role_resource` VALUES (10, 1, 10);
INSERT INTO `role_resource` VALUES (11, 1, 11);
INSERT INTO `role_resource` VALUES (12, 1, 12);
INSERT INTO `role_resource` VALUES (13, 1, 13);
INSERT INTO `role_resource` VALUES (14, 1, 14);
INSERT INTO `role_resource` VALUES (15, 1, 15);
INSERT INTO `role_resource` VALUES (16, 1, 16);
INSERT INTO `role_resource` VALUES (17, 1, 17);
INSERT INTO `role_resource` VALUES (18, 1, 18);
COMMIT;

-- ----------------------------
-- Table structure for tip_info
-- ----------------------------
DROP TABLE IF EXISTS `tip_info`;
CREATE TABLE `tip_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `tip_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '编码',
  `tip_msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `create_by` int(11) DEFAULT NULL COMMENT '创建人或最后更改人ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` int(11) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `memo` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TIP_PK1` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tip_info
-- ----------------------------
BEGIN;
INSERT INTO `tip_info` VALUES (1, '-99', '出错。', NULL, NULL, 1, '2018-03-13 20:49:33', NULL);
INSERT INTO `tip_info` VALUES (2, '88', 'tst', 1, '2018-03-13 20:49:38', NULL, NULL, NULL);
INSERT INTO `tip_info` VALUES (3, '80', '手机添加', 1, '2018-03-14 16:21:29', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (8, 4, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
