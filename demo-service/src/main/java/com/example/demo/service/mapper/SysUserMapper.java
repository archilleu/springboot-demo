package com.example.demo.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.service.model.entity.SysUser;
import com.example.demo.service.model.vo.SysUserVo;
import com.example.demo.service.model.vo.query.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查找带角色列表的用户
     *
     * @param page 分页
     * @param vo   查询参数
     * @return 列表
     */
    IPage<SysUserVo> findListWithRole(IPage<SysUserVo> page, @Param("vo") SysUserQueryVo vo);
}
