package com.xbg.qkd_server.cert;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.PemUtil;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.openssl.PEMEncryptor;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcePEMEncryptorBuilder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import static com.xbg.qkd_server.cert.X509CertificateConstants.KEY_ALGORITHM;
import static com.xbg.qkd_server.cert.X509CertificateConstants.SIGN_ALGORITHM;

/**
 * @Author XBG
 * @Description:
 * @Date 2024-12-21
 */

public class X509CertificateDemo {

    /**
     * 自签名根证书私钥保存路径
     */
    private static final String CA_PRIVATE_KEY_FILE_PATH = StrUtil.format("{}/x509-certificate-demo/ca/ca-private.key", System.getProperty("user.dir"));
    /**
     * 自签名根证书公钥保存路径
     */
    private static final String CA_PUBLIC_KEY_FILE_PATH = StrUtil.format("{}/x509-certificate-demo/ca/ca-public.key", System.getProperty("user.dir"));
    /**
     * 自签名根证书保存路径
     */
    private static final String CA_CERTIFICATE_FILE_PATH = StrUtil.format("{}/x509-certificate-demo/ca/ca.cer", System.getProperty("user.dir"));
    /**
     * 自签名证书私钥保存路径
     */
    private static final String SELF_SIGNED_PRIVATE_KEY_FILE_PATH = StrUtil.format("{}/x509-certificate-demo/self/self-private.key", System.getProperty("user.dir"));
    /**
     * 自签名证书公钥保存路径
     */
    private static final String SELF_SIGNED_PUBLIC_KEY_FILE_PATH = StrUtil.format("{}/x509-certificate-demo/self/self-public.key", System.getProperty("user.dir"));
    /**
     * 自签名证书保存路径
     */
    private static final String SELF_SIGNED_CERTIFICATE_FILE_PATH = StrUtil.format("{}/x509-certificate-demo/self/self.cer", System.getProperty("user.dir"));
    /**
     * pkcs12 格式证书保存路径
     */
    private static final String SELF_SIGNED_PKCS12_FILE_PATH = StrUtil.format("{}/x509-certificate-demo/self/self.p12", System.getProperty("user.dir"));
    /**
     * pkcs12 格式证书密码
     */
    private static final String SELF_SIGNED_PKCS12_CERTIFICATE_PASSWORD = "KtYBnbVEotFvYEFm";

    public static void main(String[] args) throws Throwable {
        // 生成ca证书
        generateCaCertificate();
        //
        generateSelfSignedCertificate();
        generatePkcs12Store();
    }

    /**
     * 生成根证书及其密钥对
     * @throws Throwable
     */
    public static void generateCaCertificate() throws Throwable {
        //根证书主题信息
        X500Name subject = new SubjectBuilder()
                .setCn("My CA common name")
                .setO("My CA organization")
                .setOu("My CA organizational unit name")
                .setC("My CA country code")
                .setSt("My CA state, or province name")
                .setL("My CA locality name")
                .build();

        //根证书颁发者信息，即自身
        X500Name issuer = subject;

        //根证书有效期
        DateTime notBefore = DateUtil.yesterday();
        DateTime notAfter = DateUtil.offset(notBefore, DateField.YEAR, 10);

        CertificateInfo certificateInfo = new CertificateInfo();
        certificateInfo.setSerial(BigInteger.valueOf(RandomUtil.randomLong(1L, Long.MAX_VALUE)));
        certificateInfo.setIssuer(issuer);
        certificateInfo.setSubject(subject);
        certificateInfo.setNotBefore(notBefore);
        certificateInfo.setNotAfter(notAfter);
        certificateInfo.setKeyAlgorithm(KEY_ALGORITHM);
        certificateInfo.setSignAlgorithm(SIGN_ALGORITHM);

        //生成根证书及其密钥对
        KeyAndCertificate keyAndCertificate = CertificateUtils.generateCertificate(certificateInfo, null);
        //保存根证书及其密钥对到磁盘
        CertificateUtils.save(keyAndCertificate.getPrivateKey(), CA_PRIVATE_KEY_FILE_PATH,"");
        CertificateUtils.save(keyAndCertificate.getPublicKey(), CA_PUBLIC_KEY_FILE_PATH);
        CertificateUtils.save(keyAndCertificate.getCertificate(), CA_CERTIFICATE_FILE_PATH);
    }

