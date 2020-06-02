package com.haoya.demo.app.service.sys;

import com.haoya.demo.app.model.sys.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface SysUserRepository extends JpaRepository<SysUser, BigInteger> {

    SysUser findByUsername(String username);

    Page<SysUser> findByUsernameLikeAndNicknameLike(String username, String nickname, Pageable pageable);

    @Query(
            value="SELECT sys_user.* FROM sys_user, sys_dept_user where sys_dept_user.dept_id=?1 and sys_user.user_id=sys_dept_user.user_id",
            countQuery = "SELECT count(*) FROM sys_user, sys_dept_user where sys_dept_user.dept_id=?1 and sys_user.user_id=sys_dept_user.user_id",
            nativeQuery = true
    )
    Page<SysUser> findByDeptId(BigInteger deptId, Pageable pageable);
}
