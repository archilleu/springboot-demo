package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SysParamRepository extends JpaRepository<SysParam, BigInteger> {

    SysParam findByDomain(String domain);

    /**
     * 根据域名查找系统参数
     * @param domain 域名
     * @param title 标题
     * @return SysParam
     */
    Page<SysParam> findByDomainLikeAndTitleLike(String domain, String title, Pageable pageable);
}
