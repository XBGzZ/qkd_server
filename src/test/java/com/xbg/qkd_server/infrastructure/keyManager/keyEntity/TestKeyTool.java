package com.xbg.qkd_server.infrastructure.keyManager.keyEntity;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/1/14 2:10
 */
public class TestKeyTool {
    public static String getKey(KeyEntity keyEntity) {
        return  "getKeyId: " + keyEntity.getKeyId() + "\n" +
                "getKey: " + keyEntity.getKey() + "\n" +
                "owner: " + keyEntity.getOwner() + "\n" +
                "getAllocateTime: " + keyEntity.getAllocateTime() + "\n";
    }
}
