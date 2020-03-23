package com.haoya.demo.app.sys.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class SysRoleMenu {

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public BigInteger getMenuId() {
        return menuId;
    }

    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column
    private BigInteger roleId;

    @Column
    private BigInteger menuId;
}
