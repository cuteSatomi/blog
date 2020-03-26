package com.zzx.service;

import com.zzx.dto.SearchBlog;
import com.zzx.pojo.Blog;
import com.zzx.pojo.User;

import java.util.List;

public interface BlogService {

    //查找所有的博客
    List<Blog> findAll(Integer page,Integer pageSize);

    //根据id查找博客
    Blog findByBlogId(Long id);

    //更新博客
    int updateBlog(Blog blog);

    //删除博客
    int deleteBlogById(Long id);

    //新增博客
    int saveBlog(Blog blog, User user);

    //根据条件查询博客
    List<Blog> findByCondition(Integer page, Integer pageSize, SearchBlog searchBlog);

    //查询推荐博客
    List<Blog> findRecommendTopBlogs(Integer size);

    //首页的查询
    List<Blog> findByTitleOrContent(Integer page,Integer pageSize,String query);

    //查找并转换博客正文的格式
    Blog findAndConvert(Long id);
}
