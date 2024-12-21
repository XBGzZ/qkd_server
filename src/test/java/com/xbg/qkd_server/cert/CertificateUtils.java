package com.xbg.qkd_server.cert;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.PemUtil;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.PEMUtil;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Objects;

import static com.xbg.qkd_server.cert.X509CertificateConstants.*;

/**
 * @Author XBG
 * @Description:
 * @Date 2024-12-21
 */

public class CertificateUtils {
    /**
     * 生成密钥对及证书
     * @param certificateInfo 证书信息
     * @param caPrivateKey 根证书私钥，用于给证书签名
     * @return
     * @throws Throwable
     */
    public static KeyAndCertificate generateCertificate(CertificateInfo certificateInfo, PrivateKey caPrivateKey) throws Throwable {
        // 生成证书所需密钥对，RSA 算法，密钥长度 2048 字节
        KeyPair keyPair = KeyUtil.generateKeyPair(certificateInfo.getKeyAlgorithm(), 2048);

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        //证书颁发者信息
        X500Name issuer = certificateInfo.getIssuer();
        //证书主题信息
        X500Name subject = certificateInfo.getSubject();
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        X509v3CertificateBuilder x509v3CertificateBuilder = new X509v3CertificateBuilder(
                issuer,
                certificateInfo.getSerial(),
                certificateInfo.getNotBefore(),
                certificateInfo.getNotAfter(),
                subject,
                subjectPublicKeyInfo
        );
        JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(certificateInfo.getSignAlgorithm());
        //如果没有根证书私钥，自己给自己签名，在生成根证书时会有这种情况
        if(Objects.isNull(caPrivateKey)) {
            caPrivateKey = privateKey;
        }
        //签名者
        ContentSigner contentSigner = jcaContentSignerBuilder.build(caPrivateKey);
        //生成证书
        X509CertificateHolder x509CertificateHolder = x509v3CertificateBuilder.build(contentSigner);
        //X509CertificateHolder 转 X509Certificate
        JcaX509CertificateConverter jcaX509CertificateConverter = new JcaX509CertificateConverter();

        //转换成 X509Certificate 证书对象
        X509Certificate x509Certificate = jcaX509CertificateConverter.getCertificate(x509CertificateHolder);
        return new KeyAndCertificate(privateKey, publicKey, x509Certificate);
    }

    /**
     * 保存私钥到磁盘
     * @param privateKey
     * @param filePath
     * @throws Throwable
     */
    public static void save(PrivateKey privateKey, String filePath,String passwd) throws Throwable {
        if(passwd.isEmpty()){
            save(privateKey.getEncoded(), PRIVATE_KEY_TYPE, filePath);
        } else {
            save(encryptPrivateKeyWithDES(privateKey,passwd), PRIVATE_KEY_TYPE, filePath);
        }

    }
    public static byte[] encryptPrivateKeyWithDES(PrivateKey privateKey, String passwd) throws Exception {
        // 生成 DES 密钥
        DESKeySpec desKeySpec = new DESKeySpec(passwd.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 使用 DES 加密私钥
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(privateKey.getEncoded());
    }
    /**
     * 保存公钥到磁盘
     * @param publicKey
     * @param filePath
     * @throws Throwable
     */
    public static void save(PublicKey publicKey, String filePath) throws Throwable {
        save(publicKey.getEncoded(), PUBLIC_KEY_TYPE, filePath);
    }

    /**
     * 保存证书到磁盘
     * @param certificate
     * @param filePath
     * @throws Throwable
     */
    public static void save(Certificate certificate, String filePath) throws Throwable {
        save(certificate.getEncoded(), CERTIFICATE_TYPE, filePath);
    }

    /**
     * 以 PEM 格式保存密钥、证书到磁盘
     * @param encodedBytes
     * @param type
     * @param filePath
     * @throws Throwable
     */
    public static void save(byte[]  encodedBytes, String type, String filePath) throws Throwable {
        FileUtil.mkParentDirs(filePath);
        PemObject pemObject = new PemObject(type, encodedBytes);
        try(FileWriter fileWriter = new FileWriter(filePath); PemWriter pemWriter = new PemWriter(fileWriter)) {
            pemWriter.writeObject(pemObject);

        }
    }
}