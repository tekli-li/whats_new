package com.example.whats_new.controller;

import com.example.whats_new.pojo.Article;
import com.example.whats_new.pojo.PageBean;
import com.example.whats_new.pojo.Result;
import com.example.whats_new.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;
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
    public Result viewArticle(@RequestBody String articleId) {
        Article article = articleService.viewArticle(articleId);
        return Result.success(article);
    }
    @PostMapping("/like")
    public Result like(@RequestBody String articleId) {
        articleService.executeLike(articleId);
        return Result.success();
    }

    @PostMapping("/favorite")
    public Result favorite(@RequestBody String articleId) {
        articleService.executeFavorite(articleId);
        return Result.success();
    }


}
