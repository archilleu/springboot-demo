package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface SysDeptRepository extends JpaRepository<SysDept, BigInteger> {
    List<SysDept> findByNameLike(String name);
}
