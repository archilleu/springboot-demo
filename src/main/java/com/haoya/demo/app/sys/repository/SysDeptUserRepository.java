package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysDeptUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SysDeptUserRepository extends JpaRepository<SysDeptUser, BigInteger> {
    /**
     * 维护
     * select sys_dept_user.dept_id from sys_dept_user left join sys_dept on sys_dept.dept_id=sys_dept_user.dept_id where sys_dept.dept_id is null;
     * select sys_dept_user.user_id from sys_dept_user left join sys_user on sys_user.user_id=sys_dept_user.user_id where sys_user.user_id is null;
     */
}
