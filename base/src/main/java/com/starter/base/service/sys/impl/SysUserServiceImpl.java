package com.starter.base.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.base.constant.SysConstants;
import com.starter.base.controller.vo.SysUserRolesVo;
import com.starter.base.mapper.sys.SysRoleMapper;
import com.starter.base.mapper.sys.SysUserMapper;
import com.starter.base.mapper.sys.SysUserRoleMapper;
import com.starter.base.model.sys.SysMenu;
import com.starter.base.model.sys.SysRole;
import com.starter.base.model.sys.SysUser;
import com.starter.base.service.sys.SysMenuService;
import com.starter.base.service.sys.SysUserService;
import com.starter.base.shiro.util.JwtUtil;
import com.starter.base.shiro.util.PasswordEncoder;
import com.starter.common.exception.ServerException;
import com.starter.common.exception.ServerExceptionNotFound;
import com.starter.common.exception.ServerExceptionUnauthorized;
import com.starter.common.page.PageHelper;
import com.starter.common.page.PageRequest;
import com.starter.common.page.PageResult;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysUser findByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NAME", username);
        try {
            return sysUserMapper.selectOne(queryWrapper);
        } catch (TooManyResultsException e) {
            log.error("same username error!");
            throw ServerException.ServerError;
        }
    }

    @Override
    public String login(String username, String password) {
        // 查找用户
        SysUser sysUser = findByUsername(username);
        if (null == sysUser) {
            throw new ServerExceptionNotFound("account not exists!");
        }

        if (!new PasswordEncoder(sysUser.getSalt()).matches(sysUser.getPassword(), password)) {
            throw new ServerExceptionUnauthorized("account password error!");
        }

        // 用户角色
        Set<String> roles = findUserRoles(sysUser.getId())
                .stream()
                .map(SysRole::getName)
                .collect(Collectors.toSet());

        // 用户权限
        Set<String> permissions = findPermissions(username);

        return JwtUtil.sign(sysUser, roles, permissions);
    }

    public PageResult findPage(PageRequest pageRequest) {
        return PageHelper.findPage(pageRequest, sysUserMapper, "findListWithRole", pageRequest.getParams());
    }

    @Override
    public Set<String> findPermissions(String userName) {
        List<SysMenu> sysMenus;
        if (SysConstants.ADMIN.equals(userName)) {
            sysMenus = sysMenuService.findAll();
        } else {
            sysMenus = sysMenuService.findByUsername(userName);
        }
        Set<String> perms = new HashSet<>();
        for (SysMenu sysMenu : sysMenus) {
            if (!StringUtils.isEmpty(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }

        return perms;
    }

    @Override
    public List<SysRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }

    @Override
    @Transactional
    public int saveUserRoles(SysUserRolesVo sysUserRolesBean) {

        sysUserRoleMapper.deleteByUserId(sysUserRolesBean.getUserId());

        if (sysUserRolesBean.getRoles().isEmpty()) {
            return 0;
        }

        int count = sysUserRoleMapper.insertBatch(sysUserRolesBean.getUserId(), sysUserRolesBean.getRoles());
        return count;
    }
}
