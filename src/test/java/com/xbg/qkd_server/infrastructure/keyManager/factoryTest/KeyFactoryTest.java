package com.xbg.qkd_server.infrastructure.keyManager.factoryTest;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactoryConfig;
import com.xbg.qkd_server.infrastructure.keyManager.factory.BaseSimpleKeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.factory.SimpleKeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.factory.TimeRecordKeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.factory.WhiteListKeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.TestKeyTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;

/**
 * @author XBG
 * @description: 密钥实体工厂测试类
 * @date 2025/1/14 2:01
 */
@SpringBootTest
public class KeyFactoryTest {

    @Autowired
    SimpleKeyEntityFactory simpleKeyEntityFactory;


    @Test
    public void simpleKeyFactory() {
        var keyConfig = simpleKeyEntityFactory.getKeyConfig();
        keyConfig.setKeySize(1024);
        Optional<KeyEntity> keyEntity = simpleKeyEntityFactory.produceKeyEntity(keyConfig);
        keyEntity.get().setOwner("XBG");
        String key = TestKeyTool.getKey(keyEntity.get());
        System.out.println(key);
    }

    @Autowired
    TimeRecordKeyEntityFactory timeRecordKeyEntityFactory;


    @Test
    public void timeRecordKeyEntityFactory() {
        var keyConfig = timeRecordKeyEntityFactory.getKeyConfig();
        keyConfig.setKeySize(1024);
        Optional<KeyEntity> keyEntity = timeRecordKeyEntityFactory.produceKeyEntity(keyConfig);
        keyEntity.get().setOwner("YSQ");
        String key = TestKeyTool.getKey(keyEntity.get());
        System.out.println(key);
    }

    @Autowired
    WhiteListKeyEntityFactory whiteListKeyEntityFactory;

    @Test
    public void whiteListKeyEntityFactory() {
        var keyConfig = whiteListKeyEntityFactory.getKeyConfig();
        keyConfig.setKeySize(1024);
        keyConfig.setFactoryClazz(TimeRecordKeyEntityFactory.class);
        Optional<KeyEntity> keyEntity = whiteListKeyEntityFactory.produceKeyEntity(keyConfig);
        if (keyEntity.isEmpty()){
            return;
        }
        keyEntity.get().setOwner("YSQ");
        String key = TestKeyTool.getKey(keyEntity.get());
        System.out.println(key);
    }
}
