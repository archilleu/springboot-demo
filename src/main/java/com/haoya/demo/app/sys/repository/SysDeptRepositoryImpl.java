package com.haoya.demo.app.sys.repository;

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
public class SysDeptRepositoryImpl implements SysDeptExtRepository {
    @Override
    public void deleteDeptAndChild(BigInteger dept_id) {
        try {
            String sql = "SELECT dept_id FROM sys_dept WHERE dept_id = ?1";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, dept_id);
            query.getSingleResult();
        } catch (NoResultException e) {
            throw AppException.NotFound;
        }

        List<BigInteger> ids = new LinkedList<>();
        Deque<BigInteger> parents = new LinkedList<>();
        ids.add(dept_id);
        parents.add(dept_id);

        try {
            //获取子部门
            while (!parents.isEmpty()) {
                dept_id = parents.pollFirst();
                String sql = "SELECT dept_id FROM sys_dept WHERE parent_id = ?1";
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, dept_id);
                List<BigInteger> res = query.getResultList();
                parents.addAll(res);
                ids.addAll(res);
            }

            //删除
            String sql = "DELETE FROM sys_dept WHERE dept_id in(?1)";
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
