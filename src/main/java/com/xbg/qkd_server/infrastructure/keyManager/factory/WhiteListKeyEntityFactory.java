package com.xbg.qkd_server.infrastructure.keyManager.factory;

import cn.hutool.extra.spring.SpringUtil;
import com.xbg.qkd_server.common.annotations.KeyFactory;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

/**
 * @author XBG
 * @description: 白名单密钥工厂
 * @date 2025/1/14 0:49
 */
@Primary
@KeyFactory
public class WhiteListKeyEntityFactory extends BaseKeyEntityFactory<WhiteListKeyEntityFactory.WhiteListFactoryConfig> {

    @Override
    protected KeyEntity genrateKeyEntity(String id, WhiteListFactoryConfig config) {
        if (config.getFactoryClazz() == null){
            config.setFactoryClazz(SimpleKeyEntityFactory.class);
        }
        // 从 Spring 容器中获取 KeyEntityFactory 的 Bean
        BaseSimpleKeyEntityFactory bean = SpringUtil.getBean(config.getFactoryClazz());
        Optional<KeyEntity> simpleKeyEntity = bean.produceKeyEntity(config);
        return simpleKeyEntity.orElse(null);
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class WhiteListFactoryConfig extends BaseSimpleKeyEntityFactory.SimpleFactoryConfig {
        Class<? extends BaseSimpleKeyEntityFactory> factoryClazz;
    }

    @Override
    public WhiteListFactoryConfig getKeyConfig() {
        return new WhiteListFactoryConfig();
    }
}
