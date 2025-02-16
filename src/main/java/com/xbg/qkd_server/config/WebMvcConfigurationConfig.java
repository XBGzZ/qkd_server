package com.xbg.qkd_server.config;

import com.xbg.qkd_server.interceptor.StaticRouterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/2/2 10:49
 */
@Configuration
public class WebMvcConfigurationConfig implements WebMvcConfigurer {
    @Autowired
    StaticRouterInterceptor ipInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor).addPathPatterns("/**");
    }



}
