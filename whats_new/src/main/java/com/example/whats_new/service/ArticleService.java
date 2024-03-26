package com.example.whats_new.service;

import com.example.whats_new.pojo.Article;
import com.example.whats_new.pojo.PageBean;

public interface ArticleService {
    void addArticle(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void updateArticle(Article article);

    Article viewArticle(String articleId);

    void executeLike(String articleId);

    void executeFavorite(String articleId);
}
