package com.zzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBlog {
    //博客的标题
    private String title;
    //分类的id
    private Long typeId;
    //是否推荐
    private boolean recommend;
}
