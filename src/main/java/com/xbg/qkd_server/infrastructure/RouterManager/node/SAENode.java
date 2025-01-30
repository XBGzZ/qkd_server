package com.xbg.qkd_server.infrastructure.RouterManager.node;

import com.xbg.qkd_server.infrastructure.RouterManager.config.SAERouterConfig;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
public final class SAENode extends SecurityNode {


    private KMENode kmeNode = null;

    public SAENode(String id, String ip, Integer port) {
        super(id, ip, port);
    }

    public SAENode(String id, String ip) {
        super(id, ip);
    }

    public Boolean bindKMENode(KMENode kmeNode) {
        if (Objects.isNull(kmeNode)) {
            return false;
        }
        if (!kmeNode.regSAENode(this)) {
            return false;
        }
        this.kmeNode = kmeNode;
        return true;
    }

    public KMENode getKmeNode() {
        return kmeNode;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SAE;
    }
}
