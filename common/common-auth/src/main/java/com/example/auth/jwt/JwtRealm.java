package com.example.auth.jwt;

import com.example.auth.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * @author cjy
 */
public class JwtRealm extends AuthorizingRealm {

    /**
     * * 是否支持该类型的令牌
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 只支持基于JWTToken的自定义令牌
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String token = principalCollection.toString();
        List<String> roles = JwtUtil.getRoles(token);
        List<String> permissions = JwtUtil.getPermissions(token);

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
        String name = JwtUtil.getName(token);
        return new SimpleAuthenticationInfo(token, name, "jwtRealm");
    }
}
