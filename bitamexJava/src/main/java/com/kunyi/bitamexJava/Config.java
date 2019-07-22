package com.kunyi.bitamexJava;

import com.kunyi.bitamexJava.model.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kunyi.bitamexJava.filter.CrosFilter;
import com.kunyi.bitamexJava.filter.LoginFilter;
import com.kunyi.bitamexJava.interceptor.MyInterceptor;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;

import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Configuration
//@PropertySource("classpath:config/redis.properties")
public class Config  implements WebMvcConfigurer{
	
	/*@Autowired
	private Environment environment;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisConnectionFactory jFactory = new JedisConnectionFactory();
		jFactory.setHostName(environment.getProperty("redis.host").trim());
		jFactory.setPort(Integer.parseInt(environment.getProperty("redis.port").trim()));
		jFactory.setDatabase(Integer.parseInt(environment.getProperty("redis.database").trim()));
		jFactory.setUsePool(true);
		jFactory.setPoolConfig(jedisPoolConfig);
		return jFactory;
	}
	
	@Bean
	public RedisTemplate<String, String> redisTemplate(){
		RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}*/
	
	private static List<String> interceptorUrlPatterns = new ArrayList<>();
	static {
		//添加拦截路径
		//管理员信息增删改的路径
		interceptorUrlPatterns.add(Constants.ADD_ADMIN_URL);
		interceptorUrlPatterns.add(Constants.DEL_ADMIN_URL);
		interceptorUrlPatterns.add(Constants.MODIFY_ADMIN_STATUS_URL);
		interceptorUrlPatterns.add(Constants.MODIFY_ADMIN_URL);
		//管理员新增权限
		interceptorUrlPatterns.add(Constants.ADD_ADMIN_RIGHT_URL);
		//管理员角色新增
		interceptorUrlPatterns.add(Constants.ADD_ADMIN_ROLE_URL);
		//修改用户信息
		interceptorUrlPatterns.add(Constants.MODIFY_USER_INFO_URL);
		//增加用户权限
		interceptorUrlPatterns.add(Constants.ADD_USER_RIGHT_URL);
		//增加用户角色
		interceptorUrlPatterns.add(Constants.ADD_USER_ROLE_URL);
	}


	@Autowired
	private MyInterceptor myInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor).addPathPatterns(interceptorUrlPatterns);
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	
	@Bean
    public MongoClientOptions mongoOptions() {
		Builder builder = MongoClientOptions.builder();
		builder.maxConnectionIdleTime(6000);
		builder.maxConnectionLifeTime(0);
		builder.maxWaitTime(0);
		MongoClientOptions mClientOptions = builder.build();
        return mClientOptions;
    }
	
	
	@Bean
	public FilterRegistrationBean loginFilter(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new LoginFilter());
		int length = Constants.LOGIN_FILTER_URL_LIST.size();
		String[] urls = new String[length];
		Constants.LOGIN_FILTER_URL_LIST.toArray(urls);
		registrationBean.addUrlPatterns(urls);
		registrationBean.setName("loginFilter");
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean crosFilter(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new CrosFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName("crosFilter");
		registrationBean.setOrder(2);
		return registrationBean;
	}
	
}
