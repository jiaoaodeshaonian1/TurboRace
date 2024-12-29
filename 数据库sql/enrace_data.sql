 
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for enrace_admin
-- ----------------------------
DROP TABLE IF EXISTS `enrace_admin`;
CREATE TABLE `enrace_admin`  (
  `ADMIN_ID` int NOT NULL AUTO_INCREMENT,
  `ADMIN_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ADMIN_PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ADMIN_DESC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ADMIN_PHONE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ADMIN_LOGIN_CNT` int NOT NULL DEFAULT 0,
  `ADMIN_TYPE` int NOT NULL DEFAULT 0,
  `ADMIN_STATUS` int NOT NULL DEFAULT 1,
  `ADMIN_LOGIN_TIME` bigint NOT NULL DEFAULT 0,
  `ADD_TIME` bigint NOT NULL DEFAULT 0,
  `EDIT_TIME` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`ADMIN_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
INSERT INTO `enrace_admin` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', NULL, 1, 1, 1, 1735302901541, 0, 1734786380484);

-- ----------------------------
-- Table structure for enrace_enroll
-- ----------------------------
DROP TABLE IF EXISTS `enrace_enroll`;
CREATE TABLE `enrace_enroll`  (
  `ENROLL_ID` int NOT NULL AUTO_INCREMENT,
  `ENROLL_TITLE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ENROLL_VOUCH` int NOT NULL DEFAULT 0,
  `ENROLL_STATUS` int NOT NULL DEFAULT 0,
  `ENROLL_CATE_ID` int NOT NULL DEFAULT 0,
  `ENROLL_CATE_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ENROLL_MAX_CNT` int NOT NULL DEFAULT 0,
  `ENROLL_START` bigint NOT NULL,
  `ENROLL_END` bigint NOT NULL,
  `ENROLL_ORDER` int NOT NULL DEFAULT 9999,
  `ENROLL_VIEW_CNT` int NOT NULL,
  `ENROLL_JOIN_CNT` int NOT NULL DEFAULT 9,
  `ENROLL_FORMS` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ENROLL_OBJ` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ADD_TIME` bigint NULL DEFAULT 0,
  `EDIT_TIME` bigint NULL DEFAULT 0,
  PRIMARY KEY (`ENROLL_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for enrace_enroll_join
-- ----------------------------
DROP TABLE IF EXISTS `enrace_enroll_join`;
CREATE TABLE `enrace_enroll_join`  (
  `ENROLL_JOIN_ID` int NOT NULL AUTO_INCREMENT,
  `ENROLL_JOIN_ENROLL_ID` int NOT NULL DEFAULT 0,
  `ENROLL_JOIN_USER_ID` int NOT NULL DEFAULT 0,
  `ENROLL_JOIN_STATUS` int NOT NULL DEFAULT 0,
  `ADD_TIME` bigint NOT NULL DEFAULT 0,
  `EDIT_TIME` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`ENROLL_JOIN_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2224 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for enrace_fav
-- ----------------------------
DROP TABLE IF EXISTS `enrace_fav`;
CREATE TABLE `enrace_fav`  (
  `FAV_ID` int NOT NULL AUTO_INCREMENT,
  `FAV_USER_ID` int NOT NULL DEFAULT 0,
  `FAV_TITLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FAV_TYPE` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FAV_OID` int NOT NULL DEFAULT 0,
  `FAV_PATH` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ADD_TIME` bigint NOT NULL DEFAULT 0,
  `EDIT_TIME` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`FAV_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for enrace_news
-- ----------------------------
DROP TABLE IF EXISTS `enrace_news`;
CREATE TABLE `enrace_news`  (
  `NEWS_ID` int NOT NULL AUTO_INCREMENT,
  `NEWS_TITLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `NEWS_CATE_ID` int NOT NULL DEFAULT 0,
  `NEWS_CATE_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `NEWS_STATUS` int NOT NULL DEFAULT 1,
  `NEWS_ORDER` int NOT NULL DEFAULT 9999,
  `NEWS_VOUCH` int NOT NULL DEFAULT 0,
  `NEWS_CONTENT` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `NEWS_VIEW_CNT` int NOT NULL DEFAULT 0,
  `NEWS_PIC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `NEWS_OBJ` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `NEWS_FORMS` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `ADD_TIME` bigint NOT NULL DEFAULT 0,
  `EDIT_TIME` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`NEWS_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for enrace_setup
-- ----------------------------
DROP TABLE IF EXISTS `enrace_setup`;
CREATE TABLE `enrace_setup`  (
  `SETUP_ID` int NOT NULL AUTO_INCREMENT,
  `SETUP_TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SETUP_KEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SETUP_VALUE` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ADD_TIME` bigint NOT NULL DEFAULT 0,
  `EDIT_TIME` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`SETUP_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for enrace_user
-- ----------------------------
DROP TABLE IF EXISTS `enrace_user`;
CREATE TABLE `enrace_user`  (
  `USER_ID` int NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `USER_ACCOUNT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `USER_STATUS` int NOT NULL DEFAULT 1,
  `USER_PASSWORD` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `USER_LOGIN_TIME` bigint NOT NULL DEFAULT 0,
  `USER_LOGIN_CNT` int NOT NULL DEFAULT 0,
  `USER_OBJ` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `USER_FORMS` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `ADD_TIME` bigint NOT NULL DEFAULT 0,
  `EDIT_TIME` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1320 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
