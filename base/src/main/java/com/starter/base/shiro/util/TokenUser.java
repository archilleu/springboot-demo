package com.starter.base.shiro.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TokenUser {

    String username;

    Long userId;

    Set<String> permissions;

    Set<String> roles;
}
