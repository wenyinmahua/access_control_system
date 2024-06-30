package com.tyut.accesscontrol.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.model.entity.User;
import com.tyut.accesscontrol.service.UserService;
import com.tyut.accesscontrol.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author mahua
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-07-01 00:14:42
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




