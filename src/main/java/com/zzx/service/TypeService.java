package com.zzx.service;

import com.zzx.pojo.Type;

import java.util.List;

public interface TypeService {

    //分页查询所有的分类
    List<Type> findAll(Integer page,Integer pageSize);

    //根据id查询分类
    Type findById(Long id);

    //更新分类
    int updateType(Type type);

    //保存分类信息
    int saveType(Type type);

    //根据id删除分类
    int delectById(Long id);

    //根据分类名称查询分类
    Type findByTypeName(String typeName);
}
