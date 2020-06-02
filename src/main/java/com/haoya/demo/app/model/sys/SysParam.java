package com.haoya.demo.app.model.sys;

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

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public static final String kRESOURCE_MAIN = "/common/sys-param/default";

    public static SysParam DEFAULT() {
        if(null == instance) {
            synchronized (SysParam.class) {
                if(null == instance) {
                    instance = new SysParam();
                    instance.setTitle("Google科技有限公司");
                    instance.setLoginPage("login.html");
                    instance.setHomePage("home.html");
                    instance.setCopyright("Google信息科技有限公司");
                    instance.setFavicon("default-favicon.ico");
                }
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
    private String favicon;

}