    public static String toPem(String privateKey, String password) throws IOException {

        StringWriter sw = new StringWriter();

        try (JcaPEMWriter pemWriter = new JcaPEMWriter(sw)) {

            PEMEncryptor encryptor = new JcePEMEncryptorBuilder("AES-256-CBC").build(password.toCharArray());
            // privateKey is a java.security.PrivateKey
            JcaMiscPEMGenerator gen = new JcaMiscPEMGenerator(privateKey, encryptor);
            pemWriter.writeObject(gen);
        }
        return sw.toString();
    }
    /**
     * 生成自签名证书及其密钥对
     * @throws Throwable
     */
    public static void generateSelfSignedCertificate() throws Throwable {
        //根证书私钥
        PrivateKey caPrivateKey;
        //从磁盘加载根证书私钥
        try(FileInputStream fileInputStream = new FileInputStream(CA_PRIVATE_KEY_FILE_PATH);) {
            caPrivateKey = PemUtil.readPemPrivateKey(fileInputStream);
        }

        //从磁盘加载根证书
        X509Certificate caCertificate;
        try(FileInputStream fileInputStream = new FileInputStream(CA_CERTIFICATE_FILE_PATH);) {
            caCertificate = (X509Certificate) KeyUtil.readX509Certificate(fileInputStream);
        }

        //证书颁发者信息
        X500Name issuer = new X500Name(caCertificate.getIssuerX500Principal().getName());

        //证书主题信息
        X500Name subject = new SubjectBuilder()
                .setCn("My common name")
                .setO("My organization")
                .setOu("My organizational unit name")
                .setC("My country code")
                .setSt("My state, or province name")
                .setL("My locality name")
                .build();

        //证书有效期
        DateTime notBefore = DateUtil.yesterday();
        DateTime notAfter = DateUtil.offset(notBefore, DateField.YEAR, 10);

        CertificateInfo certificateInfo = new CertificateInfo();
        certificateInfo.setSerial(BigInteger.valueOf(RandomUtil.randomLong(1L, Long.MAX_VALUE)));
        certificateInfo.setIssuer(issuer);
        certificateInfo.setSubject(subject);
        certificateInfo.setNotBefore(notBefore);
        certificateInfo.setNotAfter(notAfter);
        certificateInfo.setKeyAlgorithm(KEY_ALGORITHM);
        certificateInfo.setSignAlgorithm(SIGN_ALGORITHM);

        //生成自签名证书及其密钥对
        KeyAndCertificate keyAndCertificate = CertificateUtils.generateCertificate(certificateInfo, caPrivateKey);
        //保存自签名证书及其密钥对到磁盘
        CertificateUtils.save(keyAndCertificate.getPrivateKey(), SELF_SIGNED_PRIVATE_KEY_FILE_PATH,"");
        CertificateUtils.save(keyAndCertificate.getPublicKey(), SELF_SIGNED_PUBLIC_KEY_FILE_PATH);
        CertificateUtils.save(keyAndCertificate.getCertificate(), SELF_SIGNED_CERTIFICATE_FILE_PATH);
    }

    /**
     * 生成 pkcs12 格式证书
     * @throws Exception
     */
    public static void generatePkcs12Store() throws Exception {
        char[] password = SELF_SIGNED_PKCS12_CERTIFICATE_PASSWORD.toCharArray();
        //证书私钥
        PrivateKey privateKey;
        //从磁盘加载证书私钥
        try(FileInputStream fileInputStream = new FileInputStream(SELF_SIGNED_PRIVATE_KEY_FILE_PATH)) {
            privateKey = PemUtil.readPemPrivateKey(fileInputStream);
        }
        //证书
        Certificate certificate;
        //从磁盘加载证书
        try(FileInputStream fileInputStream = new FileInputStream(SELF_SIGNED_CERTIFICATE_FILE_PATH)) {
            certificate = KeyUtil.readX509Certificate(fileInputStream);
        }
        KeyStore pkcs12 = KeyStore.getInstance("pkcs12");
        pkcs12.load(null, password);
        pkcs12.setKeyEntry("self signed key", privateKey, password, new Certificate[] {certificate});
        try(FileOutputStream fileOutputStream = new FileOutputStream(SELF_SIGNED_PKCS12_FILE_PATH)) {
            pkcs12.store(fileOutputStream, password);
        }
    }


}
