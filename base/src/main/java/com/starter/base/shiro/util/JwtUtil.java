package com.starter.base.shiro.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.starter.base.config.properties.JwtProperties;
import com.starter.base.model.sys.SysUser;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
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
                    .withClaim(USERNAME, username) // 验证匹配username字段
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 生成签名
     *
     * @param user
     * @param roles
     * @param permissions
     * @return token
     */
    public static String sign(SysUser user, Set<String> roles, Set<String> permissions) {
        Date date = DateUtils.addSeconds(new Date(), jwtProperties.getExpireSecond());
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        // 附带username信息
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
     *
     * @param token
     * @return
     */
    public static DecodedJWT getJwtInfo(String token) {
        return JWT.decode(token);
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        if (ObjectUtils.isEmpty(token)) {
            return null;
        }

        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        String username = decodedJwt.getClaim(USERNAME).asString();
        return username;
    }

    public static String[] getPermissions(String token) {
        if (ObjectUtils.isEmpty(token)) {
            return null;
        }

        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        return decodedJwt.getClaim(PERMISSIONS).asArray(String.class);
    }

    public static String[] getRoles(String token) {
        if (ObjectUtils.isEmpty(token)) {
            return null;
        }

        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        return decodedJwt.getClaim(ROLES).asArray(String.class);
    }

    /**
     * 获取创建时间
     *
     * @param token
     * @return
     */
    public static Date getIssuedAt(String token) {
        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        return decodedJwt.getIssuedAt();
    }

    /**
     * 获取过期时间
     *
     * @param token
     * @return
     */
    public static Date getExpireDate(String token) {
        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        return decodedJwt.getExpiresAt();
    }

    /**
     * 判断token是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpired(String token) {
        Date expireDate = getExpireDate(token);
        if (expireDate == null) {
            return true;
        }
        return expireDate.before(new Date());
    }
}