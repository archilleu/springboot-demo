package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysMenu;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Transactional
public class SysRoleMenuExtRepositoryImpl implements SysRoleMenuExtRepository {

    @Override
    public List<SysMenu> getRoleMenu(List<BigInteger> roleIds) {
        try {
            String jpql = "select distinct sm from SysMenu sm, SysRoleMenu rm where rm.roleId in ?1 and rm.menuId=sm.menuId";
            Query query = em.createQuery(jpql);
            query.setParameter(1, roleIds);
            List<SysMenu> list = query.getResultList();
            return list;
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @Override
    public void modifyRoleMenu(BigInteger roleId, List<BigInteger> menus) {
        try {
            String deleteSql = "delete from sys_role_menu where role_id=?1";
            Query query = em.createNativeQuery(deleteSql);
            query.setParameter(1, roleId);
            query.executeUpdate();

            try {
                String addSql = "insert into sys_role_menu (role_id, menu_id) values(?1, ?2)";
                for(BigInteger menuId : menus) {
                    query = em.createNativeQuery(addSql);
                    query.setParameter(1, roleId);
                    query.setParameter(2,menuId);
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
