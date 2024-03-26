package com.example.whats_new.service.Impl;

import com.example.whats_new.dao.ArticleMapper;
import com.example.whats_new.pojo.Article;
import com.example.whats_new.pojo.PageBean;
import com.example.whats_new.service.ArticleService;
import com.example.whats_new.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(Article article) {
        Map<String , Object> user = ThreadLocalUtil.get();
        Integer id = (Integer) user.get("id");
        article.setCreateUser(id);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        log.info(article.getState());
        articleMapper.addArticle(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper
        List<Article> articles = articleMapper.list(categoryId, state);
        //查询结果需要转换为Page类型
        Page<Article> p = (Page<Article>) articles;

        //把数据填充到PageBean中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void updateArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public Article viewArticle(String articleId) {
        Map<String , Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("id");
        return null;
    }

    @Override
    public void executeLike(String articleId) {

    }

    @Override
    public void executeFavorite(String articleId) {

    }
}
