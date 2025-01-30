package com.xbg.qkd_server.infrastructure.RouterManager.config;

import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  SAE路由配置项
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:56
 */
@Data
public class SAERouterConfig {

    String saeId;

    String ipAddr;

    @Nullable
    Integer port;
}
