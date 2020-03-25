package com.zzx.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zzx.dto.SearchBlog;
import com.zzx.pojo.Blog;
import com.zzx.pojo.Tag;
import com.zzx.pojo.Type;
import com.zzx.pojo.User;
import com.zzx.service.BlogService;
import com.zzx.service.TagService;
import com.zzx.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          Model model) {
        List<Blog> blogs = blogService.findAll(page, pageSize);
        List<Type> types = typeService.findAll(page, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("page", pageInfo);
        //查询博客时还要查询所有的分类
        model.addAttribute("types", types);
        return "admin/blogs";
    }

    @PostMapping("/blogs/searchBlog")
    public String searchBlog(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                             Model model, SearchBlog searchBlog) {
        List<Type> types = typeService.findAll(page, pageSize);
        List<Blog> blogs = blogService.findByCondition(page, pageSize, searchBlog);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("page", pageInfo);
        model.addAttribute("types", types);
        return "admin/blogs";
    }

    @GetMapping("/blogs/input")
    public String input(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                        Model model) {
        //需要查询出所有的分类和标签到博客新增页面
        model.addAttribute("types", typeService.findAll(page, pageSize));
        model.addAttribute("tags", tagService.findAll(page, pageSize));
        return "admin/blogs-input";
    }

    @PostMapping("/blogs/saveBlog")
    public String saveBlog(Blog blog, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        //一些初始化等琐碎的操作在业务层完成
        blogService.saveBlog(blog, user);
        attributes.addFlashAttribute("message", "新增博客\"" + blog.getTitle() + "\"成功");
        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs/updateBlog")
    public String updateBlog(Blog blog, RedirectAttributes attributes) {
        blogService.updateBlog(blog);
        attributes.addFlashAttribute("message", "修改博客\"" + blog.getTitle() + "\"成功");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/update")
    public String editInput(@RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                            @PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("types", typeService.findAll(page, pageSize));
        model.addAttribute("tags", tagService.findAll(page, pageSize));
        model.addAttribute("blog", blogService.findByBlogId(id));
        return "admin/blogs-update";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", "删除博客\"" + blogService.findByBlogId(id).getTitle() + "\"成功");
        blogService.deleteBlogById(id);
        return "redirect:/admin/blogs";
    }
}
