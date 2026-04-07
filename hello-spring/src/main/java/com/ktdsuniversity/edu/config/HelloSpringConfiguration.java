package com.ktdsuniversity.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.config.interceptor.IllegalAccessInterceptor;
import com.ktdsuniversity.edu.config.interceptor.SessionInterceptor;

// application.yml에서 작성할 수 없는 설정들을 적용하기 위한 Annotation
// @Component 의 자식 Annotation
@Configuration
// spring-boot-starter-validation 동작 활성화 시키기
// @EnableWebMvc가 추가되면 application.yml의 mvc 관련 설정들이 모두 무시된다.
//   1. spring.mvc.view.prefix, spring.mvc.view.suffix
//   2. src/main/resources/static 경로 사용 불가능.
@EnableWebMvc
public class HelloSpringConfiguration implements
		// WebMvc 설정을 위한 Configuration
		// @EnableWebMvc Annotation 에서 적용하는 기본 설정들을 변경하기 위함.
		WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		SessionInterceptor interceptor = new SessionInterceptor();
		registry.addInterceptor(interceptor).addPathPatterns("/**")// 모든 URL을 대상으로 SessionInterceptor를 수행
				.excludePathPatterns("/login", "/regist", "/regist/check/duplicate/**", "/", "/js/**", "/css/**",
						"/image/**", "/view/**", "/file/**")// SessionInterceptor를 수행하지 않을 URL 명시
		;
		IllegalAccessInterceptor illegalAccessInterceptor = new IllegalAccessInterceptor();
		registry.addInterceptor(illegalAccessInterceptor).addPathPatterns("/login", "/regist",
				"/regist/check/duplicate/**");
		// 세션이 있으면 특정 url 접근 불가
	}

	// configureViewResolvers 설정
	// spring.mvc.view.prefix, spring.mvc.view.suffix 재설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// addResourceHandlers
	// src/main/resources/static 경로의 endpoint 재설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// /static/css/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/css/**") // /static/css/ 의 엔드포인트
				.addResourceLocations("classpath:/static/css/"); // /static/css/ 의 물리적인 위치

		// /static/image/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/image/**") // /static/image/ 의 엔드포인트
				.addResourceLocations("classpath:/static/image/"); // /static/image/ 의 물리적인 위치

		// /static/js/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/js/**") // /static/js/ 의 엔드포인트
				.addResourceLocations("classpath:/static/js/"); // /static/js/ 의 물리적인 위치
	}
}
