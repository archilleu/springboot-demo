package com.haoya.demo.app.sys.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class SysParam {

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public static final String kRESOURCE_MAIN = "/common/sys-res/";

    public static SysParam DEFAULT() {
        if(null == instance) {
            synchronized (SysParam.class) {
                instance = new SysParam();
                instance.setTitle("Google科技有限公司");
                instance.setLoginPage("/login.html");
                instance.setHomePage("/home.html");
                instance.setCopyright("Google信息科技有限公司");
                instance.setLogo("default-logo.png");
                instance.setFavicon("default-favicon.ico");
                instance.setBackgroundImage("default-bg.png");
            }
        }

        return instance;
    }

    private static SysParam instance;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column
    private String title;

    @Column
    private String loginPage;

    @Column
    private String homePage;

    @Column
    private String domain;

    @Column
    private String copyright;

    @Column
    private String logo;

    @Column
    private String favicon;

    @Column
    private String backgroundImage;
}
