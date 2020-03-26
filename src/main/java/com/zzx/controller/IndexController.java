package com.zzx.controller;

import com.github.pagehelper.PageInfo;
import com.zzx.pojo.Blog;
import com.zzx.pojo.Tag;
import com.zzx.pojo.Type;
import com.zzx.service.BlogService;
import com.zzx.service.TagService;
import com.zzx.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                        Model model) {
        List<Blog> blogs = blogService.findAll(page, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        //找到前几个分类
        List<Type> types = typeService.findTopTypes(6);
        //找到前几个标签
        List<Tag> tags = tagService.findTopTags(10);
        //查询前几个推荐博客列表
        List<Blog> recommendBlogs = blogService.findRecommendTopBlogs(6);
        model.addAttribute("recommendBlogs",recommendBlogs);
        model.addAttribute("page", pageInfo);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                         String query,Model model){
        List<Blog> blogs = blogService.findByTitleOrContent(page,pageSize,query);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String findOne(@PathVariable(name = "id")Long id,Model model){
        model.addAttribute("blog",blogService.findAndConvert(id));
        model.addAttribute("tags",tagService.findTagsByBlogId(id));
        return "blog";
    }
}
