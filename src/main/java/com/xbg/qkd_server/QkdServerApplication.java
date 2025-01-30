package com.xbg.qkd_server;

import com.xbg.qkd_server.config.KeyManagerConfig;
import com.xbg.qkd_server.infrastructure.keyManager.config.MPOKeyManagerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class QkdServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QkdServerApplication.class, args);
    }

}
