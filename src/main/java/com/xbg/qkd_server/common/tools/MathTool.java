package com.xbg.qkd_server.common.tools;

/**
 * @Author XBG
 * @Description: 数学工具
 * @Date 2025-01-12
 */

public class MathTool {
    /**
     * 计算以n为底的对数
     * @param x
     * @param n
     * @return
     */
    public static Double logN(double x, double n) {
        return Math.log(x) / Math.log(n);
    }

    /**
     * 计算以2为底的对数
     * @param x
     * @return
     */
    public static Double log2(double x) {
        return logN(x, 2);
    }
}
