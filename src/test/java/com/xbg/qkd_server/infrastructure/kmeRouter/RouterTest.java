package com.xbg.qkd_server.infrastructure.kmeRouter;

import com.xbg.qkd_server.common.tools.IpUtils;
import com.xbg.qkd_server.infrastructure.RouterManager.KmeRouterManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-30
 */

@SpringBootTest
public class RouterTest {

    @Autowired
    KmeRouterManager routerManager;

    @Test
    public void routerTest(){
        System.out.println(routerManager.querySAEBySAEId("SAE1"));
    }

    @Test
    public void routerTest2(){
        System.out.println(routerManager.queryKMEByKMEId("Alice"));
    }
}
