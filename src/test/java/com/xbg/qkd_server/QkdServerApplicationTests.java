package com.xbg.qkd_server;

import com.xbg.qkd_server.common.enums.GenerateCode;
import com.xbg.qkd_server.common.enums.KmeServiceCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QkdServerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(KmeServiceCode.KEY_ERROR.IsSuccess());
        System.out.println(GenerateCode.SUCCESS.IsSuccess());
    }

}
