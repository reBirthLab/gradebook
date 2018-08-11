package com.rebirthlab.gradebook.application;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.rebirthlab.gradebook.application.util.CustomKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Anastasiy
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Value("${hazelcast.evict-time.user}")
    private Integer userEvictTime;

    @Bean
    public Config hazelcastCacheConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-cache");

        MapConfig adminCache = new MapConfig();
        adminCache.setTimeToLiveSeconds(userEvictTime);
        adminCache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("admin-cache", adminCache);

        MapConfig lecturerCache = new MapConfig();
        lecturerCache.setTimeToLiveSeconds(userEvictTime);
        lecturerCache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("lecturer-cache", lecturerCache);

        MapConfig studentCache = new MapConfig();
        studentCache.setTimeToLiveSeconds(userEvictTime);
        studentCache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("student-cache", studentCache);

        return config;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }
}
