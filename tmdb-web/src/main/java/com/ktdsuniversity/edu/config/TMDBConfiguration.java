package com.ktdsuniversity.edu.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.config.interceptor.IllegalAccessInterceptor;
import com.ktdsuniversity.edu.config.interceptor.SessionInterceptor;

@Configuration
public class TMDBConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		SessionInterceptor sessionInterceptor = new SessionInterceptor();

		List<String> excludePath = new ArrayList<>();
		excludePath.add("/regist/check/duplicate/**");
		excludePath.add("/member/regist/**");
		excludePath.add("/login");
		excludePath.add("/js/**");
		excludePath.add("/css/**");
		excludePath.add("/images/**");
		excludePath.add("/");
		excludePath.add("/movie/view/**");
		excludePath.add("/file/**");
		excludePath.add("/error");

		registry.addInterceptor(sessionInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(excludePath);

		IllegalAccessInterceptor illegalAccessInterceptor = new IllegalAccessInterceptor();
		registry.addInterceptor(illegalAccessInterceptor)
				.addPathPatterns();
	}
}