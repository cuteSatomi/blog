package com.zzx.dao;

import com.zzx.pojo.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TagDao {

    @Select("select * from tag")
    public List<Tag> findAll();

    @Select("select * from tag where id=#{id}")
    public Tag findById(Long id);

    @Update("update tag set name=#{name} where id=#{id}")
    public int updateTag(Tag tag);

    @Insert("insert into tag (name) values (#{name})")
    public int saveTag(Tag tag);

    @Delete("delete from tag where id=#{id}")
    public int delectById(Long id);

    @Select("select * from tag where name=#{tagName}")
    public Tag findByTagName(String tagName);
}
