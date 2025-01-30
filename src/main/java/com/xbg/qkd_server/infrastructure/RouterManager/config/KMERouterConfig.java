package com.xbg.qkd_server.infrastructure.RouterManager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  KME路由配置项
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:58
 */
@Data
public class KMERouterConfig {

    String kmeId;

    String ipAddr;

    Integer port;

    List<SAERouterConfig> sae;
}
