package com.xbg.qkd_server.config;

import com.xbg.qkd_server.infrastructure.RouterManager.KmeRouterManager;
import com.xbg.qkd_server.infrastructure.RouterManager.StaticKmeRouterManager;
import com.xbg.qkd_server.infrastructure.RouterManager.config.RouterConfig;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import static com.xbg.qkd_server.common.constant.ConfigConstants.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-30 0:03
 */
@Configuration
public class KMERouterConfig {

    /**
     * 静态路由配置，通过配置文件进行加载
     * @param routerConfig
     * @param kmeConfig
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX_KME_ROUTER_CONFIG, name = CONFIG_PREFIX_KME_ROUTER_STRATEGY, havingValue = "static_router")
    public KmeRouterManager staticRouterManager(RouterConfig routerConfig, BaseKeyManagerConfig kmeConfig){
        StaticKmeRouterManager staticKmeRouterManager = new StaticKmeRouterManager(kmeConfig.getKmeId());
        staticKmeRouterManager.initConfig(routerConfig);
        return staticKmeRouterManager;
    }
}
