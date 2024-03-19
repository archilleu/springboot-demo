package com.example.auth.resolver;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth.util.JwtUtil;
import com.example.auth.vo.TokenUser;
import com.example.common.base.exception.ServerExceptionUnauthorized;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

/**
 * 自动添加controller token user入参
 *
 * @author cjy
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == TokenUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String token = request.getHeader("token");
        if (null == token) {
            throw new ServerExceptionUnauthorized("token is null");
        }
        DecodedJWT decodedjwt = JWT.decode(token);
        String name = decodedjwt.getClaim(JwtUtil.USERNAME).asString();
        Long id = decodedjwt.getClaim(JwtUtil.USER_ID).asLong();
        List<String> permissions = decodedjwt.getClaim(JwtUtil.PERMISSIONS).asList(String.class);
        List<String> roles = decodedjwt.getClaim(JwtUtil.ROLES).asList(String.class);
        return new TokenUser(name, id, new HashSet<>(permissions), new HashSet<>(roles));
    }
}
