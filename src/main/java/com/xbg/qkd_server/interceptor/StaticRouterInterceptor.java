package com.xbg.qkd_server.interceptor;

import com.xbg.qkd_server.common.enums.CertErrorCode;
import com.xbg.qkd_server.common.enums.RouterErrorCode;
import com.xbg.qkd_server.common.errors.KMEException;
import com.xbg.qkd_server.common.tools.AuthUtils;
import com.xbg.qkd_server.common.tools.IpUtils;
import com.xbg.qkd_server.infrastructure.RouterManager.Host;
import com.xbg.qkd_server.infrastructure.RouterManager.KmeRouterManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  证书信息拦截模块
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:28
 */
@Component
@Slf4j

public class StaticRouterInterceptor implements HandlerInterceptor {

    static final String OLD_CERT_ATTR = "javax.servlet.request.X509Certificate";
    static final String NEW_CERT_ATTR = "jakarta.servlet.request.X509Certificate";

    @Autowired
    KmeRouterManager routerManager;

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
        log.info("client's tls cert common name:[{}]", subjectCommonName);
        AuthUtils.recordCommonName(subjectCommonName);
        Boolean isSuccess = routerManager.updateSAEHostInfo(subjectCommonName, new Host(ipAddr, port));
        if (!isSuccess) {
            log.warn("can't find sae id in config:[{}]", subjectCommonName);
            throw new KMEException(RouterErrorCode.CAN_NOT_FIND_SAE_NODE);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IpUtils.cleanConnectIP();
        IpUtils.cleanConnectPort();
        AuthUtils.cleanCommonName();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private Optional<X509Certificate[]> requestX509Certificate(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute(NEW_CERT_ATTR);
        if (certs != null) {
            return Optional.of(certs);
        }
        certs = (X509Certificate[]) request.getAttribute(OLD_CERT_ATTR);
        return Optional.ofNullable(certs);
    }

    private String getSubjectCommonName(X509Certificate cert) {
        String name = cert.getSubjectX500Principal().getName();
        String[] keyPair = name.split(",");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (String s : keyPair) {
            int splitIndex = s.indexOf("=");
            var key = s.substring(0, splitIndex);
            var value = s.substring(splitIndex + 1);
            stringStringHashMap.put(key, value);
        }
        return stringStringHashMap.get("CN");
    }
}
