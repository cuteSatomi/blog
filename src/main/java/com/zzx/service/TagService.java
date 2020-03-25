package com.zzx.service;

import com.zzx.pojo.Tag;

import java.util.List;

public interface TagService {

    //分页查询所有的标签
    List<Tag> findAll(Integer page, Integer pageSize);

    //根据id查询标签
    Tag findById(Long id);

    //更新标签
    int updateTag(Tag tag);

    //保存标签信息
    int saveTag(Tag tag);

    //根据id删除标签
    int delectById(Long id);

    //根据标签名称查询标签
    Tag findByTagName(String tagName);
}
