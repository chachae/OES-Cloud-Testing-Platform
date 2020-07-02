/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MongoDB
 Source Server Version : 40208
 Source Host           : localhost:27017
 Source Schema         : oes_cloud_route

 Target Server Type    : MongoDB
 Target Server Version : 40208
 File Encoding         : 65001

 Date: 26/06/2020 16:17:43
*/


// ----------------------------
// Collection structure for blackList
// ----------------------------
db.getCollection("blackList").drop();
db.createCollection("blackList");

// ----------------------------
// Collection structure for blockLog
// ----------------------------
db.getCollection("blockLog").drop();
db.createCollection("blockLog");

// ----------------------------
// Collection structure for rateLimitLog
// ----------------------------
db.getCollection("rateLimitLog").drop();
db.createCollection("rateLimitLog");

// ----------------------------
// Collection structure for rateLimitRule
// ----------------------------
db.getCollection("rateLimitRule").drop();
db.createCollection("rateLimitRule");

// ----------------------------
// Collection structure for routeLog
// ----------------------------
db.getCollection("routeLog").drop();
db.createCollection("routeLog");

// ----------------------------
// Collection structure for routeUser
// ----------------------------
db.getCollection("routeUser").drop();
db.createCollection("routeUser");
