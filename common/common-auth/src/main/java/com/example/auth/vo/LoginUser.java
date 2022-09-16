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
    private String id;
    private String name;
    private String nickname;
}
