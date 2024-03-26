package com.example.whats_new.service.Impl;

import com.example.whats_new.dao.UserMapper;
import com.example.whats_new.pojo.User;
import com.example.whats_new.service.UserService;
import com.example.whats_new.utils.Md5Util;
import com.example.whats_new.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
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
}
