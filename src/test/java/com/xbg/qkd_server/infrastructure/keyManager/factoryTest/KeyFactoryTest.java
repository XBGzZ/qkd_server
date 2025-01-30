package com.xbg.qkd_server.infrastructure.keyManager.factoryTest;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.factory.KeyEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.TestKeyTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void t() {
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);

        List<Integer> integerList = ints.subList(0, 2);
        integerList.clear();
        for(var ite:ints) {
            System.out.println(ite);
        }

        for(var ite:integerList) {
            System.out.println(ite);
        }
    }
}
