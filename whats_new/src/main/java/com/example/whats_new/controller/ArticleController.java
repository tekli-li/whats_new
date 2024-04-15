package com.example.whats_new.controller;

import com.example.whats_new.pojo.*;
import com.example.whats_new.service.ArticleService;
import com.example.whats_new.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@CrossOrigin
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody @Validated Article article) {
        log.info(article.getState());
        articleService.addArticle(article);
        return Result.success();
    }
    @GetMapping("/listArticle")
    public Result listArticleByCat(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }
    @PutMapping("/updateArticle")
    public Result updateArticle(@RequestBody @Validated Article article) {
//        System.out.println(article);
        articleService.updateArticle(article);
        return Result.success();
    }
    @GetMapping("/view")
    public Result viewArticle(Integer articleId) {
        Article article = articleService.getArticle(articleId);
        articleService.viewArticle(articleId);
        article = articleService.addViewNum(article);
        return Result.success(article);
    }
    @PostMapping("/like")
    public Result like(@RequestBody Map<String, Integer> requestBody) {
        Integer articleId = requestBody.get("articleId");
        articleService.executeLike(articleId);
        Article article = articleService.getArticle(articleId);
        articleService.addLikeNum(article);
        return Result.success();
    }

    @PostMapping("/favorite")
    public Result favorite(@RequestBody Map<String, Integer> requestBody) {
        Integer articleId = requestBody.get("articleId");
        articleService.executeFavorite(articleId);
        Article article = articleService.getArticle(articleId);
        articleService.addFavoriteNum(article);
        return Result.success();
    }
    @PostMapping("/addArticleComment")
    public Result addArticleComment(@RequestBody Comment comment) {
        commentService.addArticleComment(comment);
        return Result.success();
    }
    @GetMapping("/getCommentsByArticle")
    public Result getCommentsByArticle(Integer articleId) {
        List<CommentTree> commentTrees = commentService.getCommentsByArticle(articleId);
        return Result.success(commentTrees);
    }
    @GetMapping("/recommend")
    public Result recommend (Integer pageNum, Integer pageSize, Integer userId) {
        PageBean<Article> recommend = articleService.recommend(userId, pageNum, pageSize);
        return Result.success(recommend);
    }
}
