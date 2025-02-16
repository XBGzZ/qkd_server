package com.xbg.qkd_server.interceptor;

import com.xbg.qkd_server.common.enums.CertErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.common.tools.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Optional;

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
@Slf4j
public class IpInterceptor implements HandlerInterceptor {

    static final String OLD_CERT_ATTR = "javax.servlet.request.X509Certificate";
    static final String NEW_CERT_ATTR = "jakarta.servlet.request.X509Certificate";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = IpUtils.getIpAddr(request);
        Integer port = IpUtils.getPort(request);
        IpUtils.setConnectIP(ipAddr);
        IpUtils.setConnectPort(port);
        Optional<X509Certificate[]> certs = requestX509Certificate(request);
        if (certs.isEmpty()) {
            throw new KMEException(CertErrorCode.EMPTY_CERT);
        }
        String subjectCommonName = getSubjectCommonName(certs.get()[0]);
        log.info("client connect cert common name:[{}]", subjectCommonName);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IpUtils.cleanConnectIP();
        IpUtils.cleanConnectPort();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private Optional<X509Certificate[]> requestX509Certificate(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
        if (certs != null) {
            return Optional.ofNullable(certs);
        }
        certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        return Optional.ofNullable(certs);
    }

    private String getSubjectCommonName(X509Certificate cert) {
        String name = cert.getSubjectX500Principal().getName();
        String[] keyPair = name.split(",");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (int i = 0; i < keyPair.length; i++) {
            int splitIndex = keyPair[i].indexOf("=");
            var key = keyPair[i].substring(0, splitIndex);
            var value = keyPair[i].substring(splitIndex + 1);
            stringStringHashMap.put(key, value);
        }
        return stringStringHashMap.get("CN");
    }
}
