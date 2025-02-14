package com.example.whats_new.service;

import com.example.whats_new.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> getAllCategory();

    Category getCatDetail(Integer id);

    void updateCat(Category category);

    void deleteCat(Integer id);
}
