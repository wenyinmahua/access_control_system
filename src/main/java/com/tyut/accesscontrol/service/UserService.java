package com.tyut.accesscontrol.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.model.dto.UserQueryDTO;
import com.tyut.accesscontrol.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author mahua
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-07-01 00:14:42
*/
public interface UserService extends IService<User> {

	Page<User> getPageUser(UserQueryDTO userQueryDTO);

	Boolean updateUser(User user);

	Boolean deleteUserById(DeleteRequest deleteRequest);

	List<Long> getUserIds();
}
