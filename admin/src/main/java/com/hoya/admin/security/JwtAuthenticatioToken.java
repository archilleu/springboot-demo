package com.hoya.admin.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义令牌对象
 */
public class JwtAuthenticatioToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String token;

    public JwtAuthenticatioToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtAuthenticatioToken(Object principal, Object credentials, String token) {
        super(principal, credentials);
        this.token = token;
    }

    public JwtAuthenticatioToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
        super(principal, credentials, authorities);
        this.token = token;
    }

}
