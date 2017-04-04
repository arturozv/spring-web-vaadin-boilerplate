package com.shootr.web.core.config;

import com.google.common.cache.CacheBuilder;

import com.shootr.web.core.config.cache.Caches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = {MongoDBConfiguration.class})
public class CacheConfiguration extends CachingConfigurerSupport {
    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    @Bean
    public CacheManager inMemoryCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<GuavaCache> caches = new ArrayList<>();

        for (Map.Entry<String, Long> entry : Caches.ALL.entrySet()) {
            GuavaCache cache = new GuavaCache(entry.getKey(), CacheBuilder.newBuilder()
                    .expireAfterWrite(entry.getValue(), TimeUnit.SECONDS)
                    .build());
            caches.add(cache);
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

}