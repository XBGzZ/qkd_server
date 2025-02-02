package com.xbg.qkd_server.service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *      SAE注册中心
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:23
 */
public interface IRouterService {
    /**
     * 获取当前连接的SAE ID 信息
     * @return
     */
    String getCurrConnectSAEId();

    String getCurrConnectKMEId();

    String queryKMEbySAEId(String saeId);
}
