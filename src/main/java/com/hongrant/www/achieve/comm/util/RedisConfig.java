package com.hongrant.www.achieve.comm.util;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Redis缓存配置类
 * 
 * @author yangshaoxin
 *
 */
@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		CacheManager cacheManager = RedisCacheManager.create(factory);

		//配置缓存失效时间
//		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//		Map<String, Long> expires = new HashMap<String, Long>();
//		expires.put("thisRedis", 60*60L);//缓存1小时失效
//		cacheManager.setExpires(expires);
		return cacheManager;

		/*
		 * RedisCacheManager rcm = new RedisCacheManager(redisTemplate); //
		 * 多个缓存的名称,目前只定义了一个 rcm.setCacheNames(Arrays.asList("thisredis"));
		 * //设置缓存默认过期时间(秒) rcm.setDefaultExpiration(600); return rcm;
		 */
	}

	// 以下两种redisTemplate自由根据场景选择
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		//使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		//		template.setHashKeySerializer(new StringRedisSerializer());

		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(mapper);
		template.setValueSerializer(serializer);
		template.setHashValueSerializer(serializer);

		//		template.setValueSerializer(new FastJson2JsonRedisSerializer<Object>(Object.class));
		//		template.setHashValueSerializer(new FastJson2JsonRedisSerializer<Object>(Object.class));

		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(factory);
		return stringRedisTemplate;
	}
}
