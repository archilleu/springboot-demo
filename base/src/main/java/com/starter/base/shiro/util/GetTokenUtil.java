package com.starter.base.shiro.util;

import com.starter.base.config.properties.JwtProperties;
import com.starter.base.util.HttpServletRequestUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class GetTokenUtil {

    private static String tokenName;

    public GetTokenUtil(JwtProperties jwtProperties) {
        tokenName = jwtProperties.getTokenName();
    }

    public static String getToken() {
        return getToken(HttpServletRequestUtil.getRequest());
    }

    /**
     * 从请求头或者请求参数中
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request can't null");
        }

        // 从请求头中获取token
        String token = request.getHeader(tokenName);
        if (StringUtils.isBlank(token)) {
            // 从请求参数中获取token
            token = request.getParameter(tokenName);
        }

        return token;
    }
}
