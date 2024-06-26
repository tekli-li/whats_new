package com.example.whats_new.service;

import com.example.whats_new.pojo.Article;
import com.example.whats_new.pojo.PageBean;

import java.util.List;

public interface ArticleService {
    void addArticle(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    List<Article> listAllArticle();

    void updateArticle(Article article);

    void viewArticle(Integer articleId);

    Article getArticle(Integer articleId);

    void executeLike(Integer articleId);

    void executeFavorite(Integer articleId);

    PageBean<Article> recommend(Integer userId, Integer pageNum, Integer pageSize);

    Article addViewNum(Article article);

    Article addLikeNum(Article article);

    Article addFavoriteNum(Article article);

    Article uploadCoverImg(Article article);

    List<Article> hotest(Integer limit, Integer offset);

    void cancelLike(Integer articleId);

    void cancelFav(Integer articleId);
}
