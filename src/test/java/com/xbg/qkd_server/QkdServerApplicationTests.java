package com.xbg.qkd_server;

import com.xbg.qkd_server.common.enums.GenerateCode;
import com.xbg.qkd_server.common.enums.KmeServiceCode;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

@SpringBootTest
class QkdServerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(KmeServiceCode.KEY_ERROR.IsSuccess());
        System.out.println(GenerateCode.SUCCESS.IsSuccess());
    }

}