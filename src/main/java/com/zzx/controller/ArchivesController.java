package com.zzx.controller;

import com.zzx.pojo.Blog;
import com.zzx.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> map = blogService.archiveBlogs();
        model.addAttribute("map",map);
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
