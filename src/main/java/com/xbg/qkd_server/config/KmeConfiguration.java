package com.xbg.qkd_server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "kme")
@Data
public class KmeConfiguration {
    // 默认值dft
    @Value("${kme.saeIdd:dft}")
    String saeId;

    // ca证书路径
    String caCertPath;

    // local证书
    String localCertPath;
}
