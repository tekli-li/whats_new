package com.example.whats_new.service.Impl;

import com.example.whats_new.dao.ArticleMapper;
import com.example.whats_new.dao.UserMapper;
import com.example.whats_new.pojo.*;
import com.example.whats_new.service.UserService;
import com.example.whats_new.utils.Md5Util;
import com.example.whats_new.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public User findUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }


    @Override
    public void register(String username, String password) {
        Random random = new Random();
        Integer salt = random.nextInt(900) + 100;
        password = Md5Util.md5Digest(password, salt);
        userMapper.register(username, password, salt);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updatePwd(String newPwd, int salt) {
        Map<String , Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updatePwd(id, Md5Util.md5Digest(newPwd, salt));
    }

    @Override
    public void uploadAvatar(String fileUrl) {
        Map<String , Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.uploadAvatar(fileUrl, id);
    }

    @Override
    public void updateAvatar(String url) {
        Map<String , Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updateAvatar(url, id);
    }

    @Override
    public PageBean<Article> getViewHistory(Integer pageNum, Integer pageSize) {
        Map<String , Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        PageBean<ViewHistory> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<ViewHistory> viewHistories = userMapper.getViewHistory(id);
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

    @Override
    public PageBean<Article> getFavorites(Integer pageNum, Integer pageSize) {
        Map<String , Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        PageBean<Favorite> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Favorite> favorites = userMapper.getFavorites(id);
        Page<Favorite> p = (Page<Favorite>) favorites;

        //把数据填充到PageBean中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        List<Article> articles = new ArrayList<>();
        PageBean<Article> articlePb = new PageBean<>();
        articlePb.setTotal(p.getTotal());
        for (Favorite favorite : p.getResult()) {
            articles.add(articleMapper.getArticle(favorite.getArticleId()));
        }
        articlePb.setItems(articles);
        return articlePb;
    }

    @Override
    public ViewHistory viewHistoryIsExist(Integer userId, Integer articleId) {
        return userMapper.viewHistoryIsExist(userId, articleId);
    }
}
