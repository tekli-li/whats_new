package com.example.whats_new.task;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.whats_new.pojo.Article;
import com.example.whats_new.service.ArticleService;
import com.example.whats_new.utils.RedisCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class updateNewsViewNumTask {

    private static final Logger log = LoggerFactory.getLogger(updateNewsViewNumTask.class);

    @Resource
    private RedisCache redisCache;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void updateViewCount() throws JsonProcessingException {
        log.info("开始从redis更新新闻浏览量==>");
        List<Article> articleList = articleService.listAllArticle();
        for (Article article : articleList) {
            ValueOperations<String , String> operations = stringRedisTemplate.opsForValue();
            String articleString = operations.get("articles::" + article.getId());
            JSONObject jsonObject = JSON.parseObject(articleString);
            Article articleData = null;
            if (jsonObject != null) {
                jsonObject.remove("@type");
                articleData = JSON.parseObject(jsonObject.toJSONString(), Article.class);
            }
            if (articleData != null) {
//                article = redisCache.getCacheObject("article::" + article.getId());
                articleService.updateArticle(articleData);
            }

        }
        log.info("<==新闻浏览量数据库与redis同步成功");
    }
}
