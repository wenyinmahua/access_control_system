package com.tyut.accesscontrol.mapper;

import com.tyut.accesscontrol.model.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE userAccount = #{userAccount}")
    Admin findByUserAccount(@Param("userAccount") String userAccount);

    @Insert("INSERT INTO admin (userAccount, userPassword, username, gender, age, position) VALUES (#{userAccount}, #{userPassword}, #{username}, #{gender}, #{age}, #{position})")
    int insert(Admin admin);

    @Update("UPDATE admin SET userPassword=#{newPassword}, username=#{newUsername}, gender=#{newGender}, age=#{newAge}, position=#{newPosition} WHERE id=#{id}")
    void update(Admin admin);

    @Delete("DELETE FROM admin WHERE id = #{id}")
    void deleteById(@Param("id") Long id);

    @Select("SELECT * FROM admin")
    List<Admin> selectAll();
}




