package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.infrastructure.keyManager.config.MPOKeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.factory.SimpleBaseEntityFactory;
import com.xbg.qkd_server.infrastructure.keyManager.states.ManagerState;

/**
 * @author XBG
 * @description: 内存优化的密钥管理器
 * @date 2025/1/14 22:48
 */
public  abstract class MPOKeyEntityManager extends BaseKeyEntityManager<ManagerState, MPOKeyManagerConfig> {

}
