package com.tyut.accesscontrol.mapper;

import com.tyut.accesscontrol.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> selectAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(@Param("id") int id);

    @Insert("INSERT INTO user (username, gender, age, position) VALUES (#{username}, #{gender}, #{age}, #{position})")
    int insert(User user);

    @Update("UPDATE user SET username = #{username}, gender = #{gender}, age = #{age}, position = #{position} WHERE id = #{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(@Param("id") int id);
}


