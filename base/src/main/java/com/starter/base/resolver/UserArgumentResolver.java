package com.starter.base.resolver;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.starter.base.config.properties.JwtProperties;
import com.starter.base.shiro.util.JwtUtil;
import com.starter.base.shiro.util.TokenUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;

/**
 * 自动添加controller token user入参
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private static String tokenName;

    public UserArgumentResolver(JwtProperties jwtProperties) {
        tokenName = jwtProperties.getTokenName();
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == TokenUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String token = request.getHeader(tokenName);
        DecodedJWT decodedJWT = JWT.decode(token);
        String username = decodedJWT.getClaim(JwtUtil.USERNAME).asString();
        Long userId = decodedJWT.getClaim(JwtUtil.USER_ID).asLong();
        String[] permissions = decodedJWT.getClaim(JwtUtil.PERMISSIONS).asArray(String.class);
        String[] roles = decodedJWT.getClaim(JwtUtil.ROLES).asArray(String.class);
        TokenUser tokenUser = new TokenUser(username, userId,
                new HashSet<>(Arrays.asList(permissions)), new HashSet<>(Arrays.asList(roles)));
        return tokenUser;
    }
}
