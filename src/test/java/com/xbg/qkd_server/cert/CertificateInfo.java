package com.xbg.qkd_server.cert;

import lombok.Data;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.OutputEncryptor;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author XBG
 * @Description:
 * @Date 2024-12-21
 */

@Data
public class CertificateInfo {
 /**
  * 证书序列号
  */
 private BigInteger serial;
 /**
  * 颁发者
  */
 private X500Name issuer;
 /**
  * 主体
  */
 private X500Name subject;
 /**
  * 颁发时间
  */
 private Date notBefore;
 /**
  * 到期时间
  */
 private Date notAfter;
 /**
  * 加密算法
  */
 private String keyAlgorithm;
 /**
  * 签名算法
  */
 private String signAlgorithm;

}
