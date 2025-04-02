package com.xbg.qkd_server.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * @Author XBG
 * @Description: 添加ContentLength
 * @Date 2025-04-02
 */

public class AddContentLengthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingResponseWrapper cacheResponseWrapper;
        if (!(response instanceof ContentCachingResponseWrapper)) {
            cacheResponseWrapper = new ContentCachingResponseWrapper(response);
        } else {
            cacheResponseWrapper = (ContentCachingResponseWrapper) response;
        }
        filterChain.doFilter(request, cacheResponseWrapper);
        cacheResponseWrapper.copyBodyToResponse();
    }

    @Configuration
    @ConfigurationProperties(prefix = "server")
    @Data
    public static class ConfigProcess {
        final public static String ENABLE_CHUNKED_MODE = "enabled-chunked-mode";
        private Boolean enabledChunkedMode = true;
    }
}
