create database if not exists access_control;
use access_control;
-- 创建管理员表
drop table if exists admin;
CREATE TABLE if not exists admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '管理员ID',
    userAccount VARCHAR(50) NOT NULL UNIQUE COMMENT '用户账号',
    userPassword VARCHAR(255) NOT NULL COMMENT '用户密码',
    username VARCHAR(100) NOT NULL COMMENT '用户姓名',
    gender TINYINT DEFAULT  0 COMMENT '用户性别: 0-男 | 1-女',
    age INT COMMENT '用户年龄',
    position VARCHAR(100) COMMENT '岗位',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
    ) COMMENT '管理员表';

-- 创建用户表
drop table if exists user;
CREATE TABLE if not exists user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(100) NOT NULL COMMENT '用户姓名',
    gender TINYINT DEFAULT  0 COMMENT '用户性别: 0-男 | 1-女',
    age INT COMMENT '用户年龄',
    position VARCHAR(100) COMMENT '岗位',
    createTime datetime default CURRENT_TIMESTAMP comment '注册时间',
    updateTime datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间'
    ) COMMENT '用户表';

-- 创建出入表
drop table if exists access;
CREATE TABLE if not exists access (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '出入表ID',
    userId BIGINT NOT NULL COMMENT '用户ID',
    flag VARCHAR(100) DEFAULT '签退',
    checkInTime DATETIME DEFAULT CURRENT_TIMESTAMP null,
    checkInStatus  TINYINT DEFAULT 0 COMMENT '0-签到失败 | 1-签到成功',
    checkInImage TEXT COMMENT '签到状态图片',
    checkIutTime DATETIME DEFAULT CURRENT_TIMESTAMP null,
    checkOutStatus TINYINT DEFAULT 0 COMMENT '0-签退失败 | 1-签退成功',
    checkOutImage TEXT COMMENT '签退状态图片'
) COMMENT '出入表';

-- 创建日志表
drop table if exists log;
CREATE TABLE if not exists log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    userId BIGINT COMMENT '用户ID',
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP null,
    checkInStatus  TINYINT DEFAULT 0 COMMENT '0-签到失败 | 1-签到成功',
    checkOutStatus TINYINT DEFAULT 0 COMMENT '0-签退失败 | 1-签退成功'
) COMMENT '日志表';