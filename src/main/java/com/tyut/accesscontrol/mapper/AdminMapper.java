package com.tyut.accesscontrol.mapper;

import com.tyut.accesscontrol.model.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author mahua
* @description 针对表【admin(管理员表)】的数据库操作Mapper
* @createDate 2024-07-01 00:14:15
* @Entity com.tyut.accesscontrol.model.entity.Admin
*/
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin where adminName=#{adminName}")
    public Admin FindAdminByName(String adminName);
}




