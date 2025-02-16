package com.xbg.qkd_server.infrastructure.RouterManager;

import com.xbg.qkd_server.common.enums.RouterErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.common.tools.AuthUtils;
import com.xbg.qkd_server.common.tools.IpUtils;
import com.xbg.qkd_server.infrastructure.RouterManager.config.KMERouterConfig;
import com.xbg.qkd_server.infrastructure.RouterManager.config.RouterConfig;
import com.xbg.qkd_server.infrastructure.RouterManager.node.StaticRouterKMENode;
import com.xbg.qkd_server.infrastructure.RouterManager.node.StaticRouterSAENode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


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

@Slf4j
public class StaticKmeRouterManager implements KmeRouterManager {

    private final Map<String, KMENode> kmeNodeMap = new ConcurrentHashMap<>();

    private final Map<String, SAENode> saeNodeMap = new ConcurrentHashMap<>();

    private final String currentKMEId;

    private KMENode currentKMENode;

    public StaticKmeRouterManager(String currentKMEId) {
        this.currentKMEId = currentKMEId;
    }

    public void initConfig(RouterConfig routerConfig) {
        List<KMERouterConfig> kmeList = Optional.ofNullable(routerConfig.getKmeList()).orElse(List.of());
        if (!configCheck(routerConfig)) {
            throw new KMEException(RouterErrorCode.ERROR_ROUTER_CONFIG);
        }
        for (var kme : kmeList) {
            StaticRouterKMENode kmeNode = new StaticRouterKMENode(kme.getKmeId(), kme.getIpAddr(), kme.getPort());
            if (kme.getKmeId().equals(currentKMEId)) {
                currentKMENode = kmeNode;
            }
            kmeNodeMap.put(kmeNode.getId(), kmeNode);
            for (var sae : kme.getSaeIds()) {
                StaticRouterSAENode saeNode = new StaticRouterSAENode(sae);
                saeNodeMap.put(sae, saeNode);
                saeNode.bindKMENode(kmeNode);
            }
        }
        if (Objects.isNull(currentKMENode)) {
            throw new KMEException(RouterErrorCode.MISSING_CURRENT_KME_ID);
        }
    }

    /**
     * 配置校验
     *
     * @param routerConfig
     */
    protected boolean configCheck(RouterConfig routerConfig) {
        Set<String> nodeIdSet = new HashSet<>();
        Set<String> ipHost = new HashSet<>();
        int kmeIndex = 0;
        int saeIndex = 0;
        boolean containCurrKMEIdFlag = false;
        if (Objects.isNull(routerConfig) || Objects.isNull(routerConfig.getKmeList())) {
            log.error("config error, missing router config or kme list");
            return false;
        }
        for (var kme : routerConfig.getKmeList()) {
            String kmeId = kme.getKmeId();
            String kmeHost = kme.getIpAddr() + ":" + kme.getPort();
            if (currentKMEId.equals(kmeId)) {
                containCurrKMEIdFlag = true;
            }
            if (nodeIdSet.contains(kmeId)) {
                log.error("config error, kme[{}] has duplicate id [{}] ", kmeIndex, kmeId);
                return false;
            }
            nodeIdSet.add(kmeId);
            if (ipHost.contains(kmeHost)) {
                log.error("config error, kme[{}] has duplicate host [{}]  ", kmeIndex, kmeHost);
                return false;
            }
            ipHost.add(kmeHost);
            for (var saeId : kme.getSaeIds()) {
                if (nodeIdSet.contains(saeId)) {
                    log.error("config error, kme[{}]'s sae[{}] has duplicate id [{}] ", kmeIndex, saeIndex, saeId);
                    return false;
                }
                nodeIdSet.add(saeId);
                saeIndex++;
            }
            saeIndex = 0;
            kmeIndex++;
        }
        if (!containCurrKMEIdFlag) {
            log.error("config error, missing current kme name [{}]", currentKMEId);
            return false;
        }
        return true;
    }


    private <T extends SecurityNode> Optional<T> findNodeByNodeId(String nodeId, Map<String, T> nodeMap) {
        if (!StringUtils.hasLength(nodeId)) {
            return Optional.empty();
        }
        return nodeMap.values().stream()
                .filter(item -> nodeId.equals(item.nodeId()))
                .findAny();
    }



    @Override
    public Boolean updateSAEHostInfo(String saeId, Host host) {
        SAENode saeNode = saeNodeMap.get(saeId);
        if (Objects.isNull(saeNode)) {
            return false;
        }
        saeNode.updateHost(host);
        return true;
    }

    @Override
    public List<SecurityNode> getAllSecurityNodes() {
        List<SecurityNode> list = new ArrayList<>();
        list.addAll(kmeNodeMap.values());
        list.addAll(saeNodeMap.values());
        return list;
    }

    @Override
    public List<SAENode> getSAENodes() {
        return new ArrayList<>(saeNodeMap.values());
    }

    @Override
    public List<KMENode> getKMENodes() {
        return new ArrayList<>(kmeNodeMap.values());
    }

    @Override
    public Optional<SAENode> querySAEBySAEId(String saeId) {
        return findNodeByNodeId(saeId, saeNodeMap);
    }

    @Override
    public Optional<KMENode> queryKMEByKMEId(String kmeId) {
        return findNodeByNodeId(kmeId, kmeNodeMap);
    }

    @Override
    public SAENode getCurrentSAEId() {
        return saeNodeMap.get(AuthUtils.getCommonName());
    }

    @Override
    public KMENode getCurrentKME() {
        return currentKMENode;
    }

}

