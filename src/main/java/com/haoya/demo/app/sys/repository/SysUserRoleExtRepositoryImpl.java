package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysRole;
import com.haoya.demo.app.sys.entity.SysUserRole;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Transactional
public class SysUserRoleExtRepositoryImpl implements SysUserRoleExtRepository {
    @Override
    public void modifyUserRole(BigInteger userId, List<SysRole> roles) {
        try {
            String deleteSql = "delete from sys_user_role where user_id=?1";
            Query query = em.createNativeQuery(deleteSql);
            query.setParameter(1, userId);
            query.executeUpdate();

            try {
                String addSql = "insert into sys_user_role (user_id, role_id) values(?1, ?2)";
                for(SysRole role : roles) {
                    query = em.createNativeQuery(addSql);
                    query.setParameter(1, userId);
                    query.setParameter(2, role.getRoleId());
                    query.executeUpdate();
                }
            } catch (DataIntegrityViolationException e) {
            }
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PersistenceContext
    private EntityManager em;
}
