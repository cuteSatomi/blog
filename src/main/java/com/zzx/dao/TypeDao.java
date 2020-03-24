package com.zzx.dao;

import com.zzx.pojo.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeDao {
    @Select("select * from type")
    List<Type> findAll();

    @Select("select * from type where id=#{id}")
    Type findById(Long id);

    @Update("update type set name=#{name} where id=#{id}")
    int updateType(Type type);

    @Insert("insert into type (name) values(#{name})")
    int saveType(Type type);

    @Delete("delete from type where id=#{id}")
    int delectById(Long id);

    @Select("select * from type where name=#{name}")
    Type findByTypeName(String typeName);
}
