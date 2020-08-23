package com.hoya.admin.util;

import java.io.Serializable;
import java.util.*;

import com.hoya.admin.security.JwtUserDetails;
import com.hoya.core.exception.AppExceptionForbidden;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * JWT工具类
 */
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    private static final String USERNAME = Claims.SUBJECT;

    /**
     * 用户id
     */
    private static final String USER_ID = "userI";

    /**
     * 创建时间
     */
    private static final String CREATED = "created";
    /**
     * 权限列表
     */
    private static final String AUTHORITIES = "authorities";
    /**
     * 密钥
     */
    private static final String SECRET = "fyDod";
    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成令牌
     *
     * @param authentication 授权
     * @return 令牌
     */
    public static String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(3);
        JwtUserDetails user = (JwtUserDetails) authentication.getPrincipal();
        claims.put(USERNAME, user.getUsername());
        claims.put(USER_ID, user.getId());
        claims.put(CREATED, new Date());
        claims.put(AUTHORITIES, authentication.getAuthorities());
        return generateToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 认证token
     *
     * @param request
     */
    public static void authentication(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = JwtTokenUtils.getToken(request);

        // 请求令牌为空返回空
        if (null == token) {
            throw new AppExceptionForbidden("用户未登陆");
        }

        // 校验令牌
        isTokenExpired(token);

        return;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            throw new AppExceptionForbidden("非法token");
        }
    }

    /**
     * 刷新令牌
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 判断令牌是否过期
     */
    public static void isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();

        if (expiration.before(new Date()))
            throw new AppExceptionForbidden("token过期");

        return;
    }

    /**
     * 获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if (token == null) {
            token = request.getHeader("token");
        } else if (token.contains(tokenHead)) {
            token = token.substring(tokenHead.length());
        }
        if ("".equals(token)) {
            token = null;
        }

        return token;
    }

}
