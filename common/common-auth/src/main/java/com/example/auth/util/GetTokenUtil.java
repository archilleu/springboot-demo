package com.example.auth.util;

import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cjy
 */
public class GetTokenUtil {

    /**
     * 从请求头或者请求参数中
     *
     * @param request 请求
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request can't null");
        }

        // 从请求头中获取token
        String tokenName = "token";
        String token = request.getHeader(tokenName);
        if (ObjectUtils.isEmpty(token)) {
            // 从请求参数中获取token
            token = request.getParameter(tokenName);
        }

        return token;
    }
}
