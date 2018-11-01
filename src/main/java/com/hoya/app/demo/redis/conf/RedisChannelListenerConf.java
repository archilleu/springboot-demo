package com.hoya.app.demo.redis.conf;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.hoya.app.demo.redis.RedisChannelListener;

@Configuration
public class RedisChannelListenerConf {
	
	@Bean
	MessageListenerAdapter listenerAdapter( ) {

		MessageListenerAdapter adapter = new MessageListenerAdapter(new RedisChannelListener());
		adapter.setSerializer(new JdkSerializationRedisSerializer());
		return adapter;
	}

	@Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //订阅所有news.* 频道内容,应该放在配置文件里面
        container.addMessageListener(listenerAdapter, new PatternTopic("news.*"));
        return container;
    }
	
	/**
	 * 构造json格式的bean，用法为
	 * @Resource
	 * RedisTemplate<String, User> jsonRedisTemplate;
	 */
	@Bean("jsonRedisTemplate")
	public RedisTemplate<String, Object> redisTemplate(
			RedisConnectionFactory redisConnectionFactory)
					throws UnknownHostException {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		return template;
	}
}