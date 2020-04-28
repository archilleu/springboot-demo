package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SysParamRepository extends JpaRepository<SysParam, BigInteger> {
    /**
     * 根据域名查找系统参数
     * @param domain 域名
     * @return SysParam
     */
    SysParam findByDomain(String domain);
}
