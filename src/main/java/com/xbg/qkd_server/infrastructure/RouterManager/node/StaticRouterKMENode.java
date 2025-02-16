package com.xbg.qkd_server.infrastructure.RouterManager.node;

import com.xbg.qkd_server.common.enums.CommonErrorCode;
import com.xbg.qkd_server.common.enums.ErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.infrastructure.RouterManager.Host;
import com.xbg.qkd_server.infrastructure.RouterManager.KMENode;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 23:17
 */
@EqualsAndHashCode(callSuper = true)
public final class StaticRouterKMENode extends SecurityAbstractNode implements KMENode {


    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final Set<StaticRouterSAENode> saeNodes = new HashSet<>();

    public StaticRouterKMENode(String id, String ip, Integer port) {
        super(id, ip, port);
    }

    public StaticRouterKMENode(String id, String ip) {
        super(id, ip);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.KME;
    }

    public Boolean regSAENode(StaticRouterSAENode node) {
        boolean add = true;
        lock.writeLock().lock();
        add = saeNodes.add(node);
        lock.writeLock().unlock();
        return add;
    }

    public Optional<StaticRouterSAENode> queryById(String saeId) {
        lock.readLock().lock();
        Optional<StaticRouterSAENode> findOne = saeNodes.stream().filter(node -> Objects.equals(node.getId(), saeId))
                .findAny();
        lock.readLock().unlock();
        return findOne;
    }

    public Optional<StaticRouterSAENode> queryByIp(String saeIp) {
        lock.readLock().lock();
        Optional<StaticRouterSAENode> findOne = saeNodes.stream().filter(node -> Objects.equals(node.getHost().getIp(), saeIp))
                .findAny();
        lock.readLock().unlock();
        return findOne;
    }

    public Optional<StaticRouterSAENode> queryByHost(String saeIp, Integer saePort) {
        lock.readLock().lock();
        Optional<StaticRouterSAENode> findOne = saeNodes.stream().filter(node -> Objects.equals(node.getHost(), new Host(saeIp, saePort)))
                .findAny();
        lock.readLock().unlock();
        return findOne;
    }

    @Override
    public void updateHost(Host host) {
        throw new KMEException(CommonErrorCode.NOT_SUPPORT);
    }
}
