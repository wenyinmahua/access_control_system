package com.tyut.accesscontrol.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author mahua
 */
@Data
public class UserDemoAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;


    /**
     * 用户角色: 0 - user, 1 - admin
     */
    private Integer userRole;

    /**
     * 密码
     */
    private String userPassword;

    private static final long serialVersionUID = 1L;
}