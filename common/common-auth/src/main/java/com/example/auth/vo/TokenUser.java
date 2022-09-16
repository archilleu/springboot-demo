package com.example.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @author cjy
 */
@Data
@ToString
@AllArgsConstructor
public class TokenUser {
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户权限
     */
    private Set<String> permissions;
    /**
     * 用户角色
     */
    private Set<String> roles;
}
