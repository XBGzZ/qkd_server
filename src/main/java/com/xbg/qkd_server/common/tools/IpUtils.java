package com.xbg.qkd_server.common.tools;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.Enumeration;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:20
 */

@Slf4j
public class IpUtils {
    private static final String LOCAL_IP = "127.0.0.1";

    private static final ThreadLocal<String> currentIp = new ThreadLocal<>();
    private static final ThreadLocal<Integer> currentPort = new ThreadLocal<>();

    public static void setConnectIP(String ip) {
        currentIp.set(ip);
    }

    public static String getConnectIP() {
        return currentIp.get();
    }

    public static void cleanConnectIP() {
        currentIp.remove();
    }

    public static void setConnectPort(Integer port) {
        currentPort.set(port);
    }

    public static Integer getConnectPort() {
        return Optional.ofNullable(currentPort.get()).orElse(0);
    }

    public static void cleanConnectPort() {
        currentPort.remove();
    }

    private static final String IPV4_PATTERN =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

    // ip地址判断
    public static boolean isValidIPv4(String ip) {
        if (ip == null) {
            return false;
        }
        return pattern.matcher(ip).matches();
    }
    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_IP : ip;
    }

    public static Integer getPort(HttpServletRequest request) {
        return request.getRemotePort();
    }


    public static boolean internalIp(String ip) {
        boolean res = false;
        byte[] addr = textToNumericFormatV4(ip);
        if (addr != null && ip != null) {
            res = internalIp(addr) || LOCAL_IP.equals(ip);
        }
        return res;
    }

    private static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        boolean flag = false;
        switch (b0) {
            case SECTION_1:
                flag = true;
                break;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    flag = true;
                }
                break;
            case SECTION_5:
                if (b1 == SECTION_6) {
                    flag = true;
                }
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     * 将IPv4地址转换成字节
     * IPv4地址
     *
     * @param text
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L))
                        return null;
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L))
                        return null;
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L))
                        return null;
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return null;
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L))
                        return null;
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return null;
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            log.error("数字格式化异常", e);
            return null;
        }
        return bytes;
    }

    public static String getLocalIP() {
        String ip = "";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
                ip = addr.getHostAddress();
            } catch (UnknownHostException e) {
                log.error("获取失败", e);
            }
            return ip;
        } else {
            try {
                Enumeration<?> e1 = (Enumeration<?>) NetworkInterface
                        .getNetworkInterfaces();
                while (e1.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) e1.nextElement();
                    if (!ni.getName().equals("eth0")) {
                        continue;
                    } else {
                        Enumeration<?> e2 = ni.getInetAddresses();
                        while (e2.hasMoreElements()) {
                            InetAddress ia = (InetAddress) e2.nextElement();
                            if (ia instanceof Inet6Address)
                                continue;
                            ip = ia.getHostAddress();
                            return ip;
                        }
                        break;
                    }
                }
            } catch (SocketException e) {
                log.error("获取失败", e);
            }
        }
        return "";
    }



}
