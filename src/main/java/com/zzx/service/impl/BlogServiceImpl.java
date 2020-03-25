package com.zzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzx.dao.BlogDao;
import com.zzx.dto.BlogAndTag;
import com.zzx.dto.SearchBlog;
import com.zzx.pojo.Blog;
import com.zzx.pojo.User;
import com.zzx.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            blogDao.saveBlogAndTag(new BlogAndTag(blog.getId(),tagId));
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
}
