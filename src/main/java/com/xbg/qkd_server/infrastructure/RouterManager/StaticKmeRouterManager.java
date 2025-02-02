package com.xbg.qkd_server.infrastructure.RouterManager;

import com.xbg.qkd_server.common.enums.RouterErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.common.tools.IpUtils;
import com.xbg.qkd_server.infrastructure.RouterManager.config.KMERouterConfig;
import com.xbg.qkd_server.infrastructure.RouterManager.config.RouterConfig;
import com.xbg.qkd_server.infrastructure.RouterManager.node.SimpleKMENode;
import com.xbg.qkd_server.infrastructure.RouterManager.node.SimpleSAENode;
import com.xbg.qkd_server.infrastructure.RouterManager.node.SecurityAbstractNode;
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

    private final Map<Host, KMENode> kmeNodeMap = new ConcurrentHashMap<>();

    private final Map<Host, SAENode> saeNodeMap = new ConcurrentHashMap<>();

    private final String currentKMEId;

    public StaticKmeRouterManager(String currentKMEId) {
        this.currentKMEId = currentKMEId;
    }

    public void initConfig(RouterConfig routerConfig) {
        List<KMERouterConfig> kmeList = Optional.ofNullable(routerConfig.getKmeList()).orElse(List.of());
        if (!configCheck(routerConfig)) {
            throw new KMEException(RouterErrorCode.ERROR_ROUTER_CONFIG);
        }
        for (var kme : kmeList) {
            SimpleKMENode kmeNode = new SimpleKMENode(kme.getKmeId(), kme.getIpAddr(), kme.getPort());
            kmeNodeMap.put(kmeNode.getHost(), kmeNode);
            for (var sae : kme.getSae()) {
                SimpleSAENode saeNode = new SimpleSAENode(sae.getSaeId(), sae.getIpAddr(), Optional.ofNullable(sae.getPort()).orElse(Host.DEFAULT_PORT));
                saeNodeMap.put(saeNode.getHost(), saeNode);
                saeNode.bindKMENode(kmeNode);
            }
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
            for (var sae : kme.getSae()) {
                String saeId = sae.getSaeId();
                String saeHost = sae.getIpAddr() + ":" + sae.getPort();
                if (nodeIdSet.contains(saeId)) {
                    log.error("config error, kme[{}]'s sae[{}] has duplicate id [{}] ", kmeIndex, saeIndex, saeId);
                    return false;
                }
                nodeIdSet.add(saeId);
                if (ipHost.contains(saeHost)) {
                    log.error("config error, kme[{}]'s sae[{}] has duplicate host [{}] ", kmeIndex, saeIndex, saeHost);
                    return false;
                }
                ipHost.add(saeHost);
                saeIndex++;
            }
            saeIndex = 0;
            kmeIndex ++;
        }
        if (!containCurrKMEIdFlag) {
            log.error("config error, missing current kme name [{}]", currentKMEId);
            return false;
        }
        return true;
    }

    /**
     * Host和Node是一对一映射，在这种模式下
     * 只能寻找到一个Node
     *
     * @param host
     * @return
     */
    private <T extends SecurityNode> Optional<T> findSaeByHost(Host host, Map<Host, T> nodeMap) {
        Optional<T> opt = Optional.empty();
        for (Host saeHosts : nodeMap.keySet()) {
            if (hostMatchCheck(saeHosts, host)) {
                opt = Optional.of(nodeMap.get(saeHosts));
                break;
            }
        }
        return opt;
    }

    /**
     * 匹配模式，允许寻找多个节点
     *
     * @param host
     * @return
     */
    private <T extends SecurityNode> List<T> matchAllSaeByHost(Host host, Map<Host, T> nodeMap) {
        List<T> list = new ArrayList<>();
        for (Host saeHosts : nodeMap.keySet()) {
            if (hostMatchCheck(saeHosts, host)) {
                list.add(nodeMap.get(saeHosts));
            }
        }
        return list;
    }

    private <T extends SecurityNode> Optional<T> findNodeByNodeId(String nodeId, Map<Host, T> nodeMap) {
        if (!StringUtils.hasLength(nodeId)) {
            return Optional.empty();
        }
        return nodeMap.values().stream()
                .filter(item -> nodeId.equals(item.nodeId()))
                .findAny();
    }

    /**
     * host匹配模式
     * 有 ip 无 port，按照ip匹配
     * 有 ip 有 port, 按照port匹配
     *
     * @param hostOfConfig
     * @param hostOfReq
     * @return
     */
    private boolean hostMatchCheck(Host hostOfConfig, Host hostOfReq) {
        if (!hostOfConfig.getIp().equals(hostOfReq.getIp())) {
            return false;
        }
        // 没有配置，或者配置的端口号为0
        if (Objects.isNull(hostOfConfig.getPort()) || hostOfConfig.getPort().equals(Host.DEFAULT_PORT)) {
            return true;
        }
        return hostOfConfig.getPort().equals(hostOfReq.getPort());
    }

    /**
     * 通过host查询所有匹配的节点
     *
     * @param host
     * @param nodeMap
     * @param <T>
     * @return
     */
    private <T extends SecurityNode> List<T> queryNodeByHost(Host host, Map<Host, T> nodeMap) {
        if (!IpUtils.isValidIPv4(host.getIp())) {
            log.error("invalid ip address: [{}]", host.getIp());
            throw new KMEException(RouterErrorCode.INVALID_IP_ADDRESS_FORMAT);
        }
        List<T> list = new ArrayList<>();
        // 无端口匹配模式
        if (Host.DEFAULT_PORT.equals(host.getPort())) {
            list.addAll(matchAllSaeByHost(host, nodeMap));
        } else {
            Optional<T> saeByHost = findSaeByHost(host, nodeMap);
            saeByHost.ifPresent(list::add);
        }
        return list;
    }

    @Override
    public String getCurrentSAEId() {
        String connectIP = IpUtils.getConnectIP();
        Integer connectPort = IpUtils.getConnectPort();
        if (!StringUtils.hasLength(connectIP)) {
            throw new KMEException(RouterErrorCode.UNABLE_GET_SAE_IP_PORT);
        }
        Optional<SAENode> saeByHost = findSaeByHost(new Host(connectIP, connectPort), saeNodeMap);
        if (saeByHost.isEmpty()) {
            return "";
        }
        return saeByHost.get().nodeId();
    }

    @Override
    public String getCurrentKME() {
        return currentKMEId;
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
    public List<SAENode> querySAENodeByHost(Host host) {
        return queryNodeByHost(host, saeNodeMap);
    }

    @Override
    public List<SAENode> querySAENodeByIpPort(String ipPort, Integer port) {
        return querySAENodeByHost(new Host(ipPort, port));
    }

    @Override
    public Optional<KMENode> queryKMEByKMEId(String kmeId) {
        return findNodeByNodeId(kmeId, kmeNodeMap);
    }

    @Override
    public List<KMENode> queryKMENodeByHost(Host host) {
        return matchAllSaeByHost(host, kmeNodeMap);
    }

    @Override
    public List<KMENode> queryKMENodeByIpPort(String ipPort, Integer port) {
        return queryKMENodeByHost(new Host(ipPort, port));
    }

}

