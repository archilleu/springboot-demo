package com.starter.base.shiro.jwt;

import com.starter.base.service.sys.SysUserService;
import com.starter.base.shiro.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtRealm extends AuthorizingRealm {

    @Autowired
    SysUserService sysUserService;

    /**
     * * 是否支持该类型的令牌
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 只支持基于JWTToken的自定义令牌
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String token = principalCollection.toString();
        String[] roles = JwtUtil.getRoles(token);
        String[] permissions = JwtUtil.getPermissions(token);

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (String role : roles) {
            simpleAuthorizationInfo.addRole(role);
        }
        for (String permission : permissions) {
            simpleAuthorizationInfo.addStringPermission(permission);
        }

        return simpleAuthorizationInfo;
    }

    /**
     * 简单生成授权信息对象，在Matcher中判断token是否合法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户信息
        String token = authenticationToken.getPrincipal().toString();
        String name = JwtUtil.getUsername(token);
        return new SimpleAuthenticationInfo(token, name, "jwtRealm");
    }
}
