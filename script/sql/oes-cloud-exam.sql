/*
 Navicat Premium Data Transfer

 Source Server         : 云平台-172.18.28.82
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 172.18.28.82:3306
 Source Schema         : oes-cloud-exam

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 28/11/2020 16:03:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `course_id` bigint NOT NULL AUTO_INCREMENT COMMENT '课程主键（id）',
  `course_name` varchar(150) NOT NULL COMMENT '课程名称',
  `dept_id` bigint NOT NULL COMMENT '开课学院（部门）',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_course_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_course_teacher`;
CREATE TABLE `t_course_teacher` (
  `course_id` bigint NOT NULL COMMENT '课程编号（id）',
  `teacher_id` bigint NOT NULL COMMENT '教师编号（id）',
  PRIMARY KEY (`course_id`,`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_exam_review
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_review`;
CREATE TABLE `t_exam_review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT COMMENT '复成绩复查主键',
  `score_id` int DEFAULT NULL COMMENT '分数编号',
  `reason` varchar(200) DEFAULT NULL COMMENT '复查原因',
  `read` tinyint DEFAULT NULL COMMENT '1：已读，0：未读',
  `status` tinyint DEFAULT NULL COMMENT '1：处理通过，0：处理未通过',
  `fallback` varchar(200) DEFAULT NULL COMMENT '处理反馈',
  PRIMARY KEY (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='成绩复查表';

-- ----------------------------
-- Table structure for t_exam_violate_log
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_violate_log`;
CREATE TABLE `t_exam_violate_log` (
  `violate_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `paper_id` bigint DEFAULT NULL COMMENT '试卷编号',
  `behaviour` varchar(50) DEFAULT NULL COMMENT '违规行为',
  `violate_time` datetime DEFAULT NULL COMMENT '违规时间',
  `stay_time` varchar(30) DEFAULT NULL COMMENT '违规停留时间',
  `system` varchar(50) DEFAULT NULL COMMENT '系统信息',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器信息',
  `capture` blob COMMENT '抓拍',
  `description` varchar(80) DEFAULT NULL COMMENT '违规行为描述信息',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名（学号）',
  `full_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  PRIMARY KEY (`violate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_paper
-- ----------------------------
DROP TABLE IF EXISTS `t_paper`;
CREATE TABLE `t_paper` (
  `paper_id` bigint NOT NULL COMMENT '试卷主键（id）',
  `paper_name` varchar(50) DEFAULT NULL COMMENT '试卷名称',
  `paper_score` int DEFAULT NULL COMMENT '试卷分数',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `minute` varchar(10) DEFAULT NULL COMMENT '考试时长',
  `creator_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT NULL COMMENT '试卷状态（1：启用，0：禁用）',
  `course_id` bigint DEFAULT NULL COMMENT '课程编号（id）',
  `type` tinyint DEFAULT NULL COMMENT '试卷类型（1：正式考试，0：模拟考试）',
  `is_random` tinyint DEFAULT NULL COMMENT '是否为随机生成的试卷（1：是，0：否）',
  `term_id` bigint DEFAULT NULL COMMENT '学期',
  `config_random_question_order` int DEFAULT NULL COMMENT '试题乱序配置',
  `config_remote` int DEFAULT NULL COMMENT '远程监控配置',
  `config_label_switch` int DEFAULT NULL COMMENT '标签切换检测配置',
  PRIMARY KEY (`paper_id`),
  UNIQUE KEY `t_paper_id` (`paper_id`) USING BTREE,
  KEY `fk_course_id` (`course_id`),
  KEY `fk_term_id` (`term_id`),
  CONSTRAINT `fk_course_id` FOREIGN KEY (`course_id`) REFERENCES `t_course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_term_id` FOREIGN KEY (`term_id`) REFERENCES `t_term` (`term_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_paper_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_paper_answer`;
CREATE TABLE `t_paper_answer` (
  `paper_id` bigint NOT NULL COMMENT '试卷编号（id）',
  `question_id` bigint NOT NULL COMMENT '题目编号（id）',
  `user_id` bigint NOT NULL COMMENT '用户主键（id）',
  `answer_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学生答题JSON数据',
  `score` int DEFAULT NULL COMMENT '题目的分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT NULL COMMENT '答案状态（1：批改，0：未批改）',
  `warn` tinyint DEFAULT NULL COMMENT '1：回答正确，0：回答错误',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名（学号）',
  PRIMARY KEY (`paper_id`,`question_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_paper_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_paper_dept`;
CREATE TABLE `t_paper_dept` (
  `paper_id` bigint NOT NULL COMMENT '试卷编号（id）',
  `dept_id` bigint NOT NULL COMMENT '部门编号（id）',
  PRIMARY KEY (`paper_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_paper_question
-- ----------------------------
DROP TABLE IF EXISTS `t_paper_question`;
CREATE TABLE `t_paper_question` (
  `paper_id` bigint NOT NULL COMMENT '试卷编号（id）',
  `question_id` bigint NOT NULL COMMENT '试题编号（id)',
  PRIMARY KEY (`paper_id`,`question_id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `fk_question_id` FOREIGN KEY (`question_id`) REFERENCES `t_question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_paper_type
-- ----------------------------
DROP TABLE IF EXISTS `t_paper_type`;
CREATE TABLE `t_paper_type` (
  `paper_id` bigint NOT NULL COMMENT '模板主键',
  `type_id` bigint NOT NULL COMMENT '试题类型编号（id）',
  `score` tinyint NOT NULL COMMENT '题目分值',
  `num` int DEFAULT NULL COMMENT '题目数量',
  PRIMARY KEY (`paper_id`,`type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `question_id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目主键（id）',
  `question_name` varchar(500) NOT NULL COMMENT '题干',
  `question_image` varchar(255) DEFAULT NULL COMMENT '题目图片',
  `options` varchar(500) DEFAULT NULL COMMENT '选项Map',
  `right_key` varchar(255) NOT NULL COMMENT '正确答案（选择题录入答案内容）',
  `analysis` varchar(255) DEFAULT NULL COMMENT '试题解析',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `creator_id` bigint NOT NULL COMMENT '创建人用户编号',
  `creator_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `type_id` bigint NOT NULL COMMENT '题目类型',
  `course_id` bigint NOT NULL COMMENT '所属课程',
  `difficult` enum('1','2','3') NOT NULL COMMENT '试题难度',
  `used_count` int DEFAULT '0' COMMENT '使用量',
  `fill_count` tinyint DEFAULT NULL COMMENT '填空数量',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_score
-- ----------------------------
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score` (
  `score_id` bigint NOT NULL AUTO_INCREMENT COMMENT '成绩编号（id）',
  `score` tinyint NOT NULL COMMENT '成绩',
  `paper_id` bigint NOT NULL COMMENT '试卷编号（id）',
  `times` varchar(30) DEFAULT NULL COMMENT '考试用时',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT NULL COMMENT '提交状态（1：已提交，0：未提交）',
  `username` varchar(50) DEFAULT NULL COMMENT '学号',
  `full_name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`score_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_term
-- ----------------------------
DROP TABLE IF EXISTS `t_term`;
CREATE TABLE `t_term` (
  `term_id` bigint NOT NULL AUTO_INCREMENT COMMENT '学期主键',
  `term_name` varchar(200) DEFAULT NULL COMMENT '学期名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`term_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目类型主键',
  `type_name` varchar(15) NOT NULL COMMENT '类型名称',
  `score` int DEFAULT NULL COMMENT '题目参考分值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
