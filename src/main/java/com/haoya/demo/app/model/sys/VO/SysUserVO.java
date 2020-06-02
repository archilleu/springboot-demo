package com.haoya.demo.app.model.sys.VO;

import com.haoya.demo.app.model.sys.SysDept;
import com.haoya.demo.app.model.sys.SysRole;
import com.haoya.demo.app.model.sys.SysUser;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public class SysUserVO {

    public SysUserVO() {

    }

    public SysUserVO(SysUser sysUser, SysDept sysDept, List<SysRole> sysRoles) {
        this.userId = sysUser.getUserId();
        this.username = sysUser.getUsername();
        this.nickname = sysUser.getNickname();
        this.password = sysUser.getPassword();
        this.email = sysUser.getEmail();
        this.mobile = sysUser.getMobile();
        this.status = sysUser.getStatus();
        this.createTime = sysUser.getCreateTime();

        this.sysDept = sysDept;
        this.sysRoles = sysRoles;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public SysDept getSysDept() {
        return sysDept;
    }

    public void setSysDept(SysDept sysDept) {
        this.sysDept = sysDept;
    }

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    private BigInteger userId;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String mobile;
    private Short status;
    private Timestamp createTime;

    private SysDept sysDept;
    private List<SysRole> sysRoles;

}
