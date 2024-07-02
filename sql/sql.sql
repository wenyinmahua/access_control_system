create database if not exists access_control;
use access_control;

-- 创建管理员表
drop table if exists admin;
CREATE TABLE if not exists admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '管理员ID',
    adminAccount VARCHAR(50) NOT NULL UNIQUE COMMENT '管理员账号',
    adminPassword VARCHAR(50) NOT NULL COMMENT '管理员密码',
    adminName VARCHAR(20) NOT NULL COMMENT '管理员姓名',
    gender TINYINT DEFAULT  0 COMMENT '管理员性别: 0-男 | 1-女',
    age INT COMMENT '管理员年龄',
    position VARCHAR(100) COMMENT '岗位',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) COMMENT '管理员表';

-- 创建用户表
drop table if exists user;
CREATE TABLE if not exists user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(20) NOT NULL COMMENT '用户姓名',
    gender TINYINT DEFAULT  0 COMMENT '用户性别: 0-男 | 1-女', 
    age INT COMMENT '用户年龄',
    position VARCHAR(100) COMMENT '岗位',
    createTime datetime default CURRENT_TIMESTAMP comment '注册时间',
    updateTime datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间'
) COMMENT '用户表';

-- 创建出入表
drop table if exists access;
CREATE TABLE if not exists access (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '出入表ID',
    userId BIGINT NOT NULL COMMENT '用户ID',
    userName VARCHAR(20) NOT NULL COMMENT '用户姓名',
    gender TINYINT DEFAULT  0 COMMENT '用户性别: 0-男 | 1-女',
    checkInTime DATETIME DEFAULT NULL COMMENT '签到时间',
    checkInStatus  TINYINT DEFAULT 0 COMMENT '0-未签到 | 1-已签到',
    checkInImage TEXT COMMENT '签到状态图片',
    checkOutTime DATETIME DEFAULT NULL COMMENT '签退时间',
    checkOutStatus TINYINT DEFAULT 0 COMMENT '0-未签退 | 1-已签退',
    checkOutImage TEXT COMMENT '签退状态图片',
    flag VARCHAR(4) DEFAULT '签退' COMMENT '标志'
) COMMENT '出入表';

-- 创建异常记录表
drop table if exists exception_record;
CREATE TABLE if not exists exception_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '异常记录ID',
    recognitionTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '识别时间',
    recognitionImage TEXT COMMENT '识别图片',
    isAlarm TINYINT DEFAULT 0 COMMENT '1-报警 | 2-记录'
) COMMENT '异常记录表';

-- 创建日志表
drop table if exists log;
CREATE TABLE if not exists log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    logDate DATE DEFAULT (CURRENT_DATE) COMMENT '日志日期',
    totalCheckedIn INT DEFAULT 0 COMMENT '签到总次数',
    totalCheckedOut INT DEFAULT 0 COMMENT '签退总次数',
    totalRecognitionFailures INT DEFAULT 0 COMMENT '识别失败总次数'
) COMMENT '日志表';