package com.xbg.qkd_server.service.impl;

import com.xbg.qkd_server.common.enums.ErrorCode;
import com.xbg.qkd_server.common.enums.RouterErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.infrastructure.RouterManager.KMENode;
import com.xbg.qkd_server.infrastructure.RouterManager.KmeRouterManager;
import com.xbg.qkd_server.infrastructure.RouterManager.SAENode;
import com.xbg.qkd_server.service.IRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
        return routerManager.getCurrentSAEId().nodeId();
    }

    @Override
    public String getCurrConnectKMEId() {
        return routerManager.getCurrentKME().nodeId();
    }

    @Override
    public String queryKMEbySAEId(String saeId) {
        Optional<SAENode> saeNode = routerManager.querySAEBySAEId(saeId);
        if (saeNode.isEmpty()) {
            throw new KMEException(RouterErrorCode.CAN_NOT_FIND_SAE_NODE);
        }
        if (Objects.isNull(saeNode.get().getKMENode())) {
            return "";
        }
        return saeNode.get().getKMENode().nodeId();
    }


}
