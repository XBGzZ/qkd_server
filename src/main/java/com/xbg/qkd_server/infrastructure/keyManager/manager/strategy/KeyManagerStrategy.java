package com.xbg.qkd_server.infrastructure.keyManager.manager.strategy;

import com.xbg.qkd_server.infrastructure.keyManager.factory.strategy.FactoryStrategy;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-28 21:58
 */
public enum KeyManagerStrategy {
    SIMPLE_KEY_MANAGER;

    public static boolean contain(String name) {
        FactoryStrategy[] values = FactoryStrategy.values();
        for (FactoryStrategy strategy: values) {
            if(strategy.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
