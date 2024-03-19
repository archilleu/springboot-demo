package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.service.mapper.SysRoleMenuMapper;
import com.example.demo.service.model.entity.SysRoleMenu;
import com.example.demo.service.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        super.remove(queryWrapper);
    }
}
