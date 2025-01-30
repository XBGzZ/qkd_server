package com.xbg.qkd_server.infrastructure.keyManager.factory.strategy;

public enum FactoryStrategy {
    SIMPLE_FACTORY,
    TIME_RECORD_FACTORY,
    SIMPLE_WHITE_LIST_FACTORY,
    TIME_RECORD_WHITE_LIST_FACTORY;

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
