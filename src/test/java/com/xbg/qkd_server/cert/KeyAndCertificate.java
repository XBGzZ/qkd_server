package com.xbg.qkd_server.cert;

import lombok.Data;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * @Author XBG
 * @Description:
 * @Date 2024-12-21
 */

@Data
public class KeyAndCertificate {
    /**
     * 私钥
     */
    private PrivateKey privateKey;
    /**
     * 公钥
     */
    private PublicKey publicKey;
    /**
     * 证书
     */
    private X509Certificate certificate;

    public KeyAndCertificate(PrivateKey privateKey, PublicKey publicKey, X509Certificate certificate) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.certificate = certificate;
    }
}