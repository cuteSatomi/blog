package com.zzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzx.dao.BlogDao;
import com.zzx.dto.BlogAndTag;
import com.zzx.dto.SearchBlog;
import com.zzx.exception.NotFountException;
import com.zzx.pojo.Blog;
import com.zzx.pojo.User;
import com.zzx.service.BlogService;
import com.zzx.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Blog> findAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return blogDao.findAll();
    }

    @Override
    public Blog findByBlogId(Long id) {
        return blogDao.findByBlogId(id);
    }

    @Override
    public int updateBlog(Blog blog) {
        //更新博客时更新时间
        blog.setUpdateTime(new Date());
        //更新前还应该删除中间表中的映射关系
        blogDao.deleteBlogAndTag(blog.getId());
        //再插入数据到中间表
        saveBlogAndTag(blog);
        return blogDao.updateBlog(blog);
    }

    @Override
    public int deleteBlogById(Long id) {
        //删除博客前也应该删除中间表中的数据
        blogDao.deleteBlogAndTag(id);
        return blogDao.deleteBlogById(id);
    }

    @Override
    public int saveBlog(Blog blog, User user) {
        //保存博客的同时插入创建以及更新时间
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        //插入userId和user
        blog.setUser(user);
        blog.setUserId(user.getId());
        //初始化浏览次数
        blog.setViews(0);

        saveBlogAndTag(blog);

        return blogDao.saveBlog(blog);
    }

    private void saveBlogAndTag(Blog blog) {
        //由于blog和tag是多对多的关系,因此插入博客数据的同时也需要在中间表插入数据
        List<Long> tagIds = convert2List(blog.getTagIds());

        for (Long tagId : tagIds) {
            blogDao.saveBlogAndTag(new BlogAndTag(blog.getId(), tagId));
        }
    }

    private List<Long> convert2List(String tagIds) {
        List list = new ArrayList<Long>();
        for (String tagId : tagIds.split(",")) {
            list.add(Long.valueOf(tagId));
        }
        return list;
    }

    @Override
    public List<Blog> findByCondition(Integer page, Integer pageSize, SearchBlog searchBlog) {
        return blogDao.findByCondition(searchBlog);
    }

    @Override
    public List<Blog> findRecommendTopBlogs(Integer size) {
        return blogDao.findRecommendTopBlogs(size);
    }

    @Override
    public List<Blog> findByTitleOrContent(Integer page, Integer pageSize, String query) {
        PageHelper.startPage(page, pageSize);
        return blogDao.findByTitleOrContent(query);
    }

    @Override
    public Blog findAndConvert(Long id) {
        Blog blog = blogDao.findByBlogId(id);
        if(blog==null){
            throw new  NotFountException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogDao.updateViewsById(id);
        return blog;
    }

    @Override
    public List<Blog> findByTypeId(Integer page,Integer pageSize,Long typeId) {
        PageHelper.startPage(page,pageSize);
        return blogDao.findByTypeId(typeId);
    }

    @Override
    public List<Blog> findByTagId(Integer page, Integer pageSize, Long tagId) {
        PageHelper.startPage(page,pageSize);
        return blogDao.findByTagId(tagId);
    }

    @Override
    public Map<String, List<Blog>> archiveBlogs() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,blogDao.findBlogsByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.countBlog();
    }
}
