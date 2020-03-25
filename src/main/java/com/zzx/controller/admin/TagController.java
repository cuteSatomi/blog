package com.zzx.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zzx.pojo.Tag;
import com.zzx.pojo.Type;
import com.zzx.service.TagService;
import com.zzx.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          Model model) {
        List<Tag> tags = tagService.findAll(page, pageSize);
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        model.addAttribute("page", pageInfo);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input() {
        return "admin/tags-input";
    }

    @PostMapping("/tags/save")
    public String saveTag(Tag tag, RedirectAttributes attributes,Model model) {
        //先根据接收的分类名称去查询该标签是否已存在
        Tag t = tagService.findByTagName(tag.getName());
        //若已经存在该标签,则添加失败
        if (t != null) {
            model.addAttribute("message", "标签:\"" + tag.getName() + "\"已存在,添加失败!");
            return "admin/tags-input";
        }
        tagService.saveTag(tag);
        attributes.addFlashAttribute("message", "新增标签\"" + tag.getName() + "\"成功");
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/update")
    public String editInput(@PathVariable(name = "id")Long id,Model model){
        model.addAttribute("tag",tagService.findById(id));
        return "admin/tags-update";
    }

    @PostMapping("/tags/update")
    public String updateType(Tag tag, RedirectAttributes attributes,Model model) {
        //先根据接收的标签名称去查询该标签是否已存在
        Tag t = tagService.findByTagName(tag.getName());
        //若已经存在该标签,则添加失败
        if (t != null) {
            model.addAttribute("message", "标签:\"" + tag.getName() + "\"已存在,更新失败!");
            return "admin/tags-update";
        }
        tagService.updateTag(tag);
        attributes.addFlashAttribute("message", "更新标签\"" + tag.getName() + "\"成功");
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteType(@PathVariable(name = "id")Long id,RedirectAttributes attributes){
        attributes.addFlashAttribute("message","删除标签\"" + tagService.findById(id).getName() + "\"成功");
        tagService.delectById(id);
        return "redirect:/admin/tags";
    }
}
