package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.util.JwtUtil;
import com.example.auth.util.PasswordEncoder;
import com.example.auth.util.PasswordUtils;
import com.example.auth.vo.LoginUser;
import com.example.auth.vo.TokenUser;
import com.example.common.base.exception.ServerException;
import com.example.common.base.exception.ServerExceptionForbidden;
import com.example.common.base.exception.ServerExceptionNotFound;
import com.example.common.base.exception.ServerExceptionUnauthorized;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.mapper.SysUserMapper;
import com.example.demo.service.mapper.SysUserRoleMapper;
import com.example.demo.service.model.entity.SysDept;
import com.example.demo.service.model.entity.SysMenu;
import com.example.demo.service.model.entity.SysRole;
import com.example.demo.service.model.entity.SysUser;
import com.example.demo.service.model.vo.SysUserVo;
import com.example.demo.service.model.vo.input.SysUserInputVo;
import com.example.demo.service.model.vo.input.SysUserRoleInputVo;
import com.example.demo.service.model.vo.input.UserPasswordInputVo;
import com.example.demo.service.model.vo.query.SysUserQueryVo;
import com.example.demo.service.service.ISysDeptService;
import com.example.demo.service.service.ISysMenuService;
import com.example.demo.service.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUserVo add(TokenUser tokenUser, SysUserInputVo record) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(record, sysUser);

        String salt = PasswordUtils.getSalt();
        String password = PasswordUtils.encode(record.getPassword(), salt);
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        sysUser.setCreateBy(tokenUser.getName());
        sysUser.setCreateTime(LocalDateTime.now());
        super.save(sysUser);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        if (null != record.getDeptId()) {
            SysDept sysDept = sysDeptService.getById(record.getDeptId());
            sysUserVo.setDeptName(sysDept.getName());
        }
        sysUserVo.setRoles(Collections.EMPTY_LIST);
        return sysUserVo;
    }

    @Override
    public SysUserVo edit(TokenUser tokenUser, Long id, SysUserInputVo record) {
        if (SysConstants.ADMIN_ID.equals(id)) {
            throw new ServerExceptionForbidden("不能修改超级管理员");
        }

        SysUser user = super.getById(id);
        if (null == user) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, user);

        if (record.getPassword() != null) {
            String salt = PasswordUtils.getSalt();
            String password = PasswordUtils.encode(record.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(password);
        }
        user.setLastUpdateBy(tokenUser.getName());
        user.setLastUpdateTime(LocalDateTime.now());
        super.updateById(user);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(user, sysUserVo);
        if (null != user.getDeptId()) {
            SysDept sysDept = sysDeptService.getById(user.getDeptId());
            sysUserVo.setDeptName(sysDept.getName());
        }

        return sysUserVo;
    }

    @Override
    public void modifyPassword(TokenUser tokenUser, Long id, UserPasswordInputVo record) {
        if (SysConstants.ADMIN_ID.equals(id)) {
            throw new ServerExceptionForbidden("不能修改超级管理员");
        }

        SysUser user = super.getById(id);
        if (null == user) {
            throw ServerException.NotFound;
        }

        String salt = PasswordUtils.getSalt();
        String password = PasswordUtils.encode(record.getNewPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setLastUpdateBy(tokenUser.getName());
        user.setLastUpdateTime(LocalDateTime.now());
        super.updateById(user);
    }

    @Override
    public void delete(TokenUser tokenUser, Long id) {
        if (SysConstants.ADMIN_ID.equals(id)) {
            throw new ServerExceptionForbidden("不能删除超级管理员");
        }

        super.removeById(id);
    }

    @Override
    public void delete(TokenUser tokenUser, List<Long> ids) {
        for (Long record : ids) {
            if (SysConstants.ADMIN_ID.equals(record)) {
                throw new ServerExceptionForbidden("不能删除超级管理员");
            }
        }

        super.removeByIds(ids);
    }

    @Override
    public int saveUserRoles(TokenUser tokenUser, SysUserRoleInputVo record) {
        sysUserRoleMapper.deleteByUserId(record.getUserId());

        if (record.getRoleIds().isEmpty()) {
            return 0;
        }

        int count = sysUserRoleMapper.insertBatch(record.getUserId(), record.getRoleIds());
        return count;
    }

    @Override
    public IPage<SysUserVo> findPage(TokenUser tokenUser, PageRequest<SysUserQueryVo> pageRequest) {
        Page<SysUserVo> page = new Page<>(pageRequest.getPage(), pageRequest.getSize());
        SysUserQueryVo params = pageRequest.getParams();

        return sysUserMapper.findListWithRole(page, params);
    }

    @Override
    public SysUser findByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>() {{
            eq(SysUser::getName, username);
        }};
        try {
            return sysUserMapper.selectOne(queryWrapper);
        } catch (TooManyResultsException e) {
            log.error("same username error!");
            throw ServerException.ServerError;
        }
    }

    @Override
    public Set<String> findPermissions(String username) {
        List<SysMenu> sysMenus;
        if (SysConstants.ADMIN.equals(username)) {
            sysMenus = sysMenuService.findAll();
        } else {
            sysMenus = sysMenuService.findByUsername(username);
        }
        Set<String> perms = new HashSet<>();
        for (SysMenu sysMenu : sysMenus) {
            if (!ObjectUtils.isEmpty(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }

        return perms;
    }

    @Override
    public List<SysRole> findUserRoles(String username) {
        return sysUserRoleMapper.findUserRoles(username);
    }

    @Override
    public String login(String username, String password) {
        // 查找用户
        SysUser sysUser = findByUsername(username);
        if (null == sysUser) {
            throw new ServerExceptionNotFound("account not exists!");
        }

        // 验证
        if (!new PasswordEncoder(sysUser.getSalt()).matches(sysUser.getPassword(), password)) {
            throw new ServerExceptionUnauthorized("account password error!");
        }

        // 获取角色
        Set<String> roles = findUserRoles(username)
                .stream()
                .map(SysRole::getName)
                .collect(Collectors.toSet());

        // 用户权限
        Set<String> permissions = findPermissions(username);

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);
        return JwtUtil.sign(loginUser, roles, permissions);
    }
}
