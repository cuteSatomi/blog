package com.zzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzx.dao.TypeDao;
import com.zzx.pojo.Type;
import com.zzx.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> findAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return typeDao.findAll();
    }

    @Override
    public Type findById(Long id) {
        return typeDao.findById(id);
    }

    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Override
    public int delectById(Long id) {
        return typeDao.delectById(id);
    }

    @Override
    public Type findByTypeName(String typeName) {
        return typeDao.findByTypeName(typeName);
    }
}
