package com.zzx.controller;

import com.github.pagehelper.PageInfo;
import com.zzx.pojo.Blog;
import com.zzx.pojo.Tag;
import com.zzx.pojo.Type;
import com.zzx.service.BlogService;
import com.zzx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String types(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
                        @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.findTopTags(10000);
        //当从导航请求时,id值为-1,默认选中博客条数最多的标签
        if (id == -1) {
            id = tags.get(0).getId();
        }

        List<Blog> blogs = blogService.findByTagId(page, pageSize, id);
        //将标签集合设置进每一个blog中
        for (Blog blog : blogs) {
            blog.setTags(tagService.findTagsByBlogId(blog.getId()));
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("page", pageInfo);
        model.addAttribute("tags", tags);
        //将当前激活的标签id传递给页面
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
