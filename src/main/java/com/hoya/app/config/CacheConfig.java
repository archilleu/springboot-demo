package com.hoya.app.config;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis和 本地缓存配置
 */
@Configuration
public class CacheConfig {
	// 定义一个redis 的频道，默认叫cache，用于pub/sub
	@Value("${springext.cache.redis.topic:cache}")
	String topicName;
	@Value("${springext.cache.redis.expiration:3000}")
	Integer expiration;

	/**
	 * 构造json格式的bean，用法为
	 * @Resource(name="jsonRedisTemplate")
	 * RedisTemplate<String, User> jsonRedisTemplate;
	 */
	@Bean("jsonRedisTemplate")
	public RedisTemplate<String, Object> jsonRedisTemplate(
			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {

		return new JsonRedisTemplate(redisConnectionFactory);
	}
	
	@Bean
	public TwoLevelCacheManager cacheManager(StringRedisTemplate redisTemplate) {
	
		//RedisCache 需要一个RedisCacheWriter来实现读写Redis
		RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());

		//SerializationPair用于java和redis之间序列化和反序列化，这里使用自带的JdkSerializationRedisSerializer
		SerializationPair<Object> pair = SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(this.getClass().getClassLoader()));

		//构建一个RedisCache的配置，比如是否适用前缀，key和value的序列化机制
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()
				.entryTtl(Duration.ofSeconds(expiration))
				.serializeValuesWith(pair);

		//创建Cache Manager，并返回给Spring容器
		TwoLevelCacheManager cacheManager = new TwoLevelCacheManager(redisTemplate, writer, config);
		return cacheManager;
	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic(topicName));
        container.addMessageListener(listenerAdapter, new PatternTopic("news.*"));
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(final TwoLevelCacheManager cacheManager) {
		return new MessageListenerAdapter(new MessageListener() {

			public void onMessage(Message message, byte[] pattern) {
				byte[] bs = message.getChannel();
				
				try {
					// Sub 一个消息，通知缓存管理器
					String type = new String(bs, "UTF-8");
					String cacheName = new String(message.getBody(), "UTF-8");
					cacheManager.receiver(cacheName);
					System.out.println("type: " + type + " message:" + cacheName);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
	}

	class TwoLevelCacheManager extends RedisCacheManager {

		private StringRedisTemplate redisTemplate;

		public TwoLevelCacheManager(StringRedisTemplate redisTemplate, RedisCacheWriter cacheWriter,
				RedisCacheConfiguration defaultCacheConfiguration) {

			super(cacheWriter, defaultCacheConfiguration);
			this.redisTemplate = redisTemplate;
		}

		//使用RedisAndLocalCache代替Spring Boot自带的RedisCache
		@Override
		protected Cache decorateCache(Cache cache) {
			return new RedisAndLocalCache(this, (RedisCache) cache);
		}
		//通过其他分布式节点，缓存改变
		public void publishMessage(String cacheName) {
			this.redisTemplate.convertAndSend(topicName, cacheName);
		}
		// 接受一个消息清空本地缓存
		public void receiver(String name) {
			RedisAndLocalCache cache = ((RedisAndLocalCache) this.getCache(name));
			if(cache != null){
				cache.clearLocal();
			}
		}

	}

	class RedisAndLocalCache implements Cache {
		// 本地缓存提供
		ConcurrentHashMap<Object, Object> local = new ConcurrentHashMap<Object, Object>();
		RedisCache redisCache;
		TwoLevelCacheManager cacheManager;

		public RedisAndLocalCache(TwoLevelCacheManager cacheManager, RedisCache redisCache) {
			this.redisCache = redisCache;
			this.cacheManager = cacheManager;
		}

		@Override
		public String getName() {
			return redisCache.getName();
		}

		@Override
		public Object getNativeCache() {
			return redisCache.getNativeCache();
		}

		@Override
		public ValueWrapper get(Object key) {
			ValueWrapper wrapper = (ValueWrapper) local.get(key);
			if (wrapper != null) {
				return wrapper;
			} else {
				// 二级缓存取
				wrapper = redisCache.get(key);
				if (wrapper != null) {
					local.put(key, wrapper);
				}
				
				return wrapper;
			}

		}

		@Override
		public <T> T get(Object key, Class<T> type) {

			return redisCache.get(key, type);
		}

		@Override
		public <T> T get(Object key, Callable<T> valueLoader) {
			return redisCache.get(key, valueLoader);
		}

		@Override
		public void put(Object key, Object value) {
			redisCache.put(key, value);
			clearOtherJVM();

		}

		@Override
		public ValueWrapper putIfAbsent(Object key, Object value) {
			ValueWrapper v = redisCache.putIfAbsent(key, value);
			clearOtherJVM();
			return v;

		}

		@Override
		public void evict(Object key) {
			redisCache.evict(key);
			clearOtherJVM();

		}

		@Override
		public void clear() {
			redisCache.clear();

		}

		public void clearLocal() {
			this.local.clear();
		}

		protected void clearOtherJVM() {
			cacheManager.publishMessage(redisCache.getName());
		}
	}
	
	class JsonRedisTemplate extends RedisTemplate<String, Object> {
		public JsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
			super.setKeySerializer(new StringRedisSerializer());
			super.setValueSerializer(new GenericJackson2JsonRedisSerializer());
			super.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
			super.setConnectionFactory(redisConnectionFactory);
		}
	}
}