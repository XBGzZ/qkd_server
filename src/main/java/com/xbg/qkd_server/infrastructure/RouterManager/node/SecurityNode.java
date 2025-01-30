package com.xbg.qkd_server.infrastructure.RouterManager.node;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  安全节点
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 23:16
 */
@Data
public abstract class SecurityNode {

    String id;

    Host host;

    public SecurityNode(String id,String ip,Integer port) {
        host = new Host(ip,port);
        this.id = id;
    }

    public SecurityNode(String id,String ip) {
        host = new Host(ip,null);
        this.id = id;
    }

    public static enum NodeType {
        KME,SAE
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Host {
        public static final int DEFAULT_PORT = 0;

        String ip;
        Integer port = DEFAULT_PORT;
    }

    public abstract NodeType getNodeType();
}
