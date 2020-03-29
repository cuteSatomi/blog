package com.zzx.controller;

import com.github.pagehelper.PageInfo;
import com.zzx.pojo.Blog;
import com.zzx.pojo.Type;
import com.zzx.service.BlogService;
import com.zzx.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.findTopTypes(10000);
        //当从导航请求时,id值为-1,默认选中博客条数最多的分类
        if (id == -1) {
            id = types.get(0).getId();
        }
        List<Blog> blogs = blogService.findByTypeId(page,pageSize,id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("page", pageInfo);
        model.addAttribute("types", types);
        //将当前激活的标签id传递给页面
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
