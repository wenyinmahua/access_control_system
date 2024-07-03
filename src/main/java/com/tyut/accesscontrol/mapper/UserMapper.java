package com.tyut.accesscontrol.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyut.accesscontrol.model.entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper extends BaseMapper<User> {

}


