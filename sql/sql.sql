create database if not exists access_control;
use access_control;

-- 创建管理员表
drop table if exists admin;
create table admin
(
    id           bigint auto_increment comment '管理员ID' primary key,
    userAccount  varchar(50)                        not null comment '用户账号',
    userPassword varchar(255)                       not null comment '用户密码',
    username     varchar(100)                       null comment '用户姓名',
    gender       tinyint  default 0                 null comment '用户性别: 0-男 | 1-女',
    age          int                                null comment '用户年龄',
    position     varchar(100)                       null comment '岗位',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint userAccount
        unique (userAccount)
) comment '管理员表';


-- 创建用户表
drop table if exists user;
create table if not exists user
(
    id         bigint auto_increment comment '用户id' primary key,
    username   varchar(100)                       not null comment '用户姓名',
    gender     tinyint  default 0                 null comment '用户性别: 0-男 | 1-女',
    age        int                                null comment '用户年龄',
    position   varchar(100)                       null comment '岗位',
    createTime datetime default CURRENT_TIMESTAMP null comment '注册时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '用户表';

-- 创建出入表
drop table if exists access;
create table if not exists access
(
    id             bigint auto_increment comment '出入表ID' primary key,
    userId         bigint            not null comment '用户ID',
    checkInTime    datetime          null comment '签到时间',
    checkInStatus  tinyint default 0 null comment '0-未签到 | 1-已签到',
    checkInImage   longtext          null comment '签到状态图片',
    checkOutTime   datetime          null comment '签退时间',
    checkOutStatus tinyint default 0 null comment '0-未签退 | 1-已签退',
    checkOutImage  longtext          null comment '签退状态图片',
    flag           tinyint default 0 null comment '0-签退 | 1-签到',
    thisDay        date              null comment '数据日期'
) comment '出入表';



-- 创建异常记录表
drop table if exists exception_record;
create table if not exists exception_record
(
    id               bigint auto_increment comment '异常记录ID' primary key,
    recognitionTime  datetime default CURRENT_TIMESTAMP null comment '识别时间',
    recognitionImage longtext                           null comment '识别图片',
    isAlarm          tinyint                            null comment '1-报警 | 2-记录'
)comment '异常记录表';


-- 创建日志表
drop table if exists log;
create table if not exists log
(
    id                       bigint auto_increment comment '日志ID' primary key,
    logDate                  date          null comment '日志日期',
    totalCheckedIn           int default 0 null comment '签到总次数',
    totalCheckedOut          int default 0 null comment '签退总次数',
    totalRecognitionFailures int default 0 null comment '识别失败总次数'
) comment '日志表';
