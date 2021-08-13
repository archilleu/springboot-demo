package com.starter.base.shiro.jwt;

import com.starter.base.shiro.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * 校验token是否合法,校验通过是正常登陆签发的token，失败是伪造的
 */
@Slf4j
public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = authenticationToken.getPrincipal().toString();
        String name = authenticationInfo.getCredentials().toString();
        if (JwtUtil.verify(token, name)) {
            return true;
        }

        log.warn("JWT Token CredentialsMatch error,user:" + name);
        return false;
    }

}
