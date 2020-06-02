package com.haoya.demo.app.model.sys;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class SysMenu {

    public BigInteger getMenuId() {
        return menuId;
    }

    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Short getSpread() {
        return spread;
    }

    public void setSpread(Short spread) {
        this.spread = spread;
    }

    public Short getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Short orderNum) {
        this.orderNum = orderNum;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger menuId;
    @Column
    private BigInteger parentId;
    @Column
    private String name;
    @Column
    private String url;
    @Column
    private String perms;
    @Column
    private Short type;         //0:目录,1:菜单,2:按钮
    @Column
    private String icon;
    @Column
    private Short spread;
    @Column
    private Short orderNum;
}
