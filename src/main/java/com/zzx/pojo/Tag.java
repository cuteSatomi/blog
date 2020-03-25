package com.zzx.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;
    //该标签的博客总数，只是方便在页面显示，并不存入数据库
    private Integer blogNums;

    //一个标签下可能有多个博客
    private List<Blog> blogs;
}
