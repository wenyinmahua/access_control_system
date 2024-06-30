package com.tyut.accesscontrol.constant;

/**
 * 用户常量
 *
 * @author mahua
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";


    //  region 权限

    /**
     * 默认权限
     */
    String DEFAULT_ROLE = "user";

    Integer DEFAULT_ROLE_CODE = 0;

    /**
     * 管理员权限
     */
    String ADMIN_ROLE = "admin";

    Integer ADMIN_ROLE_CODE = 1;

    // endregion
}
