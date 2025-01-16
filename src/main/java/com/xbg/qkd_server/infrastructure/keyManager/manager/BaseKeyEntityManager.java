package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.infrastructure.keyManager.KeyForm;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityManager;
import com.xbg.qkd_server.infrastructure.keyManager.keyEntity.SimpleKeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.states.ManagerState;
import lombok.Builder;

import java.util.List;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/1/14 22:40
 */
public abstract class BaseKeyEntityManager<T extends ManagerState, F extends BaseKeyEntityManager.SimpleForm> implements KeyEntityManager<T, BaseKeyEntityManager.SimpleForm>{

    @Builder
    public static class SimpleForm implements KeyForm {

    }

}
