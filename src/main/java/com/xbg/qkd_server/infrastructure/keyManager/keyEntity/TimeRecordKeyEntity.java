package com.xbg.qkd_server.infrastructure.keyManager.keyEntity;

import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * @author XBG
 * @description: 实现时间记录功能的密钥实体
 * @date 2025/1/13 1:35
 */
@ToString
public class TimeRecordKeyEntity extends SimpleKeyEntity implements KeyEntity {
    // 分配时间
    private Long allocateTime =  0L;

    public TimeRecordKeyEntity(String id, Integer keySize) {
        super(id, keySize);
    }

    @Override
    public Boolean setOwner(String saeId) {
        if(StringUtils.hasLength(getOwner()) && getOwner().equals(saeId)) {
            return true;
        }
        if (super.setOwner(saeId)) {
            recordAllocateTime();
            return true;
        }
        return false;
    }

    @Override
    public Long getAllocateTime() {
        return allocateTime;
    }

    private void recordAllocateTime() {
        this.allocateTime = System.currentTimeMillis();
    }
}
