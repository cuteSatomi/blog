package com.zzx.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    private Long id;
    private String name;

    //该分类的博客总数，只是方便在页面显示，并不存入数据库
    private Integer blogNums;

    //一个分类下可能有多个博客
    private List<Blog> blogs;
}
