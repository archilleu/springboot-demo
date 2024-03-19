package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.vo.TokenUser;
import com.example.common.base.exception.ServerException;
import com.example.common.base.exception.ServerExceptionNotFound;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.mapper.SysRoleMapper;
import com.example.demo.service.model.entity.SysMenu;
import com.example.demo.service.model.entity.SysRole;
import com.example.demo.service.model.entity.SysRoleMenu;
import com.example.demo.service.model.vo.input.SysRoleInputVo;
import com.example.demo.service.model.vo.query.SysRoleQueryVo;
import com.example.demo.service.service.ISysMenuService;
import com.example.demo.service.service.ISysRoleMenuService;
import com.example.demo.service.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Override
    public SysRole add(TokenUser tokenUser, SysRoleInputVo record) {
        log.info("用户[{}]新增角色", tokenUser.getName());

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(record, sysRole);
        sysRole.setCreateBy(tokenUser.getName());
        sysRole.setCreateTime(LocalDateTime.now());
        super.save(sysRole);
        return sysRole;
    }

    @Override
    public SysRole edit(TokenUser tokenUser, Long id, SysRoleInputVo record) {
        log.info("用户[{}]修改角色", tokenUser.getName());

        SysRole sysRole = super.getById(id);
        if (null == sysRole) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysRole);
        sysRole.setLastUpdateBy(tokenUser.getName());
        sysRole.setLastUpdateTime(LocalDateTime.now());
        super.updateById(sysRole);
        return sysRole;
    }

    @Override
    public void delete(TokenUser tokenUser, Long id) {
        log.info("用户[{}]删除角色", tokenUser.getName());

        super.removeById(id);
    }

    @Override
    public void delete(TokenUser tokenUser, List<Long> ids) {
        log.info("用户[{}]批量删除角色", tokenUser.getName());

        super.removeByIds(ids);
    }

    @Override
    public IPage<SysRole> findPage(TokenUser tokenUser, PageRequest<SysRoleQueryVo> pageRequest) {
        log.info("用户[{}]查询角色", tokenUser.getName());
        Page<SysRole> page = new Page<>(pageRequest.getPage(), pageRequest.getSize());
        SysRoleQueryVo vo = pageRequest.getParams();

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(vo.getName())) {
            queryWrapper.like(SysRole::getName, vo.getName());
        }

        return super.page(page, queryWrapper);
    }

    @Override
    public List<SysMenu> findRoleMenus(TokenUser tokenUser, Long roleId) {
        log.info("用户[{}]查询角色菜单", tokenUser.getName());

        SysRole sysRole = super.getById(roleId);
        if (null == sysRole) {
            throw new ServerExceptionNotFound("角色不存在");
        }

        if (SysConstants.ADMIN_ID.equals(sysRole.getId())) {
            // 如果是超级管理员，返回全部
            return sysMenuService.list();
        }

        return sysMenuService.findRoleMenus(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveRoleMenus(TokenUser tokenUser, Long roleId, List<Long> menuIds) {
        log.info("用户[{}]插入角色菜单", tokenUser.getName());

        sysRoleMenuService.deleteByRoleId(roleId);
        if (menuIds.isEmpty()) {
            return 0;
        }

        menuIds.forEach(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setCreateBy(tokenUser.getName());
            sysRoleMenu.setCreateTime(LocalDateTime.now());
            sysRoleMenuService.save(sysRoleMenu);
        });

        return menuIds.size();
    }

}
