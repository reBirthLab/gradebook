package com.rebirthlab.gradebook.application;

import com.rebirthlab.gradebook.application.service.user.UserDetails;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Anastasiy
 */
@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${resource.id}")
    private String resourceId;
    @Autowired
    private ResourceServerProperties resource;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/api/**").access("#oauth2.hasScope('write') and hasRole('ROLE_USER')");
        //.antMatchers("/api/**").authenticated();
        http.cors();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(resourceId)
                .tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        String keyValue = resource.getJwt().getKeyValue();
        if (!StringUtils.hasText(keyValue)) {
            keyValue = (String) new RestTemplate()
                    .getForObject(resource.getJwt().getKeyUri(), Map.class)
                    .get("value");
        }
        if (keyValue != null) {
            converter.setVerifierKey(keyValue);
        }
        converter.setAccessTokenConverter(accessTokenConverter());
        return converter;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {
            @Override
            @SuppressWarnings("unchecked")
            public Authentication extractAuthentication(Map<String, ?> map) {
                Authentication authentication = super.extractAuthentication(map);
                UserDetails user = new UserDetails(map.get("client_id").toString(),
                        map.get("user_name").toString(),
                        (List<String>) map.get("scope"),
                        Instant.ofEpochSecond((Long) map.get("exp")),
                        (List<String>) map.get("authorities"));
                return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(),
                        authentication.getAuthorities());
            }
        });
        return tokenConverter;
    }
}