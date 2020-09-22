package com.hoya.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "datasource")
@Data
public class BackupDataSourceProperties {

    private String host;
    private String username;
    private String password;
    private String database;
}
