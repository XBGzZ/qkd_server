package com.xbg.qkd_server.common.constant;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 23:01
 */
public class ConfigConstants {
    // ===== 配置路径 ======
    public static final String CONFIG_SPLIT = ".";
    public static final String CONFIG_PREFIX_ROOT = "key-manager";

    // ===== manager配置路径 =====

    public static final String CONFIG_PREFIX_KEY_FACTORY = "key-factory-config";

    public static final String CONFIG_PREFIX_KEY_MANAGER = "key-manager-config";

    public static final String CONFIG_PREFIX_KEY_FACTORY_CONFIG = CONFIG_PREFIX_ROOT + CONFIG_SPLIT + CONFIG_PREFIX_KEY_FACTORY;

    public static final String CONFIG_PREFIX_KEY_MANAGER_CONFIG = CONFIG_PREFIX_ROOT + CONFIG_SPLIT + CONFIG_PREFIX_KEY_MANAGER;

    public static final String CONFIG_PREFIX_KEY_FACTORY_STRATEGY = "strategy";

    public static final String CONFIG_PREFIX_KEY_MANAGER_STRATEGY = "strategy";

    // ==== router配置路径 =====
    public static final String CONFIG_PREFIX_KME_ROUTER = "kme-router-config";

    public static final String CONFIG_PREFIX_KME_ROUTER_CONFIG = CONFIG_PREFIX_ROOT + CONFIG_SPLIT + CONFIG_PREFIX_KME_ROUTER;

    public static final String CONFIG_PREFIX_KME_ROUTER_STRATEGY = "strategy";
}
