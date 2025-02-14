package com.example.whats_new.service;

import com.example.whats_new.pojo.Article;
import com.example.whats_new.pojo.PageBean;
import com.example.whats_new.pojo.User;
import com.example.whats_new.pojo.ViewHistory;

public interface UserService {
    User findUserByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updatePwd(String newPwd, int salt);

    void uploadAvatar(String fileUrl);

    void updateAvatar(String url);

    PageBean<Article> getViewHistory(Integer pageNum, Integer pageSize);

    PageBean<Article> getFavorites(Integer pageNum, Integer pageSize);

    ViewHistory viewHistoryIsExist(Integer userId, Integer articleId);

}
