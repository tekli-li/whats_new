package com.example.whats_new.service.Impl;

import com.example.whats_new.dao.ArticleMapper;
import com.example.whats_new.dao.UserMapper;
import com.example.whats_new.pojo.*;
import com.example.whats_new.service.ArticleService;
import com.example.whats_new.service.UserService;
import com.example.whats_new.utils.RatingCalculateUtil;
import com.example.whats_new.utils.Recommend;
import com.example.whats_new.utils.RedisCache;
import com.example.whats_new.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private Recommend recommend;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RatingCalculateUtil ratingCalculateUtil;

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
    public List<Article> listAllArticle() {
        return articleMapper.listAllArticle();
    }

    @Override
    public void updateArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void viewArticle(Integer articleId) {
        //获取用户id
        Map<String , Object> claims = ThreadLocalUtil.get();
        String userId = String.valueOf(claims.get("id")) ;
//        Article article = articleMapper.getArticle(articleId);
        ViewHistory viewHistory = userService.viewHistoryIsExist(Integer.valueOf(userId), articleId);
        ratingCalculateUtil.refreshRating("view", Integer.parseInt(userId), articleId);
        //写入浏览记录
        if (viewHistory != null) {
            userMapper.updateViewHistory(viewHistory.getId());
        } else {
            articleMapper.addViewRecord(userId, articleId);
        }
    }

    @Override
    public Article getArticle(Integer articleId) {
        return articleMapper.getArticle(articleId);
    }

    @Override
    public void executeLike(Integer articleId) {
        //获取用户id
        Map<String , Object> claims = ThreadLocalUtil.get();
        String userId = String.valueOf(claims.get("id")) ;
        Article article = articleMapper.getArticle(articleId);
        ratingCalculateUtil.refreshRating("like", Integer.parseInt(userId), articleId);
        articleMapper.executeLike(articleId, userId);
    }

    @Override
    public void executeFavorite(Integer articleId) {
        //获取用户id
        Map<String , Object> claims = ThreadLocalUtil.get();
        String userId = String.valueOf(claims.get("id")) ;
        Article article = articleMapper.getArticle(articleId);
        ratingCalculateUtil.refreshRating("favorite", Integer.parseInt(userId), articleId);
        articleMapper.executeFavorite(articleId, userId);
    }

    @Override
    public PageBean<Article> recommend(Integer userId, Integer pageNum, Integer pageSize) {
        List<User> users = userMapper.listUsers();
        Map<Integer, Double> relations = recommend.computeNearestNeighbor(userId, users);
        // 将 Map 转换为 List<Map.Entry<Integer, Double>>，方便排序
        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(relations.entrySet());

        // 使用 Collections.sort 对 List 进行排序，按照 Double 类型的值倒序排列
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // 将排序后的 List 转换回 Map
        LinkedHashMap<Integer, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        Integer firstKey = sortedMap.keySet().iterator().next();
        PageBean<ViewHistory> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<ViewHistory> viewHistories = userMapper.getViewHistory(firstKey);
        Page<ViewHistory> p = (Page<ViewHistory>) viewHistories;

        //把数据填充到PageBean中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        List<Article> articles = new ArrayList<>();
        PageBean<Article> articlePb = new PageBean<>();
        articlePb.setTotal(p.getTotal());
        for (ViewHistory viewHistory : p.getResult()) {
            articles.add(articleMapper.getArticle(viewHistory.getArticleId()));
        }
        articlePb.setItems(articles);
        return articlePb;
    }


    @CachePut(cacheNames = "articles",key = "(#article.getId())")
    public Article addViewNum(Article article) {
        article.setViewNum(article.getViewNum() + 1);
        return article;
    }

    @CachePut(cacheNames = "articles",key = "(#article.getId())")
    public Article addLikeNum(Article article) {
        article.setLikes(article.getLikes() + 1);
        return article;
    }

    @CachePut(cacheNames = "articles",key = "(#article.getId())")
    public Article addFavoriteNum(Article article) {
        article.setFavorites(article.getFavorites() + 1);
        return article;
    }

    @PostConstruct
    public void init() {
        log.info("新闻浏览量写入缓存开始==>");
        List<Article> articleList = listAllArticle();
        Map<Integer, Integer> newsViewMap = articleList.stream().collect(Collectors.toMap(Article::getId, Article::getViewNum));
        redisCache.setCacheMap("newsViewHistory", newsViewMap);
        log.info("<==新闻浏览量写入缓存成功");
    }


}
