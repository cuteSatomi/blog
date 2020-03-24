package com.zzx.dao;

import com.zzx.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where username=#{username} and password=#{password}")
    User checkUser(@Param("username") String username, @Param("password") String password);
}
