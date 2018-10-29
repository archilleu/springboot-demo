package com.hoya.app.config;

import javax.activation.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	@Bean(name = "dataSource")
	@Profile("dev")
	public DataSource devDatasource(Environment env) {
		HikariDataSource ds = getDataSource(env);
		ds.setMaximumPoolSize(10);
		return (DataSource)ds;
	}

	@Bean(name = "dataSource")
	@Profile("prod")
	public DataSource prodDatasource(Environment env) {
		HikariDataSource ds = getDataSource(env);
		ds.setMaximumPoolSize(10);
		return (DataSource)ds;
	}
	
	private HikariDataSource getDataSource(Environment env) {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
		ds.setUsername(env.getProperty("spring.datasource.username"));
		ds.setPassword(env.getProperty("spring.datasource.password"));
		ds.setJdbcUrl(env.getProperty("spring.datasource.driver-class-name"));
		return ds;
	}
}
