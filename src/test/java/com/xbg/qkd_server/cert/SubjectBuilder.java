package com.xbg.qkd_server.cert;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.util.StringUtils;

/**
 * @Author XBG
 * @Description:
 * @Date 2024-12-21
 */

public class SubjectBuilder {
    /**
     * 常用名称（Common Name）
     */
    private String cn;
    /**
     * 企业名称（Organization）
     */
    private String o;
    /**
     * 部门（Organizational Unit）
     */
    private String ou;
    /**
     * 国家（Country）
     */
    private String c;
    /**
     * 省份（State）
     */
    private String st;
    /**
     * 城市（Locality）
     */
    private String l;

    public SubjectBuilder setCn(String cn) {
        this.cn = cn;
        return this;
    }

    public SubjectBuilder setO(String o) {
        this.o = o;
        return this;
    }

    public SubjectBuilder setOu(String ou) {
        this.ou = ou;
        return this;
    }

    public SubjectBuilder setC(String c) {
        this.c = c;
        return this;
    }

    public SubjectBuilder setSt(String st) {
        this.st = st;
        return this;
    }

    public SubjectBuilder setL(String l) {
        this.l = l;
        return this;
    }

    public X500Name build() {
        X500NameBuilder x500NameBuilder = new X500NameBuilder();
        if (StringUtils.hasLength(cn)) {
            x500NameBuilder.addRDN(BCStyle.CN, cn);
        }
        if (StringUtils.hasLength(o)) {
            x500NameBuilder.addRDN(BCStyle.O, o);
        }
        if (StringUtils.hasLength(ou)) {
            x500NameBuilder.addRDN(BCStyle.OU, ou);
        }
        if (StringUtils.hasLength(c)) {
            x500NameBuilder.addRDN(BCStyle.C, c);
        }
        if (StringUtils.hasLength(st)) {
            x500NameBuilder.addRDN(BCStyle.ST, st);
        }
        if (StringUtils.hasLength(l)) {
            x500NameBuilder.addRDN(BCStyle.L, l);
        }
        return x500NameBuilder.build();
    }
}
