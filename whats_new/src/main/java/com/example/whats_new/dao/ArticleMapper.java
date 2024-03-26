package com.example.whats_new.dao;

import com.example.whats_new.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article (title, content, cover_img, category_id, create_user, create_time, update_time, state) " +
            "VALUES (#{title}, #{content}, #{coverImg}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime}, #{state})")
    void addArticle(Article article);

    List<Article> list(Integer categoryId, String state);

    @Update("UPDATE article SET title=#{title}, content=#{content}, category_id=#{categoryId}, state=#{state}, " +
            "update_time=#{updateTime}, cover_img=#{coverImg} WHERE id=#{id}")
    void update(Article article);
}
