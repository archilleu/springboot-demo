package com.hoya.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 

 * @ClassName: ServerConfig

 * @Description: 配置管理文件application.properties server项内容，

 */

@Configuration
@ConfigurationProperties("server")
public class ServerConfig {

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private int port;
}
