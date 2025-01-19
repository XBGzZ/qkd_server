package com.xbg.qkd_server.infrastructure.keyManager.keyEntity;

import cn.hutool.core.util.IdUtil;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author XBG
 * @description: 密钥实体测试
 * @date 2025/1/13 1:09
 */
@SpringBootTest
public class KeyEntityTest {

    @Test
    public void simpleKeyEntityTest() {
        String uuid = IdUtil.randomUUID();
        final Integer keySize = 256;
        final String user = "Bob";
        TimeRecordKeyEntity keyEntity = new TimeRecordKeyEntity(uuid,keySize);
        System.out.println("getKeyId: " + keyEntity.getKeyId());
        System.out.println("getKey: " + keyEntity.getKey());
        keyEntity.setOwner(user);
        System.out.println("getAllocateTime: " + keyEntity.getAllocateTime());
    }

    @Test
    public void whiteListEntityTest() {
        String uuid = IdUtil.randomUUID();
        final Integer keySize = 256;
        final String user = "Bob";
        KeyEntity timeEntity = new SimpleKeyEntity(uuid,keySize);
        KeyEntity keyEntity = new WhiteListKeyEntity(timeEntity);
        System.out.println("getKeyId: " + keyEntity.getKeyId());
        System.out.println("getKey: " + keyEntity.getKey());
        keyEntity.setOwner(user);
        System.out.println("getAllocateTime: " + keyEntity.getAllocateTime());
    }

}
