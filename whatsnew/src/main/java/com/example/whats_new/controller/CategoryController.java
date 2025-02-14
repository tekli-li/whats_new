package com.example.whats_new.controller;

import com.example.whats_new.pojo.Category;
import com.example.whats_new.pojo.Result;
import com.example.whats_new.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/addCategory")
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }
    @GetMapping("/getAllCategory")
    public Result getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        return Result.success(categories);
    }
    @GetMapping("/getCatDetail")
    public Result getCatDetail(Integer id) {
        Category category = categoryService.getCatDetail(id);
        return Result.success(category);
    }
    @PutMapping("/updateCat")
    public Result updateCat(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.updateCat(category);
        return Result.success();
    }
    @DeleteMapping("/deleteCat")
    public Result deleteCat(Integer id) {
        categoryService.deleteCat(id);
        return Result.success();
    }
}
