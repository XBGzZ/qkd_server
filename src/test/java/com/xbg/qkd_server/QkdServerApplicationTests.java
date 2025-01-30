package com.xbg.qkd_server;

import com.xbg.qkd_server.common.enums.CommonCode;
import com.xbg.qkd_server.common.enums.KeyErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QkdServerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(KeyErrorCode.KEY_ERROR.IsSuccess());
        System.out.println(CommonCode.SUCCESS.IsSuccess());
    }

}