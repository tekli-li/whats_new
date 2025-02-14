package com.example.whats_new.dao;

import com.example.whats_new.pojo.UserArticleRating;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RecommendMapper {
    @Select("SELECT * FROM user_article_rating WHERE user_id=#{userId} AND article_id=#{articleId};")
    UserArticleRating getRatingByUser(int userId, int articleId);
    @Update("UPDATE user_article_rating SET rating=#{rating} WHERE id=#{id};")
    void updateRating(UserArticleRating rating);
    @Insert("INSERT INTO user_article_rating (user_id, article_id, rating) " +
            "VALUES (#{userId},#{articleId},#{rating});")
    void addRating(UserArticleRating rating);
}
