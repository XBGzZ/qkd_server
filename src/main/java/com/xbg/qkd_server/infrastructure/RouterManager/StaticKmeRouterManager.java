package com.xbg.qkd_server.infrastructure.RouterManager;

import com.xbg.qkd_server.common.tools.IpUtils;
import com.xbg.qkd_server.infrastructure.RouterManager.config.KMERouterConfig;
import com.xbg.qkd_server.infrastructure.RouterManager.config.RouterConfig;
import com.xbg.qkd_server.infrastructure.RouterManager.node.KMENode;
import com.xbg.qkd_server.infrastructure.RouterManager.node.SAENode;
import com.xbg.qkd_server.infrastructure.RouterManager.node.SecurityNode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  静态路由表
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:55
 */

public class StaticKmeRouterManager implements KmeRouterManager {

    private final Map<SecurityNode.Host, KMENode> kmeNodeMap = new ConcurrentHashMap<>();

    private final Map<SecurityNode.Host, SAENode> saeNodeMap = new ConcurrentHashMap<>();

    private final String currentKMEId;
    public StaticKmeRouterManager(String currentKMEId) {
        this.currentKMEId = currentKMEId;
    }

    public void initConfig(RouterConfig routerConfig) {
        List<KMERouterConfig> kmeList = Optional.ofNullable(routerConfig.getKmeList()).orElse(List.of());
        for (var kme : kmeList) {
            KMENode kmeNode = new KMENode(kme.getKmeId(), kme.getIpAddr(), kme.getPort());
            kmeNodeMap.put(kmeNode.getHost(), kmeNode);
            for (var sae : kme.getSae()) {
                SAENode saeNode = new SAENode(sae.getSaeId(), sae.getIpAddr(), Optional.ofNullable(sae.getPort()).orElse(SecurityNode.Host.DEFAULT_PORT));
                saeNodeMap.put(saeNode.getHost(), saeNode);
                saeNode.bindKMENode(kmeNode);
            }
        }
    }


    private Optional<SAENode> findSaeByHost(SecurityNode.Host host) {
        Optional<SAENode> opt = Optional.empty();
        for (var saeHosts : saeNodeMap.keySet()) {
            if (hostMatchCheck(saeHosts, host)) {
                opt = Optional.of(saeNodeMap.get(saeHosts));
                break;
            }
        }
        return opt;
    }

    private boolean hostMatchCheck(SecurityNode.Host hostOfConfig, SecurityNode.Host hostOfReq) {
        if (!hostOfConfig.getIp().equals(hostOfReq.getIp())) {
            return false;
        }
        // 没有配置，或者配置的端口号为0
        if (Objects.isNull(hostOfConfig.getPort()) || hostOfConfig.getPort().equals(SecurityNode.Host.DEFAULT_PORT)) {
            return true;
        }
        return hostOfConfig.getPort().equals(hostOfReq.getPort());
    }

    @Override
    public String getCurrentSAEId() {
        String connectIP = IpUtils.getConnectIP();
        Integer connectPort = IpUtils.getConnectPort();
        Optional<SAENode> saeByHost = findSaeByHost(new SecurityNode.Host(connectIP, connectPort));
        if (saeByHost.isEmpty()) {
            return "";
        }
        return saeByHost.get().getId();
    }

    @Override
    public String getCurrentKME() {
        return currentKMEId;
    }
}
