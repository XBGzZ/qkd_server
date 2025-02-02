package com.xbg.qkd_server.infrastructure.RouterManager.node;

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
public final class SimpleSAENode extends SecurityAbstractNode implements SAENode {

    private SimpleKMENode kmeNode = null;

    public SimpleSAENode(String id, String ip, Integer port) {
        super(id, ip, port);
    }

    public SimpleSAENode(String id, String ip) {
        super(id, ip);
    }

    public Boolean bindKMENode(SimpleKMENode kmeNode) {
        if (Objects.isNull(kmeNode)) {
            return false;
        }
        if (!kmeNode.regSAENode(this)) {
            return false;
        }
        this.kmeNode = kmeNode;
        return true;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SAE;
    }

    @Override
    public KMENode getKMENode() {
        return kmeNode;
    }
}
