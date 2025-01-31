package com.xbg.qkd_server.infrastructure.RouterManager;

import com.xbg.qkd_server.common.enums.RouterErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.common.tools.IpUtils;
import lombok.*;

import java.util.Objects;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-31
 */

@Data
public class Host {
    public static final Integer DEFAULT_PORT = 0;
    public static final String DEFAULT_IPADDR = "0.0.0.0";

    public Host(String ip, Integer port) {
        if (!IpUtils.isValidIPv4(ip) || Objects.isNull(port) || port < 0){
            throw new KMEException(RouterErrorCode.ERROR_ROUTER_CONFIG);
        }
        this.ip = ip;
        this.port = port;
    }

    String ip;
    Integer port = DEFAULT_PORT;
}
