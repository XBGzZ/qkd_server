package com.xbg.qkd_server.common.tools;

/**
 * @author XBG
 * @description: TODO
 * @date 2025/2/16 20:46
 */
public class AuthUtils {
    private static final ThreadLocal<String> currentCommonName = new ThreadLocal<>();

    public static void recordCommonName(String cn) {
        currentCommonName.set(cn);
    }

    public static String getCommonName() {
        return currentCommonName.get();
    }

    public static void cleanCommonName() {
         currentCommonName.remove();
    }
}
