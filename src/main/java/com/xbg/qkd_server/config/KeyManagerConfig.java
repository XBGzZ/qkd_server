package com.xbg.qkd_server.config;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.config.MPOKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.factory.FactoryStrategy;
import com.xbg.qkd_server.infrastructure.keyManager.factory.SimpleKeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.factory.TimeRecordKeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.factory.WhiteListKeyEntityFactory;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManager;

/**
 * @author XBG
 * @description: 密钥工厂的策略工厂
 * @date 2025/1/20 23:37
 */
@Configuration
public class KeyManagerConfig {

    @Bean
    @ConditionalOnProperty(prefix = "key-manager.key-factory-config", name = "strategy",havingValue = "simple_factory")
    public KeyEntityFactory simpleKeyEntityFactory(MPOKeyManagerConfig config) {
        return new SimpleKeyEntityFactory(config.getKeyFactoryConfig());
    }

    @Bean
    @ConditionalOnProperty(prefix = "key-manager.key-factory-config", name = "strategy",havingValue = "time_record_factory")
    public KeyEntityFactory timeRecordKeyEntityFactory(MPOKeyManagerConfig config) {
        return new TimeRecordKeyEntityFactory(config.getKeyFactoryConfig());
    }


    @Bean
    @ConditionalOnProperty(prefix = "key-manager.key-factory-config", name = "strategy",havingValue = "simple_white_list_factory")
    public KeyEntityFactory simpleWhiteListKeyEntityFactory(MPOKeyManagerConfig config) {
        WhiteListKeyEntityFactory whiteListKeyEntityFactory = new WhiteListKeyEntityFactory();
        whiteListKeyEntityFactory.initialize(new SimpleKeyEntityFactory(config.getKeyFactoryConfig()));
        return new WhiteListKeyEntityFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = "key-manager.key-factory-config", name = "strategy",havingValue = "time_record_white_list_factory")
    public KeyEntityFactory timeRecordWhiteListKeyEntityFactory(MPOKeyManagerConfig config) {
        WhiteListKeyEntityFactory whiteListKeyEntityFactory = new WhiteListKeyEntityFactory();
        whiteListKeyEntityFactory.initialize(new TimeRecordKeyEntityFactory(config.getKeyFactoryConfig()));
        return whiteListKeyEntityFactory;
    }
}
