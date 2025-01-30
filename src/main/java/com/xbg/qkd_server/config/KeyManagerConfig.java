package com.xbg.qkd_server.config;

import com.xbg.qkd_server.common.annotations.ConfigCheck;
import com.xbg.qkd_server.config.checker.FactoryConfigChecker;
import com.xbg.qkd_server.config.checker.KeyManagerConfigChecker;
import com.xbg.qkd_server.infrastructure.keyManager.factory.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityManager;
import com.xbg.qkd_server.infrastructure.keyManager.cache.SimpleCache;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.config.MPOKeyManagerConfig;

import com.xbg.qkd_server.infrastructure.keyManager.factory.*;
import com.xbg.qkd_server.infrastructure.keyManager.manager.SimpleKeyEntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;

import static com.xbg.qkd_server.common.constant.ConfigConstants.*;


/**
 * @author XBG
 * @description: 密钥工厂的策略工厂
 * @date 2025/1/20 23:37
 */
@Configuration
public class KeyManagerConfig {

    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX_KEY_FACTORY_CONFIG, name = CONFIG_PREFIX_KEY_FACTORY_STRATEGY, havingValue = "simple_factory")
    @ConfigCheck(FactoryConfigChecker.class)
    public KeyEntityFactory simpleKeyEntityFactory(BaseKeyManagerConfig config) {
        return new SimpleKeyEntityFactory(config.getKeyFactoryConfig());
    }

    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX_KEY_FACTORY_CONFIG, name = CONFIG_PREFIX_KEY_FACTORY_STRATEGY, havingValue = "time_record_factory")
    @ConfigCheck(FactoryConfigChecker.class)
    public KeyEntityFactory timeRecordKeyEntityFactory(BaseKeyManagerConfig config) {
        return new TimeRecordKeyEntityFactory(config.getKeyFactoryConfig());
    }


    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX_KEY_FACTORY_CONFIG, name = CONFIG_PREFIX_KEY_FACTORY_STRATEGY, havingValue = "simple_white_list_factory")
    @ConfigCheck(FactoryConfigChecker.class)
    public KeyEntityFactory simpleWhiteListKeyEntityFactory(MPOKeyManagerConfig config) {
        WhiteListKeyEntityFactory whiteListKeyEntityFactory = new WhiteListKeyEntityFactory();
        whiteListKeyEntityFactory.initialize(new SimpleKeyEntityFactory(config.getKeyFactoryConfig()));
        return new WhiteListKeyEntityFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX_KEY_FACTORY_CONFIG, name = CONFIG_PREFIX_KEY_FACTORY_STRATEGY, havingValue = "time_record_white_list_factory")
    @ConfigCheck(FactoryConfigChecker.class)
    public KeyEntityFactory timeRecordWhiteListKeyEntityFactory(MPOKeyManagerConfig config) {
        WhiteListKeyEntityFactory whiteListKeyEntityFactory = new WhiteListKeyEntityFactory();
        whiteListKeyEntityFactory.initialize(new TimeRecordKeyEntityFactory(config.getKeyFactoryConfig()));
        return whiteListKeyEntityFactory;
    }

    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX_KEY_MANAGER_CONFIG, name = CONFIG_PREFIX_KEY_MANAGER_STRATEGY, havingValue = "simple_key_manager")
    @ConfigCheck(KeyManagerConfigChecker.class)
    public KeyEntityManager simpleKeyEntityManager(SimpleKeyEntityFactory factory, BaseKeyManagerConfig config) {
        return new SimpleKeyEntityManager(new SimpleCache(config.getMaxKeyCount()), config, factory);
    }


}
