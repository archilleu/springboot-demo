package com.hoya.admin.security;

import com.hoya.admin.util.PasswordEncoder;
import com.hoya.core.exception.AppExceptionForbidden;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            throw new AppExceptionForbidden("错误的凭证");
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new AppExceptionForbidden("账号锁定");
        }

        String presentedPassword = authentication.getCredentials().toString();
        String salt = ((JwtUserDetails) userDetails).getSalt();
        if (!new PasswordEncoder(salt).matches(userDetails.getPassword(), presentedPassword)) {
            throw new AppExceptionForbidden("账号密码不匹配");
        }
    }

}