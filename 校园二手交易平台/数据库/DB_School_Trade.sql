/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : DB_School_trade

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 06/01/2024 08:39:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sh_address
-- ----------------------------
DROP TABLE IF EXISTS `sh_address`;
CREATE TABLE `sh_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `consignee_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '收货人姓名',
  `consignee_phone` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '收货人手机号',
  `province_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '省',
  `city_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '市',
  `region_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '区',
  `detail_address` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `default_flag` tinyint NOT NULL COMMENT '是否默认地址',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_address
-- ----------------------------
BEGIN;
INSERT INTO `sh_address` VALUES (30, '陈xx', '17777777777', '天津市', '市辖区', '和平区', '浙江省杭州市余杭区xx街道xx小区', 1, 11);
INSERT INTO `sh_address` VALUES (31, '陈xx', '18888888888', '北京市', '市辖区', '西城区', '北京xx路xx小区', 1, 12);
COMMIT;

-- ----------------------------
-- Table structure for sh_admin
-- ----------------------------
DROP TABLE IF EXISTS `sh_admin`;
CREATE TABLE `sh_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `account_number` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '管理员账号',
  `admin_password` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `admin_name` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '管理员名字',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account_number` (`account_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_admin
-- ----------------------------
BEGIN;
INSERT INTO `sh_admin` VALUES (1, '11', '123123', '超级管理员');
INSERT INTO `sh_admin` VALUES (13, '1866666666', '123456', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for sh_favorite
-- ----------------------------
DROP TABLE IF EXISTS `sh_favorite`;
CREATE TABLE `sh_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `create_time` datetime NOT NULL COMMENT '加入收藏的时间',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `idle_id` bigint NOT NULL COMMENT '闲置物主键id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id` (`user_id`,`idle_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_favorite
-- ----------------------------
BEGIN;
INSERT INTO `sh_favorite` VALUES (48, '2024-01-05 14:29:40', 12, 112);
COMMIT;

-- ----------------------------
-- Table structure for sh_idle_item
-- ----------------------------
DROP TABLE IF EXISTS `sh_idle_item`;
CREATE TABLE `sh_idle_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `idle_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '闲置物名称',
  `idle_details` varchar(2048) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '详情',
  `picture_list` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '图集',
  `idle_price` decimal(10,2) NOT NULL COMMENT '价格',
  `idle_place` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '发货地区',
  `idle_label` int NOT NULL COMMENT '分类标签',
  `release_time` datetime NOT NULL COMMENT '发布时间',
  `idle_status` tinyint NOT NULL COMMENT '状态（发布1、下架2、删除0）',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_idle_item
-- ----------------------------
BEGIN;
INSERT INTO `sh_idle_item` VALUES (105, '手机', '书籍', '[\"http://localhost:8080/image?imageName=file170446441929610041.jpg\",\"http://localhost:8080/image?imageName=file170446442439410052.jpg\"]', 1900.00, '市辖区', 1, '2024-01-05 14:20:26', 1, 11);
INSERT INTO `sh_idle_item` VALUES (106, '电脑', '电脑', '[\"http://localhost:8080/image?imageName=file17044644744561006个人博客.jpg\"]', 3000.00, '阳泉市', 1, '2024-01-05 14:21:16', 1, 11);
INSERT INTO `sh_idle_item` VALUES (107, '电视机', '电视机', '[\"http://localhost:8080/image?imageName=file17044645972761007电影.jpg\"]', 5999.00, '长治市', 2, '2024-01-05 14:23:19', 1, 11);
INSERT INTO `sh_idle_item` VALUES (108, '空调', '空调', '[\"http://localhost:8080/image?imageName=file17044646299941008停车场.jpg\"]', 1000.00, '景德镇市', 2, '2024-01-05 14:23:51', 1, 11);
INSERT INTO `sh_idle_item` VALUES (109, '历史书', '历史书', '[\"http://localhost:8080/image?imageName=file17044646674121009音乐.jpg\"]', 20.00, '乌海市', 4, '2024-01-05 14:24:33', 1, 11);
INSERT INTO `sh_idle_item` VALUES (110, '帐篷', '帐篷', '[\"http://localhost:8080/image?imageName=file17044647092251010社团.jpg\"]', 100.00, '秦皇岛市', 3, '2024-01-05 14:25:22', 1, 11);
INSERT INTO `sh_idle_item` VALUES (111, '自行车', '自行车', '[\"http://localhost:8080/image?imageName=file17044647567061011考试.jpg\"]', 599.00, '市辖区', 5, '2024-01-05 14:26:00', 2, 11);
INSERT INTO `sh_idle_item` VALUES (112, '衣服', '衣服', '[\"http://localhost:8080/image?imageName=file17044647909181012图书馆.png\"]', 199.00, '长治市', 5, '2024-01-05 14:26:38', 1, 11);
COMMIT;

-- ----------------------------
-- Table structure for sh_message
-- ----------------------------
DROP TABLE IF EXISTS `sh_message`;
CREATE TABLE `sh_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `idle_id` bigint NOT NULL COMMENT '闲置主键id',
  `content` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  `to_user` bigint NOT NULL COMMENT '所回复的用户',
  `to_message` bigint DEFAULT NULL COMMENT '所回复的留言',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id_index` (`user_id`) USING BTREE,
  KEY `idle_id_index` (`idle_id`) USING BTREE,
  KEY `to_user_index` (`to_user`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_message
-- ----------------------------
BEGIN;
INSERT INTO `sh_message` VALUES (48, 11, 112, '多少钱', '2024-01-05 14:26:48', 11, NULL);
INSERT INTO `sh_message` VALUES (49, 11, 111, '这个可以便宜点么？', '2024-01-05 14:27:03', 11, NULL);
INSERT INTO `sh_message` VALUES (50, 11, 111, '可以', '2024-01-05 14:27:19', 11, 49);
INSERT INTO `sh_message` VALUES (51, 11, 107, '东西怎么买啊？人在哪？', '2024-01-06 00:32:51', 11, NULL);
INSERT INTO `sh_message` VALUES (52, 11, 105, '东西怎么买？', '2024-01-06 00:33:54', 11, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sh_order
-- ----------------------------
DROP TABLE IF EXISTS `sh_order`;
CREATE TABLE `sh_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `order_number` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `idle_id` bigint NOT NULL COMMENT '闲置物品主键id',
  `order_price` decimal(10,2) NOT NULL COMMENT '订单总价',
  `payment_status` tinyint NOT NULL COMMENT '支付状态',
  `payment_way` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付方式',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `order_status` tinyint NOT NULL COMMENT '订单状态',
  `is_deleted` tinyint DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_order
-- ----------------------------
BEGIN;
INSERT INTO `sh_order` VALUES (85, '170446501212310003', 12, 106, 3000.00, 0, NULL, '2024-01-05 14:30:12', NULL, 4, NULL);
INSERT INTO `sh_order` VALUES (86, '170446506640210004', 12, 111, 599.00, 1, '支付宝', '2024-01-05 14:31:06', '2024-01-05 14:31:11', 3, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sh_order_address
-- ----------------------------
DROP TABLE IF EXISTS `sh_order_address`;
CREATE TABLE `sh_order_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `consignee_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '收货人',
  `consignee_phone` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `detail_address` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `orderId` (`order_id`) USING BTREE,
  KEY `order_id_index` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_order_address
-- ----------------------------
BEGIN;
INSERT INTO `sh_order_address` VALUES (21, 86, '陈xx', '18888888888', '北京市市辖区西城区北京xx路xx小区');
INSERT INTO `sh_order_address` VALUES (22, 85, '陈xx', '18888888888', '北京市市辖区西城区北京xx路xx小区');
COMMIT;

-- ----------------------------
-- Table structure for sh_user
-- ----------------------------
DROP TABLE IF EXISTS `sh_user`;
CREATE TABLE `sh_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `account_number` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '账号（手机号）',
  `user_password` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `nickname` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `sign_in_time` datetime NOT NULL COMMENT '注册时间',
  `user_status` tinyint DEFAULT NULL COMMENT '状态（1代表封禁）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account_number` (`account_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sh_user
-- ----------------------------
BEGIN;
INSERT INTO `sh_user` VALUES (11, '18777777777', '123456', 'user', 'http://localhost:8080/image?imageName=file170446387201110031.jpg', '2024-01-05 13:20:06', NULL);
INSERT INTO `sh_user` VALUES (12, '18888888888', '123456', 'user2', 'http://localhost:8080/image?imageName=file170446508583010132.jpg', '2024-01-05 14:29:24', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
