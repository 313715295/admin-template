package com.admin.web.config;

import com.admin.web.serializer.FastJsonRedisSerializer;
import com.admin.web.serializer.ProtostuffSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wangl on 2017/11/25.
 * todo:
 */
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {


    /**
     * 配置缓存管理，springboot2.0后 参数为RedisConnectionFactory
     * 不用再配置以前的RedisTemplate 序列化方式和缓存时间在RedisCacheConfiguration已配置
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(30), // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

    /**
     * 不同key配置不同缓存失效时间
     */
    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("user", this.getRedisCacheConfigurationWithTtl(60));
        return redisCacheConfigurationMap;
    }

    /**
     * 设定缓存配置和失效时间，单位分
     */
    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer minutes) {
        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
//        ProtostuffSerializer<Object> serializer = new ProtostuffSerializer<>(Object.class);  //谷歌序列化不能用object.class
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        redisCacheConfiguration = redisCacheConfiguration
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .entryTtl(Duration.ofMinutes(minutes));
        return redisCacheConfiguration;
    }

}