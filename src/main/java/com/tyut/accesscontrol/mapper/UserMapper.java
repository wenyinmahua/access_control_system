package com.tyut.accesscontrol.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyut.accesscontrol.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {

	@Select("select id from access_control.user")
	List<Long> getUserIds();

	@Select("select id from access_control.user where username like  concat('%', #{username}, '%')")
	List<Long> getUserIdsByName(String username);

	@Select("select username from access_control.user where id =  #{userId} ")
	String getUserNameById(Long userId);
}


