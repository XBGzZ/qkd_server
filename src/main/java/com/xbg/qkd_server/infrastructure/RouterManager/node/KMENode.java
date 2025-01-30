package com.xbg.qkd_server.infrastructure.RouterManager.node;

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
public final class KMENode extends SecurityNode {


    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final Set<SAENode> saeNodes = new HashSet<>();

    public KMENode(String id, String ip, Integer port) {
        super(id, ip, port);
    }

    public KMENode(String id, String ip) {
        super(id, ip);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.KME;
    }

    public Boolean regSAENode(SAENode node) {
        boolean add = true;
        lock.writeLock().lock();
        add = saeNodes.add(node);
        lock.writeLock().unlock();
        return add;
    }

    public Optional<SAENode> queryById(String saeId) {
        lock.readLock().lock();
        Optional<SAENode> findOne = saeNodes.stream().filter(node -> Objects.equals(node.getId(), saeId))
                .findAny();
        lock.readLock().unlock();
        return findOne;
    }

    public Optional<SAENode> queryByIp(String saeIp) {
        lock.readLock().lock();
        Optional<SAENode> findOne = saeNodes.stream().filter(node -> Objects.equals(node.getHost().getIp(), saeIp))
                .findAny();
        lock.readLock().unlock();
        return findOne;
    }

    public Optional<SAENode> queryByHost(String saeIp, Integer saePort) {
        lock.readLock().lock();
        Optional<SAENode> findOne = saeNodes.stream().filter(node -> Objects.equals(node.getHost(), new Host(saeIp, saePort)))
                .findAny();
        lock.readLock().unlock();
        return findOne;
    }

}
