package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.exception.AppException;
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
public class SysMenuRepositoryImpl implements SysMenuExtRepository {
    @Override
    public void deleteMenuAndChild(BigInteger menu_id) {
        try {
            String sql = "SELECT menu_id FROM sys_menu WHERE menu_id = ?1";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, menu_id);
            query.getSingleResult();
        } catch (NoResultException e) {
            throw AppException.NotFound;
        }

        List<BigInteger> ids = new LinkedList<>();
        ids.add(menu_id);

        //获取子菜单
        try {
            while (true) {
                String sql = "SELECT menu_id FROM sys_menu WHERE parent_id = ?1";
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, menu_id);
                menu_id = (BigInteger) query.getSingleResult();
                ids.add(menu_id);
            }
        } catch (NoResultException e) {
            //没有子菜单，执行删除操作
            try {
                String sql = "DELETE FROM sys_menu WHERE menu_id in(?1)";
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, ids);

                query.executeUpdate();
            } catch (Exception ex) {
                throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            }
        } catch (Exception e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PersistenceContext
    private EntityManager em;
}
