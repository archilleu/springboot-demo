package com.starter.base.config.properties;

import com.starter.base.constant.CommonConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String tokenName = CommonConstant.JWT_DEFAULT_TOKEN_NAME;

    private String secret = CommonConstant.JWT_DEFAULT_SECRET;

    private int expireSecond = CommonConstant.JWT_DEFAULT_EXPIRE_SECOND;

}