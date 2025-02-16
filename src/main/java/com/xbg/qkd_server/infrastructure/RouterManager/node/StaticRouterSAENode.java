package com.xbg.qkd_server.infrastructure.RouterManager.node;

import com.xbg.qkd_server.infrastructure.RouterManager.Host;
import com.xbg.qkd_server.infrastructure.RouterManager.KMENode;
import com.xbg.qkd_server.infrastructure.RouterManager.SAENode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

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
@Getter
@EqualsAndHashCode(callSuper = true)
public final class StaticRouterSAENode extends SecurityAbstractNode implements SAENode {

    private StaticRouterKMENode kmeNode = null;

    public StaticRouterSAENode(String nodeId) {
        super(nodeId, "0.0.0.0", 0);
    }

    public Boolean bindKMENode(StaticRouterKMENode kmeNode) {
        if (Objects.isNull(kmeNode)) {
            return false;
        }
        if (!kmeNode.regSAENode(this)) {
            return false;
        }
        this.kmeNode = kmeNode;
        return true;
    }

    public void updateNodeHost(Host host) {
        this.host = host;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SAE;
    }

    @Override
    public KMENode getKMENode() {
        return kmeNode;
    }

    @Override
    public void updateHost(Host host) {
        this.host = host;
    }
}
