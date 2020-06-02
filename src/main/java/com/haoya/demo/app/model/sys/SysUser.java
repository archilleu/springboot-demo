package com.haoya.demo.app.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class SysUser {
    public static final short STATUS_ENABLE = 1;
    public static final short STATUS_DISABLE = 0;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger userId;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String mobile;
    private Short status;   //状态  0：禁用   1：正常
    private Timestamp createTime;

    /*
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name="sys_dept_user", joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")}
    ,inverseJoinColumns = {@JoinColumn(name = "deptId", referencedColumnName = "deptId")})
    private SysDept dept;

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name="sys_user_role", joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")}
    ,inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "roleId")})
    private List<SysRole> roles;
     */
}
