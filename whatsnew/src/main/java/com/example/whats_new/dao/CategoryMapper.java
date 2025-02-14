package com.example.whats_new.dao;

import com.example.whats_new.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("INSERT INTO category(category_name, category_alias, create_user, create_time, update_time) values (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Category category);
    @Select("SELECT * from category")
    List<Category> getAllCategory();
    @Select("SELECT * from category where id=#{id}")
    Category getCatDetail(Integer id);
    @Update("UPDATE category SET category_name=#{categoryName}, category_alias=#{categoryAlias}, update_time=NOW() WHERE id=#{id}")
    void updateCat(Category category);
    @Delete("DELETE FROM category WHERE id=#{id}")
    void deleteCat(Integer id);
}
