package com.example.whats_new.service;

import com.example.whats_new.pojo.User;

public interface UserService {
    User findUserByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updatePwd(String newPwd, int salt);

    void uploadAvatar(String fileUrl);

    void updateAvatar(String url);
}
