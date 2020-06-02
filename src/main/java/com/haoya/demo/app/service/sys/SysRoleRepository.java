package com.haoya.demo.app.service.sys;

import com.haoya.demo.app.model.sys.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface SysRoleRepository extends JpaRepository<SysRole, BigInteger> {

    Page<SysRole> findByRoleNameLikeAndRemarkLike(String roleName, String remark, Pageable pageable);

    /**
     * 查询用户的角色
     * @param userId
     * @return
     */
    @Query(
            value="select sys_role.* from sys_role, sys_user_role where sys_user_role.user_id=?1 and sys_role.role_id=sys_user_role.role_id",
            nativeQuery = true
    )
    List<SysRole> findByUserId(BigInteger userId);
}
