package com.xbg.qkd_server.server;

import com.xbg.qkd_server.common.tools.MapTool;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-01-12
 */

@SpringBootTest
public class LocalKeyTest {
    /**
     * 利用率分配函数
     * JDK17之后，需要添加VM参数
     * --add-opens
     * java.base/java.util=ALL-UNNAMED
     * --add-opens
     * java.base/java.lang.reflect=ALL-UNNAMED
     * --add-opens
     * java.base/java.text=ALL-UNNAMED
     * <p>
     * 通过空间分配，理论上分配为两个Map，利用率最低情况为 50% 的利用率
     */
    @Test
    @SneakyThrows
    void mapSizeTest() {
        int y = 230400;
        Integer x = MapTool.findBeastSegmentationPoint(y);

        //获取HashMap整个类
        Class<?> mapType = HashMap.class;
        Field threshold = mapType.getDeclaredField("threshold");
        //将目标属性设置为可以访问
        threshold.setAccessible(true);
        //获取指定方法，因为HashMap没有容量这个属性，但是capacity方法会返回容量值
        Method capacity = mapType.getDeclaredMethod("capacity");
        //设置目标方法为可访问
        capacity.setAccessible(true);

        // ================= 拆分利用率计算 =================
        // Map1
        HashMap<Integer, Boolean> map1 = new HashMap<>();
        for (Integer i = 0; i < x; i++) {
            map1.put(i, true);
        }
        // Map2
        HashMap<Integer, Boolean> map2 = new HashMap<>();
        for (int i = 0; i < y - x; i++) {
            map2.put(i, true);
        }
        //打印刚初始化的HashMap的容量、阈值和元素数量
        System.out.println("容量：" + capacity.invoke(map1) + " 阈值：" + threshold.get(map1) + " 元素数量：" + map1.size());        //获取HashMap整个类
        //打印刚初始化的HashMap的容量、阈值和元素数量
        System.out.println("容量：" + capacity.invoke(map2) + " 阈值：" + threshold.get(map2) + " 元素数量：" + map2.size());

        int v1 = (int) (capacity.invoke(map2));
        int v2 = (int) (capacity.invoke(map1));
        System.out.println("拆分后利用率: " + String.format("%.2f", (y * 1.0 / (v1 + v2)) * 100) + "%");

        // 释放
        map1.clear();
        map2.clear();

        // ================= 不拆分利用率计算 =================
        HashMap<Integer, Boolean> normalMap = new HashMap<>();
        for (int i = 0; i < y; i++) {
            normalMap.put(i, true);
        }
        System.out.println("容量：" + capacity.invoke(normalMap) + " 阈值：" + threshold.get(normalMap) + " 元素数量：" + normalMap.size());
        int v = (int) (capacity.invoke(normalMap));
        System.out.println("未拆分利用率: " + String.format("%.2f", (y * 1.0 / v) * 100) + "%");
    }

    /**
     * 拆分三次的利用率对比
     */
    @Test
    @SneakyThrows
    void mapSizeTest2() {
        int y = 4096;
        Integer x = MapTool.findBeastSegmentationPoint(y);

        //获取HashMap整个类
        Class<?> mapType = HashMap.class;
        Field threshold = mapType.getDeclaredField("threshold");
        //将目标属性设置为可以访问
        threshold.setAccessible(true);
        //获取指定方法，因为HashMap没有容量这个属性，但是capacity方法会返回容量值
        Method capacity = mapType.getDeclaredMethod("capacity");
        //设置目标方法为可访问
        capacity.setAccessible(true);

        // ================= 拆分利用率计算 =================
        // Map1
        HashMap<Integer, Boolean> map1 = new HashMap<>();
        for (Integer i = 0; i < x; i++) {
            map1.put(i, true);
        }
        int x1 = MapTool.findBeastSegmentationPoint(y - x);
        // Map2
        HashMap<Integer, Boolean> map2 = new HashMap<>();
        for (int i = 0; i < x1; i++) {
            map2.put(i, true);
        }
        // Map3
        HashMap<Integer, Boolean> map3 = new HashMap<>();
        for (int i = 0; i < y - x - x1; i++) {
            map3.put(i, true);
        }
        //打印刚初始化的HashMap的容量、阈值和元素数量
        System.out.println("容量：" + capacity.invoke(map1) + " 阈值：" + threshold.get(map1) + " 元素数量：" + map1.size());        //获取HashMap整个类
        //打印刚初始化的HashMap的容量、阈值和元素数量
        System.out.println("容量：" + capacity.invoke(map2) + " 阈值：" + threshold.get(map2) + " 元素数量：" + map2.size());
        System.out.println("容量：" + capacity.invoke(map3) + " 阈值：" + threshold.get(map3) + " 元素数量：" + map3.size());
        int v1 = (int) (capacity.invoke(map1));
        int v2 = (int) (capacity.invoke(map2));
        int v3 = (int) (capacity.invoke(map3));
        System.out.println("拆分后利用率: " + String.format("%.2f", (y * 1.0 / (v1 + v2 + v3)) * 100) + "%");

        // 释放
        map1.clear();
        map3.clear();

        // ================= 不拆分利用率计算 =================
        HashMap<Integer, Boolean> normalMap = new HashMap<>();
        for (int i = 0; i < y; i++) {
            normalMap.put(i, true);
        }
        System.out.println("容量：" + capacity.invoke(normalMap) + " 阈值：" + threshold.get(normalMap) + " 元素数量：" + normalMap.size());
        int v = (int) (capacity.invoke(normalMap));
        System.out.println("未拆分利用率: " + String.format("%.2f", (y * 1.0 / v) * 100) + "%");
    }
}
