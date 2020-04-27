package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface SysDeptRepository extends JpaRepository<SysDept, BigInteger>, SysDeptExtRepository {

    List<SysDept> findByNameLike(String name);

    /**
     * 查询用户的角色
     * @param userId
     * @return
     */
    @Query(
            value="select sys_dept.* from sys_dept, sys_dept_user where sys_dept_user.user_id=?1 and sys_dept.dept_id=sys_dept_user.dept_id",
            nativeQuery = true
    )
    SysDept findByUserId(BigInteger userId);
}
