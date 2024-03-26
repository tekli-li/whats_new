package com.example.whats_new.service.Impl;

import com.example.whats_new.dao.CategoryMapper;
import com.example.whats_new.pojo.Category;
import com.example.whats_new.service.CategoryService;
import com.example.whats_new.utils.ThreadLocalUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String , Object> user = ThreadLocalUtil.get();
        Integer userId = (Integer) user.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    @Override
    public Category getCatDetail(Integer id) {
        return categoryMapper.getCatDetail(id);
    }

    @Override
    public void updateCat(Category category) {
        categoryMapper.updateCat(category);
    }

    @Override
    public void deleteCat(Integer id) {
        categoryMapper.deleteCat(id);
    }
}
