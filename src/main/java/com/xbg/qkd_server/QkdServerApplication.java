package com.xbg.qkd_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class QkdServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QkdServerApplication.class, args);
    }

}
