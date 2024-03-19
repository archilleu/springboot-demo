package com.example.demo.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.vo.TokenUser;
import com.example.demo.service.model.entity.SysDept;
import com.example.demo.service.model.vo.SysDeptVo;
import com.example.demo.service.model.vo.input.SysDeptInputVo;

import java.util.List;

/**
 * <p>
 * 机构管理 服务类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 增加部门
     *
     * @param tokenUser 用户token
     * @param record    部门信息
     * @return 部门信息
     */
    SysDeptVo add(TokenUser tokenUser, SysDeptInputVo record);

    /**
     * 修改部门
     *
     * @param tokenUser 用户token
     * @param id        部门id
     * @param record    部门信息
     * @return 部门信息
     */
    SysDeptVo edit(TokenUser tokenUser, Long id, SysDeptInputVo record);

    /**
     * 删除部门
     *
     * @param tokenUser 用户token
     * @param id        部门id
     */
    void delete(TokenUser tokenUser, Long id);

    /**
     * 删除部门
     *
     * @param tokenUser 用户token
     * @param ids       部门ids
     */
    void delete(TokenUser tokenUser, List<Long> ids);

    /**
     * 获取部门子部门
     *
     * @param tokenUser 用户token
     * @param id        部门id
     * @return 部门列表
     */
    List<SysDeptVo> list(TokenUser tokenUser, Long id);

    /**
     * 获取部门树
     *
     * @param tokenUser 用户token
     * @return 部门树型结构列表
     */
    List<SysDeptVo> tree(TokenUser tokenUser);
}
