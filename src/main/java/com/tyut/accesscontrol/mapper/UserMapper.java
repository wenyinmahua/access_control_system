package com.tyut.accesscontrol.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyut.accesscontrol.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {

	@Select("select id from access_control.user")
	List<Long> getUserIds();
}


