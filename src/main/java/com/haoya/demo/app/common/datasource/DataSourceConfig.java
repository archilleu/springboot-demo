package com.haoya.demo.app.common.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource dataSourceMaster(){
        return  DruidDataSourceBuilder.create().build();
    }

    /*
    @Bean
    @ConfigurationProperties("spring.datasource.druid.salve")
    public DataSource dataSourceSlave(){
        return DruidDataSourceBuilder.create().build();
    }
     */
}
