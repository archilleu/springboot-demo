package com.example.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth.config.JwtProperties;
import com.example.auth.vo.LoginUser;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil {

    /**
     * 用户名称
     */
    public static final String USERNAME = "username";
    /**
     * 用户id
     */
    public static final String USER_ID = "userId";
    /**
     * 权限列表
     */
    public static final String PERMISSIONS = "permissions";
    /**
     * 角色列表
     */
    public static final String ROLES = "roles";
    private static JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        JwtUtil.jwtProperties = jwtProperties;
    }


    /**
     * 校验token是否正确，防止伪造
     *
     * @param token    密钥
     * @param username 用户
     * @return 是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    // 验证匹配username字段
                    .withClaim(USERNAME, username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 生成签名
     */
    public static String sign(LoginUser user, Set<String> roles, Set<String> permissions) {
        Date date = DateUtils.addSeconds(new Date(), jwtProperties.getExpireSecond());
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.create()
                .withClaim(USERNAME, user.getName())
                .withClaim(USER_ID, user.getId())
                .withArrayClaim(ROLES, roles.toArray(new String[0]))
                .withArrayClaim(PERMISSIONS, permissions.toArray(new String[0]))
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 解析token，获取token数据
     */
    public static DecodedJWT getJwtInfo(String token) {
        return JWT.decode(token);
    }

    /**
     * 获取用户名
     */
    public static String getName(String token) {
        if (ObjectUtils.isEmpty(token)) {
            return null;
        }

        DecodedJWT decodedJwt = getJwtInfo(token);
        return decodedJwt.getClaim(USERNAME).asString();
    }

    public static List<String> getPermissions(String token) {
        if (ObjectUtils.isEmpty(token)) {
            return null;
        }

        DecodedJWT decodedJwt = getJwtInfo(token);
        return decodedJwt.getClaim(PERMISSIONS).asList(String.class);
    }

    public static List<String> getRoles(String token) {
        if (ObjectUtils.isEmpty(token)) {
            return null;
        }

        DecodedJWT decodedJwt = getJwtInfo(token);
        return decodedJwt.getClaim(ROLES).asList(String.class);
    }

    /**
     * 获取创建时间
     */
    public static Date getIssuedAt(String token) {
        DecodedJWT decodedJwt = getJwtInfo(token);
        return decodedJwt.getIssuedAt();
    }

    /**
     * 获取过期时间
     */
    public static Date getExpireDate(String token) {
        DecodedJWT decodedJwt = getJwtInfo(token);
        return decodedJwt.getExpiresAt();
    }

    /**
     * 判断token是否已过期
     */
    public static boolean isExpired(String token) {
        Date expireDate = getExpireDate(token);
        if (expireDate == null) {
            return true;
        }
        return expireDate.before(new Date());
    }
}