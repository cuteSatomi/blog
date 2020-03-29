package com.zzx.dao;

import com.zzx.dto.BlogAndTag;
import com.zzx.dto.SearchBlog;
import com.zzx.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogDao {

    List<Blog> findAll();

    Blog findByBlogId(Long id);

    int updateBlog(Blog blog);

    int deleteBlogById(Long id);

    int saveBlog(Blog blog);

    List<Blog> findByCondition(SearchBlog searchBlog);

    void saveBlogAndTag(BlogAndTag blogAndTag);

    void deleteBlogAndTag(Long id);

    List<Blog> findRecommendTopBlogs(Integer size);

    List<Blog> findByTitleOrContent(String query);

    void updateViewsById(Long id);

    List<Blog> findByTypeId(Long typeId);

    List<Blog> findByTagId(Long tagId);

    List<String> findGroupYear();

    List<Blog> findBlogsByYear(String year);

    Long countBlog();
}
