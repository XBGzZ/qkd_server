package com.xbg.qkd_server.config;

import com.xbg.qkd_server.config.filters.AddContentLengthFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author XBG
 * @Description:
 * @Date 2025-04-02
 */

@Configuration
public class FilterConfig {

    /**
     * 给特定接口加content-length返回
     * @return FilterRegistrationBean
     */
    @Bean
    @ConditionalOnBean(AddContentLengthFilter.ConfigProcess.class)
    public FilterRegistrationBean<AddContentLengthFilter> contentLengthFilterRegistrationBean(AddContentLengthFilter.ConfigProcess process) {
        if (!process.getEnabledChunkedMode()) {
            return null;
        }
        FilterRegistrationBean<AddContentLengthFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new AddContentLengthFilter());
        List<String> urls = new ArrayList<>();
        // 只针对指定接口类型返回content-length
        urls.add("*");
        filterBean.setUrlPatterns(urls);
        return filterBean;
    }
}

