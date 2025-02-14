package com.example.whats_new.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
 
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@Configuration
public class RedisConfig {
    @Bean
    public RedisCacheConfiguration provideRedisCacheConfiguration(){
        //加载默认配置
        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig();
        //返回Jackson序列化器
        return conf.serializeValuesWith(
                RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericFastJsonRedisSerializer()));
    }
}