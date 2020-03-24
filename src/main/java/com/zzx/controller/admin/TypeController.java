package com.zzx.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zzx.pojo.Type;
import com.zzx.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          Model model) {
        List<Type> types = typeService.findAll(page, pageSize);
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        model.addAttribute("page", pageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input() {
        return "admin/types-input";
    }

    @PostMapping("/types/save")
    public String saveType(Type type, RedirectAttributes attributes,Model model) {
        //先根据接收的分类名称去查询该分类是否已存在
        Type t = typeService.findByTypeName(type.getName());
        //若已经存在该分类,则添加失败
        if (t != null) {
            model.addAttribute("message", "分类:\"" + type.getName() + "\"已存在,添加失败!");
            return "admin/types-input";
        }
        typeService.saveType(type);
        attributes.addFlashAttribute("message", "新增分类\"" + type.getName() + "\"成功");
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/update")
    public String editInput(@PathVariable(name = "id")Long id,Model model){
        model.addAttribute("type",typeService.findById(id));
        return "admin/types-update";
    }

    @PostMapping("/types/update")
    public String updateType(Type type, RedirectAttributes attributes,Model model) {
        //先根据接收的分类名称去查询该分类是否已存在
        Type t = typeService.findByTypeName(type.getName());
        //若已经存在该分类,则添加失败
        if (t != null) {
            model.addAttribute("message", "分类:\"" + type.getName() + "\"已存在,更新失败!");
            return "admin/types-update";
        }
        typeService.updateType(type);
        attributes.addFlashAttribute("message", "更新分类\"" + type.getName() + "\"成功");
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable(name = "id")Long id,RedirectAttributes attributes){
        attributes.addFlashAttribute("message","删除分类\"" + typeService.findById(id).getName() + "\"成功");
        typeService.delectById(id);
        return "redirect:/admin/types";
    }
}
