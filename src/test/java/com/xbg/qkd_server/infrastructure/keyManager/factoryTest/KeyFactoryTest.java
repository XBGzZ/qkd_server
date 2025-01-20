package com.xbg.qkd_server.infrastructure.keyManager.factoryTest;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.TestKeyTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

/**
 * @author XBG
 * @description: 密钥实体工厂测试类
 * @date 2025/1/14 2:01
 */
@SpringBootTest
public class KeyFactoryTest {

    @Autowired
    KeyEntityFactory keyEntityFactory;


    @Test
    public void simpleKeyFactory() {
        Optional<KeyEntity> keyEntity = keyEntityFactory.produceKeyEntity(1024);
        keyEntity.get().setOwner("XBG");
        String key = TestKeyTool.getKey(keyEntity.get());
        System.out.println(keyEntityFactory.getClass());
        System.out.println(key);
    }


}
