package com.xbg.qkd_server.interceptor;

import com.xbg.qkd_server.common.tools.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:28
 */
@Component
public class IpInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = IpUtils.getIpAddr(request);
        Integer port = IpUtils.getPort(request);
        IpUtils.setConnectIP(ipAddr);
        IpUtils.setConnectPort(port);
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        System.out.println(certs);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IpUtils.cleanConnectIP();
        IpUtils.cleanConnectPort();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
