package com.example.whats_new.dao;

import com.example.whats_new.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article (title, content, cover_img, category_id, create_user, create_time, update_time, state) " +
            "VALUES (#{title}, #{content}, #{coverImg}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime}, #{state})")
    void addArticle(Article article);

    List<Article> list(Integer categoryId, String state);

    @Update("UPDATE article SET title=#{title}, content=#{content}, category_id=#{categoryId}, state=#{state}, " +
            "update_time=#{updateTime}, cover_img=#{coverImg}, favorites=#{favorites}, likes=#{likes}, view_num=#{viewNum} WHERE id=#{id}")
    void update(Article article);

    @Select("SELECT * FROM article WHERE id=#{articleId}")
    Article getArticle(Integer articleId);

    @Select("INSERT INTO view_history (user_id, article_id, view_time) " +
            "VALUES (#{userId}, #{articleId}, NOW())")
    void addViewRecord(String userId, Integer articleId);
    @Select("SELECT * FROM article")
    List<Article> listAllArticle();
    @Select("INSERT INTO like_table (user_id, article_id, create_time) " +
            "VALUES (#{userId}, #{articleId}, NOW())")
    void executeLike(Integer articleId, String userId);
    @Select("INSERT INTO favorite (user_id, article_id, create_time) " +
            "VALUES (#{userId}, #{articleId}, NOW())")
    void executeFavorite(Integer articleId, String userId);
    @Select("SELECT* FROM article ORDER BY view_num DESC LIMIT #{limit} OFFSET #{offset};")
    List<Article> hotest(Integer limit, Integer offset);
    @Delete("DELETE FROM like_table WHERE user_id=#{userId} AND article_id=#{articleId}")
    void cancelLike(Integer articleId, String userId);
    @Delete("DELETE FROM favorite WHERE user_id=#{userId} AND article_id=#{articleId}")
    void cancelFav(Integer articleId, String userId);

}
