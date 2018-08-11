package com.rebirthlab.gradebook.application;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryConfig;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.rebirthlab.gradebook.application.util.CustomKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Anastasiy
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Value("${hazelcast.evict-time.user}")
    private Integer userEvictTime;

    @Value("${hazelcast.k8s-discovery.service-dns:}")
    private String serviceDns;

    @Value("${hazelcast.k8s-discovery.service-dns-timeout:}")
    private String serviceDnsTimeout;

    @Bean
    @Profile("dev")
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

    @Bean
    @Profile("kubernetes")
    public Config hazelcastCacheKubernetesConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-cache");
        config.getProperties().put("hazelcast.discovery.enabled", true);

        NetworkConfig networkConfig = config.getNetworkConfig();

        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);

        DiscoveryConfig discoveryConfig = joinConfig.getDiscoveryConfig();

        HazelcastKubernetesDiscoveryStrategyFactory factory = new HazelcastKubernetesDiscoveryStrategyFactory();
        DiscoveryStrategyConfig k8sDiscoveryStrategy = new DiscoveryStrategyConfig(factory);
        k8sDiscoveryStrategy.addProperty("service-dns", serviceDns);
        k8sDiscoveryStrategy.addProperty("service-dns-timeout", serviceDnsTimeout);

        discoveryConfig.addDiscoveryStrategyConfig(k8sDiscoveryStrategy);

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
