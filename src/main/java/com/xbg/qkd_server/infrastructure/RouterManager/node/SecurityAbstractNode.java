package com.xbg.qkd_server.infrastructure.RouterManager.node;

import com.xbg.qkd_server.common.enums.RouterErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.common.tools.IpUtils;
import com.xbg.qkd_server.infrastructure.RouterManager.Host;
import com.xbg.qkd_server.infrastructure.RouterManager.SecurityNode;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.xbg.qkd_server.infrastructure.RouterManager.Host.DEFAULT_IPADDR;
import static com.xbg.qkd_server.infrastructure.RouterManager.Host.DEFAULT_PORT;

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
public abstract class SecurityAbstractNode implements SecurityNode {

    String id;

    Host host;

    public SecurityAbstractNode(String id, String ip, Integer port) {
        host = new Host(ip,port);
        this.id = id;
    }

    public SecurityAbstractNode(String id, String ip) {
        host = new Host(ip,null);
        this.id = id;
    }

    public static enum NodeType {
        KME,SAE
    }



    public abstract NodeType getNodeType();

    @Override
    public String nodeId() {
        return id;
    }

    @Override
    public Integer port() {
        if (Objects.isNull(host)) {
            return DEFAULT_PORT;
        }
        if (Objects.isNull(host.getPort())) {
            return DEFAULT_PORT;
        }
        return host.getPort();
    }

    @Override
    public String ipAddr() {
        if (Objects.isNull(host)) {
            return DEFAULT_IPADDR;
        }
        if (StringUtils.hasLength(host.getIp())) {
            return DEFAULT_IPADDR;
        }
        return host.getIp();
    }

    @Override
    public String hostName() {
        return ipAddr() + ":" + port();
    }
}
