package com.haoya.demo.app.sys.repository;

import java.math.BigInteger;

public interface SysDeptExtRepository {
    /**
     * 删除部门及其子部门
     * @param dept_id
     * 部门id
     */
    void deleteDeptAndChild(BigInteger dept_id);
}
