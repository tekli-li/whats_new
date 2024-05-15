package com.example.whats_new.controller;

import com.example.whats_new.pojo.*;
import com.example.whats_new.service.ArticleService;
import com.example.whats_new.service.CommentService;
import com.example.whats_new.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
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

    @PutMapping("/updateCoverImg")
    public Result updateCoverImg(@RequestBody @Validated Article article) {
//        System.out.println(article);
        articleService.uploadCoverImg(article);
        return Result.success();
    }

    @GetMapping("/view")
    public Result viewArticle(Integer articleId) {
        Article article = articleService.getArticle(articleId);
        articleService.viewArticle(articleId);
        article = articleService.addViewNum(article);
        article = articleService.getArticle(articleId);
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

    @DeleteMapping("/cancelLike")
    public Result cancelLike(@RequestBody Map<String, Integer> requestBody) {
        Integer articleId = requestBody.get("articleId");
        articleService.cancelLike(articleId);
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

    @DeleteMapping("/cancelFav")
    public Result cancelFav(@RequestBody Map<String, Integer> requestBody) {
        Integer articleId = requestBody.get("articleId");
        articleService.cancelFav(articleId);
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
    public Result recommend (Integer pageNum, Integer pageSize) {
        Map<String , Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        PageBean<Article> recommend = articleService.recommend(id, pageNum, pageSize);
        return Result.success(recommend);
    }

    @PostMapping("/hotest")
    public Result hotest(@RequestBody LoadParams loadParams) {
        List<Article> list = articleService.hotest(loadParams.getLimit(), loadParams.getOffset());
        return Result.success(list);
    }



}
