package com.haoya.demo.app.service.sys;

import com.haoya.demo.app.exception.AppException;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Deque;
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
        Deque<BigInteger> parents = new LinkedList<>();
        ids.add(menu_id);
        parents.add(menu_id);

        try {
            //获取子菜单
            while (!parents.isEmpty()) {
                menu_id = parents.pollFirst();
                String sql = "SELECT menu_id FROM sys_menu WHERE parent_id = ?1";
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, menu_id);
                List<BigInteger> res =  query.getResultList();
                parents.addAll(res);
                ids.addAll(res);
            }

            //删除
            String sql = "DELETE FROM sys_menu WHERE menu_id in(?1)";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, ids);

            query.executeUpdate();
        } catch (Exception e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PersistenceContext
    private EntityManager em;
}
