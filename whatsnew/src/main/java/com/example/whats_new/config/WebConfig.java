package com.example.whats_new.config;

import com.example.whats_new.interceptors.LoginInterceptor;
import com.example.whats_new.interceptors.RoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private RoleInterceptor roleInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");
        registry.addInterceptor(roleInterceptor).excludePathPatterns("/user/**", "/article/**", "category/getAllCategory/**", "category/getCatDetail/**");
    }
}
