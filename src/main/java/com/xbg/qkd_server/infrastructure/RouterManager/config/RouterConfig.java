package com.xbg.qkd_server.infrastructure.RouterManager.config;

import com.xbg.qkd_server.infrastructure.RouterManager.strategy.RouterStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.xbg.qkd_server.common.constant.ConfigConstants.CONFIG_PREFIX_KME_ROUTER_CONFIG;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:38
 */
@Data
@Configuration
@ConfigurationProperties(prefix = CONFIG_PREFIX_KME_ROUTER_CONFIG)
public class RouterConfig {
    RouterStrategy strategy = RouterStrategy.STATIC_ROUTER;

    @Nullable
    List<KMERouterConfig> kmeList;
}
