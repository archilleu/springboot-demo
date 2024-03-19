package com.example.demo.service.config;

import liquibase.change.DatabaseChange;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author cjy
 */
@ConditionalOnClass({SpringLiquibase.class, DatabaseChange.class})
@ConditionalOnProperty(
        prefix = "spring.liquibase",
        name = {"enabled"},
        matchIfMissing = true
)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@SuppressWarnings("unused")
public class LiquibaseAutoConfiguration {
}
