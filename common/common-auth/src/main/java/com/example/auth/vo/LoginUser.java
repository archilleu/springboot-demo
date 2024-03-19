package com.example.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cjy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private Long id;
    private String name;
    private String nickname;
    private String password;
}
