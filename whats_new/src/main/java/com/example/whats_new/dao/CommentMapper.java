package com.example.whats_new.dao;

import com.example.whats_new.pojo.Comment;
import com.example.whats_new.pojo.CommentTree;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    void addArticleComment(Comment comment);
    @Select("SELECT * FROM comment WHERE article_id=#{articleId}")
    List<Comment> getCommentsByArticle(Integer articleId);
    @Select("SELECT * FROM comment WHERE root=#{Id}")
    List<Comment> getSubComments(int id);
}
