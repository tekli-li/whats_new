package com.example.whats_new.service;

import com.example.whats_new.pojo.Comment;
import com.example.whats_new.pojo.CommentTree;

import java.util.List;

public interface CommentService {
    List<CommentTree> getCommentsByArticle(Integer articleId);

    void addArticleComment(Comment comment);

}
