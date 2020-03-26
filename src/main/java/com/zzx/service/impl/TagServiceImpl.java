package com.zzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzx.dao.TagDao;
import com.zzx.pojo.Tag;
import com.zzx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> findAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return tagDao.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagDao.findById(id);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public int delectById(Long id) {
        return tagDao.delectById(id);
    }

    @Override
    public Tag findByTagName(String tagName) {
        return tagDao.findByTagName(tagName);
    }

    @Override
    public List<Tag> findTopTags(Integer size) {
        return tagDao.findTopTags(size);
    }

    @Override
    public List<Tag> findTagsByBlogId(Long id) {
        return tagDao.findTagsByBlogId(id);
    }
}
