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

    @Select("select t.*,count(t.id) as blogNums from blog_tags bt left outer join blog b on bt.blog_id=b.id " +
            " left outer join tag t on t.id=bt.tag_id group by id order by blogNums desc limit 0,#{size}")
    public List<Tag> findTopTags(Integer size);

    @Select("select * from tag where id in( select tag_id from blog_tags where blog_id = #{id})")
    List<Tag> findTagsByBlogId(Long id);
}
