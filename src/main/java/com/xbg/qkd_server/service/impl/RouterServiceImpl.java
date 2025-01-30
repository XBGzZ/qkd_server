package com.xbg.qkd_server.service.impl;

import com.xbg.qkd_server.infrastructure.RouterManager.KmeRouterManager;
import com.xbg.qkd_server.service.IRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  静态SAE注册中心，基于配置实现
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:25
 */
@Service
public class RouterServiceImpl implements IRouterService {

    @Autowired
    KmeRouterManager routerManager;

    @Override
    public String getCurrConnectSAEId() {
        return routerManager.getCurrentSAEId();
    }

    @Override
    public String getCurrConnectKMEId() {
        return routerManager.getCurrentKME();
    }


}
