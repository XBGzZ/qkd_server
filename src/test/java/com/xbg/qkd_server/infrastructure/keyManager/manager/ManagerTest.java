package com.xbg.qkd_server.infrastructure.keyManager.manager;

import com.xbg.qkd_server.common.enums.KeyErrorCode;
import com.xbg.qkd_server.common.errors.KeyException;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntity;
import com.xbg.qkd_server.infrastructure.keyManager.KeyEntityManager;
import com.xbg.qkd_server.infrastructure.keyManager.config.BaseKeyManagerConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 11:48
 */
@SpringBootTest
public class ManagerTest {
    @Autowired
    KeyEntityManager manager;

    @Autowired
    BaseKeyManagerConfig managerConfig;

    private void print(Collection<KeyEntity> keyEntities) {
        for (var item : keyEntities) {
            System.out.println(item);
        }
    }

    /**
     * 单线程下正常测试
     */
    @Test
    public void managerSingleThreadTest() {
        List<KeyEntity> keyEntities = manager.acquireKeys("xbg", 10, 1024);
        print(keyEntities);
    }


    /**
     * 单线程下，密钥参数异常测试
     */
    @Test
    public void singleThreadErrorTest_1() {
        List<KeyEntity> keyEntities = null;
        try {
            keyEntities = manager.acquireKeys("xbg", 10, 1023);
        } catch (KeyException e) {
            Assertions.assertEquals(KeyErrorCode.KEY_SIZE_INVALID, e.getErrorCode());
        }
        Assertions.assertNull(keyEntities);
    }

    @Test
    public void singleThreadErrorTest_2() {
        List<KeyEntity> keyEntities = null;
        try {
            keyEntities = manager.acquireKeys("xbg", 0, 1024);
        } catch (KeyException e) {
            Assertions.assertEquals(e.getErrorCode(), KeyErrorCode.KEY_COUNT_IS_NO_MORE_THAN_ZERO);
        }
        Assertions.assertNull(keyEntities);
    }

    @Test
    public void singleThreadErrorTest_3() {
        List<KeyEntity> keyEntities = null;
        try {
            keyEntities = manager.acquireKeys("", 1, 1024);
        } catch (KeyException e) {
            Assertions.assertEquals(e.getErrorCode(), KeyErrorCode.KEY_SAE_ID_IS_INVALID);
        }
        Assertions.assertNull(keyEntities);
    }

    @Test
    public void singleThreadErrorTest_4() {
        List<KeyEntity> keyEntities = null;
        try {
            keyEntities = manager.acquireKeys("xbg", managerConfig.getMaxKeyPerRequest() + 1, 1024);
        } catch (KeyException e) {
            Assertions.assertEquals(e.getErrorCode(), KeyErrorCode.KEY_OVER_PER_REQUEST_LIMIT);
        }
        Assertions.assertNull(keyEntities);
    }


    /**
     * 多线程测试超发测试
     */
    @Test
    public void multiThreadTest() {
        // 最大SAE数量
        Integer maxSaeIdCount = managerConfig.getMaxSaeIdCount();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(maxSaeIdCount, maxSaeIdCount, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        final int REQUIRE_TIMES = 11;
        final int PER_REQUEST_KEY_COUNT = 100;
        final int PER_REQUEST_KEY_SIZE = 1024;
        CountDownLatch countDownLatch = new CountDownLatch(REQUIRE_TIMES);
        Runnable acquireKeyTask = () -> {
            String tName = Thread.currentThread().getName();
            String owner = "Owner-" + tName;
            try {
                List<KeyEntity> keyEntities = manager.acquireKeys(owner, PER_REQUEST_KEY_COUNT, PER_REQUEST_KEY_SIZE);
                System.out.println(owner + ":" + keyEntities.size());
                Assertions.assertEquals(keyEntities.size(), PER_REQUEST_KEY_COUNT);
                for (var item : keyEntities) {
                    Assertions.assertEquals(item.getKeySize(), PER_REQUEST_KEY_SIZE);
                    Assertions.assertEquals(item.getOwner(), owner);
                }
            } catch (KeyException e) {
                System.out.println(e);
                Assertions.assertEquals(e.getErrorCode(), KeyErrorCode.KEY_PRE_ALLOCATE_FAILED);
            } finally {
                countDownLatch.countDown();
            }
        };
        for (int i = 0; i < REQUIRE_TIMES; i++) {
            threadPool.submit(acquireKeyTask);
        }
        try {
            // 等待任务全部执行完毕
            countDownLatch.await();
            Assertions.assertEquals(manager.managerState().getStoredKeyCount(), managerConfig.getMaxKeyCount());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
