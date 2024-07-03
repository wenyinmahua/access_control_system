package com.tyut.accesscontrol.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 管理员注册请求体
 *
 * @author mahua
 */
@Data
public class AdminRegisterDTO implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
