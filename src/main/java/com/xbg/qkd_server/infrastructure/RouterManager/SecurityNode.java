package com.xbg.qkd_server.infrastructure.RouterManager;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-30
 */

public interface SecurityNode {
    String nodeId();

    Integer port();

    String ipAddr();

    String hostName();

    void updateHost(Host host);
}
