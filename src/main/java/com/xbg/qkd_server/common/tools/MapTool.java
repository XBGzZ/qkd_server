package com.xbg.qkd_server.common.tools;

/**
 * @Author XBG
 * @Description: Map工具，和Map分配相关的操作
 * @Date 2025-01-12
 */

public class MapTool {
    // 负载因子倒数
    private static final double ReciprocalLoadFactor = 1.33f;
    // 负载因子
    private static final double LoadFactor = 0.75f;

    /**
     * 寻找最佳分割点函数，用于节省HashMap无效空间的开辟节省内存空间
     * 空间利用率会小于等于75%
     * 1、在刚好等于75%的情况下，不需要分割
     * 2、在小于75%的情况下需要进行分割
     * <p>
     * 如果分配的空间过小，也不需要分割，分割Map后会带来性能损耗
     * 理论上，性能的损耗程度和分割的Map数量成正比
     * <p>
     * 通过数学公式推导而来
     *
     * @param size 当前元素总数个数
     * @return 满足75%利用率的最佳分割点
     */
    public static Integer findBeastSegmentationPoint(Integer size) {
        return (int) (Math.pow(2, Math.floor(MathTool.log2(size * ReciprocalLoadFactor))) * LoadFactor);
    }

    /**
     * 负载因子存在变动是，Map最佳分割点
     * @param size
     * @param loadFactor
     * @return
     */
    public static Integer findBeastSegmentationPoint(Integer size, Double loadFactor) {
        if (loadFactor < 0 || loadFactor > 1) {
            return 0;
        }
        return (int) (Math.pow(2, Math.floor(MathTool.log2(size / loadFactor))) * loadFactor);
    }
}
