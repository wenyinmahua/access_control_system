package com.tyut.accesscontrol.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserDTO implements Serializable {
    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户性别: 0-男 | 1-女
     */
    private Integer gender;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 岗位
     */
    private String position;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}