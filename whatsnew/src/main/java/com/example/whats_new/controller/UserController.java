package com.example.whats_new.controller;

import com.example.whats_new.config.MinioProp;
import com.example.whats_new.pojo.*;
import com.example.whats_new.service.MinioService;
import com.example.whats_new.service.UserService;
import com.example.whats_new.utils.JwtUtil;
import com.example.whats_new.utils.Md5Util;
import com.example.whats_new.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MinioService minioService;
    @Autowired
    private MinioProp minioProperties;
    @PostMapping("/register")
    public Result register(String username, String password) {
        //查询用户
        User u = userService.findUserByUserName(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户已存在");
        }
    }

    @PostMapping("/login")
    public Result login(String username, String password) {
        User user = userService.findUserByUserName(username);
        if (user == null) {
            return Result.error("用户名错误");
        }
        if (Md5Util.md5Digest(password, user.getSalt()).equals(user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            claims.put("role", user.getRole());
            String token = JwtUtil.genToken(claims);
            log.info(token);
            ValueOperations<String , String > operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 180, TimeUnit.DAYS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result userInfo() {
//        Map<String, Object> claims = JwtUtil.parseToken(token);
        Map<String , Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findUserByUserName(username);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params, @RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要参数");
        }
        Map<String , Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User loginUser = userService.findUserByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.md5Digest(oldPwd, loginUser.getSalt()))) {
            return Result.error("原密码不正确");
        }
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的密码不一致");
        }
        userService.updatePwd(newPwd, loginUser.getSalt());
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @GetMapping("/getViewRecord")
    public Result getViewRecord(Integer pageNum, Integer pageSize) {
        PageBean<Article> favorites = userService.getViewHistory(pageNum, pageSize);
        return Result.success(favorites);
    }

    @GetMapping("/getFavorite")
    public Result getFavorite(Integer pageNum, Integer pageSize) {
        PageBean<Article> favorites = userService.getFavorites(pageNum, pageSize);
        return Result.success(favorites);
    }
}
