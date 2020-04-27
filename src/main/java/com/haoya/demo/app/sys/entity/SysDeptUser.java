package com.haoya.demo.app.sys.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class SysDeptUser{

    public SysDeptUser(BigInteger deptId, BigInteger userId) {
        this.deptId = deptId;
        this.userId = userId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getDeptId() {
        return deptId;
    }

    public void setDeptId(BigInteger deptId) {
        this.deptId = deptId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private BigInteger deptId;

    private BigInteger userId;
}
