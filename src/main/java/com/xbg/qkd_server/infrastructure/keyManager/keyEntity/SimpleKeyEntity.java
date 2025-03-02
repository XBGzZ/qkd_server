package com.xbg.qkd_server.infrastructure.keyManager.keyEntity;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author XBG
 * @description: 基本密钥对象
 * @date 2025/1/1 22:27
 */
@ToString
public class SimpleKeyEntity implements KeyEntity {
    // 密钥Id
    private final String id;
    // 密钥本体
    private byte[] key;

    private Integer keySize;
    // 所属 SAE
    private String owner;

    public SimpleKeyEntity(String id, Integer keySize) {
        this.id = id;
        key = RandomUtil.randomBytes(keySize / Byte.SIZE);
        this.keySize = keySize;
    }

    @Override
    public Boolean isUsing() {
        return StringUtils.hasLength(owner);
    }

    @Override
    public String getKey() {
        return Base64.encode(key);
    }

    @Override
    public String getKeyId() {
        return id;
    }

    @Override
    public Boolean setOwner(String owner) {
        if (!StringUtils.hasLength(this.owner)) {
            this.owner = owner;
            return true;
        }
        return false;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public Long getAllocateTime() {
        return 0L;
    }

    @Override
    public Integer getKeySize() {
        return keySize;
    }

    @Override
    public byte[] getByteKey() {
        return key;
    }

    @Override
    public void mergeKey(List<KeyEntity> otherKeys) {
        int totalLength = key.length;
        for (KeyEntity otherKey : otherKeys) {
            totalLength += otherKey.getByteKey().length;
        }
        ByteBuffer buffer = ByteBuffer.wrap(new byte[totalLength]);
        buffer.put(key);
        for (KeyEntity otherKey : otherKeys) {
           buffer.put(otherKey.getByteKey());
        }
        key = buffer.array();
        keySize = totalLength;
    }

    @Override
    public void mergeKey(KeyEntity otherKey) {
        int totalLength = key.length + otherKey.getByteKey().length;
        ByteBuffer buffer = ByteBuffer.wrap(new byte[totalLength]);
        buffer.put(key);
        buffer.put(otherKey.getByteKey());
        key = buffer.array();
        keySize = totalLength;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SimpleKeyEntity that = (SimpleKeyEntity) o;
        return Objects.equals(id, that.id) && Arrays.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Arrays.hashCode(key));
    }


}
