package com.example.demo.service.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图对象：登录token")
public class LoginTokenVo {
    @ApiModelProperty(value = "用户token", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJtaXNzaW9ucyI6WyJyZWFkIl0sInJvbGVzIjpbImFkbWluIl0sImV4cCI6MTY5MzE1NzY0MSwidXNlcklkIjoiaWQiLCJ1c2VybmFtZSI6InN0cmluZyJ9.FO2UtmP7M5I9zPxw9P7WqtL7mCYi0J6v5tPzqTv4Re4")
    private String token;
}
